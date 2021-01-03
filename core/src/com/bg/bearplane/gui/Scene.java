package com.bg.bearplane.gui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.bg.bearplane.engine.Bearplane;
import com.bg.bearplane.engine.Util;
import com.gdx420.jammyjam.core.DialogQueue;
import com.bg.bearplane.engine.Log;

public abstract class Scene extends Frame {

	public static HashMap<String, Scene> scenes = new HashMap<String, Scene>();
	public static Scene scene;
	private static float screenWidth, screenHeight;
	public static float viewWidth, viewHeight;
	public static int originX, originY;
	public static OrthographicCamera cam;
	public static OrthographicCamera curCam;
	private static ShapeRenderer shapeRenderer;
	public static SpriteBatch batcher;
	public static InputHandler input;
	public static long lastRepeat = 0;
	public static long tick = 0;
	public static boolean shifting = false;
	public static boolean alting = false;
	public static boolean ctrling = false;
	public static boolean locked = false;
	static List<String> msgBoxMsgs = new LinkedList<String>();
	static Frame msgBoxFrame;
	static Button msgBoxOK;
	static Scene lastScene = null;

	public Color forceCol = null; 
	
	public String id = "";
	public boolean autoCenter = false;
	public long startStamp = 0;
	public boolean started = false;
	public TreeMap<Integer, Focusable> focusList = new TreeMap<Integer, Focusable>();
	Focusable focus;

	public void clearFocus() {
		
		for(Field f : fields.values()) {
			f.focus = false;
		}
		focus = null;
	}
	
	// Lifecyle

	public static void init() {
		try {
			input = new InputHandler();
			Gdx.input.setInputProcessor(input);
			setupScreen(Bearplane.game.getGameWidth(), Bearplane.game.getGameHeight());
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public static void updateScene() {		
		if (scene != null) {
			if (!locked) {
				scene.updateBase();
			} else if (msgBoxFrame != null) {
				// we have a msg box
				msgBoxFrame.updateComponent(tick);
				if (msgBoxOK.justClicked) {
					if (msgBoxMsgs.size() > 0) {
						msgBoxMsgs.remove(0);
					}
					if (msgBoxMsgs.size() == 0) {
						msgBoxFrame = null;
						input.wasMouseJustClicked[0] = false;
						scene.deselectAllButtons();
						unlock();
					}
				}
			}
		} else {
			// Log.fatal("NOSCENE");
		}
		input.keyPress.clear();
	}

	public void updateBase() {
		try {
			tick = System.currentTimeMillis();
			shifting = input.keyDown[59] || input.keyDown[60];
			alting = input.keyDown[57] || input.keyDown[58];
			ctrling = input.keyDown[129] || input.keyDown[130];

			for (Frame d : frames.values())
				d.updateComponent(tick);
			for (Button b : buttons.values())
				b.updateComponent(tick);
			for (Field t : fields.values())
				t.updateComponent(tick);
			for (CheckBox c : checkBoxes.values())
				c.updateComponent(tick);
			for (ListBox l : listBoxes.values()) {
				l.updateComponent(tick);
			}
			DialogQueue.updateDisplayDialog(tick);
			for (int i = 0; i < 10; i++) {
				if (input.wasMouseJustClicked[i]) { // none of the scene objects caught this
					mouseDown(input.mouseDownX[i], input.mouseDownY[i], i);
					input.wasMouseJustClicked[i] = false;
				} else if (input.wasMouseJustReleased[i]) {

					mouseUp(input.mouseUpX[i], input.mouseUpY[i], i);
					input.wasMouseJustReleased[i] = false;
				}
			}
		} catch (Exception e) {
			Log.error(e);

		}
		update();
	}

	public abstract void update();

	public void renderBase() {
		try {
			// overload only in some scenes
			for (Frame d : frames.values()) {
				d.renderComponent();
			}
			for (Button b : buttons.values()) {
				b.renderComponent();
			}
			for (Label l : labels.values()) {
				l.renderComponent();
			}
			for (Field t : fields.values()) {
				t.renderComponent();
			}
			for (CheckBox c : checkBoxes.values()) {
				c.renderComponent();
			}
			for (ListBox l : listBoxes.values()) {
				l.renderComponent();
			}
			
		} catch (Exception e) {
			Log.error(e);

		}
		if (autoCenter) {
			moveCameraTo(Bearplane.game.getGameWidth() / 2, Bearplane.game.getGameHeight() / 2);
		}
		render();
		DialogQueue.render();		
		
	}

	public abstract void render();

	public static void renderScene() {
		try {
			if (scene == null) {
				return;
			}
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batcher.enableBlending();
			batcher.begin();
			scene.renderBase();
			if (msgBoxFrame != null) {
				msgBoxFrame.renderComponent();
				String ss = msgBoxMsgs.get(0);
				List<String> lines = Util.wrapText(2f, Bearplane.game.getGameWidth() / 2, ss);
				int i = 0;
				for (String s : lines) {
					i++;
					scene.drawFontAbs(0, Bearplane.game.getGameWidth() / 2,
							Bearplane.game.getGameHeight() / 2 - 170 + i * 40, s, true, 2.0f);
				}
			}
			batcher.end();
			letterBox();
		} catch (Exception e) {
			Log.error(e);

		}
	}

	// Scene management

	public static void change(String to) {
		try {
			moveCameraTo(gameWidth / 2, gameHeight / 2);
			input.wasMouseJustClicked[0] = false;
			Scene s = scenes.get(to);
			if (s != null) {
				lastScene = scene;
				scene = s;
				if (!scene.started) {
					scene.start();
				}
				scene.switchTo();
			}
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public static Scene get(String get) {
		return scenes.get(get);
	}

	public static void addScene(String id, Scene s) {
		scenes.put(id, s);
		s.id = id;
	}

	public void switchTo() {
		// overload this if you want to

	}

	public static void lock() {
		locked = true;
	}

	public static void unlock() {
		locked = false;
	}

	public void clear() {
		// dude make all of these extend something so you can fix this ridiculous list
		frames.clear();
		buttons.clear();
		labels.clear();
		fields.clear();
		checkBoxes.clear();
		focusList.clear();
		focus = null;
		startStamp = tick;
	}

	public void start() {
		try {
			started = true;
			clear();
			startStamp = tick;
		} catch (Exception e) {
			Log.error(e);

		}
	}

	// Component management

	public void msgBox(String s) {
		msgBoxMsgs.add(s);
		lock();
		if (msgBoxFrame == null) {
			int gw = Bearplane.game.getGameWidth();
			int gh = Bearplane.game.getGameHeight();
			msgBoxFrame = new Frame(this, "frame", gw / 2, gh / 2, 750, 384, true, true, true);
			msgBoxOK = new Button(this, "ok", gw / 2, gh / 2 + 128, 128, 48, "OK", false);
			msgBoxFrame.buttons.put("ok", msgBoxOK);
		}
	}

	void nextFocus() {
		if (focus == null) {
			if (!focusList.isEmpty()) {
				setFocus(focusList.firstKey());
			}
		} else {
			if (focusList.size() > 1) {
				boolean found = false;
				if (shifting) {
					for (int i = focus.getTabIndex() - 1; i >= focusList.firstKey(); i--) {
						if (!found && setFocus(i)) {
							found = true;
						}
					}
					if (!found) {
						for (int i = focusList.lastKey(); i > focus.getTabIndex(); i--) {
							if (!found && setFocus(i)) {
								found = true;
							}
						}
					}
				} else {
					for (int i = focus.getTabIndex() + 1; i <= focusList.lastKey(); i++) {
						if (!found && setFocus(i)) {
							found = true;
						}
					}
					if (!found) {
						for (int i = focusList.firstKey(); i < focus.getTabIndex(); i++) {
							if (!found && setFocus(i)) {
								found = true;
							}
						}
					}
				}
			}
		}
	}

	public Focusable getFocus() {
		return focus;
	}

	public boolean setFocus(int tab) {
		Focusable f = focusList.get(tab);
		if (f != null) {
			if (f.canFocus()) {
				if (focus != null) {
					focus.loseFocus();
				}
				focus = f;
				focus.gainFocus();
				return true;
			}
		}
		return false;
	}

	public void registerTab(Focusable f) {
		if (focusList.get(f.getTabIndex()) != null) {
			Log.error("Duplicate tabIndex: " + id);
		}
		focusList.put(f.getTabIndex(), f);
	}

	public void deselectAllButtons() {
		for (Button b : buttons.values()) {
			if (!b.toggle) {
				b.sel = false;
				b.click = false;
				b.justClicked = false;
			}
		}
	}

	// Graphical

	public void clip(int x, int y, int width, int height) {
		try {
			batcher.flush();
			Rectangle scissors = new Rectangle();
			Rectangle clipBounds = new Rectangle(x, y, width, height);
			ScissorStack.calculateScissors(cam, batcher.getTransformMatrix(), clipBounds, scissors);
			ScissorStack.pushScissors(scissors);
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public void endClip() {
		try {
			batcher.flush();
			ScissorStack.popScissors();
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public void draw(Texture t, int x, int y, int w, int h, int srcX, int srcY, int srcW, int srcH) {
		try {
			Color oldCol = Color.WHITE;
			if(t != null) {
				if(forceCol != null) {
					oldCol = batcher.getColor();					
					batcher.setColor(forceCol);
				}
				
				batcher.draw(t, x, y, w, h, srcX, srcY, srcW, srcH, false, true);
				if(forceCol != null) {
					batcher.setColor(oldCol);
				}
			}
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public void drawAbs(Texture t, int x, int y, int w, int h, int srcX, int srcY, int srcW, int srcH) {
		try {
			batcher.draw(t, x + curCam.position.x - Bearplane.game.getGameWidth() / 2,
					y + curCam.position.y - Bearplane.game.getGameHeight() / 2, w, h, srcX, srcY, srcW, srcH, false,
					true);
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public void drawAbs(Texture t, int x, int y, int srcX, int srcY, int w, int h) {
		try {
			drawAbs(t, x, y, w, h, srcX, srcY, w, h);
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public void draw(Texture t, int x, int y, int srcX, int srcY, int w, int h) {
		try {
			draw(t, x, y, w, h, srcX, srcY, w, h);
		} catch (Exception e) {
			Log.error(e);

		}
	}
	
	public void drawRegionAbs(TextureRegion region, float X, float Y, boolean centered, float rotation, float scale) {
		try {
			if (region == null) {
				return;
			}
			int width = region.getRegionWidth();
			int height = region.getRegionHeight();
			float eX = 0;
			float eY = 0;
			// if (gameState == 3) {
			// eX = X + originX;
			// eY = Y + originY;
			// } else {
			eX = X;
			eY = Y;
			// }
			if (centered) {
				eX -= (width / 2);
				eY -= (height / 2);
			}
			// we gotta round the floats
			int dX = Math.round(eX);
			int dY = Math.round(eY);
			if (centered) {
				batcher.draw(region, dX + curCam.position.x - Bearplane.game.getGameWidth() / 2, dY+ curCam.position.y - Bearplane.game.getGameHeight() / 2, width / 2, height / 2, width, height, scale, scale, rotation);
			} else {
				batcher.draw(region, dX + curCam.position.x - Bearplane.game.getGameWidth() / 2, dY+ curCam.position.y - Bearplane.game.getGameHeight() / 2, 0, 0, width, height, scale, scale, rotation);
			}
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public void drawRegion(TextureRegion region, float X, float Y, boolean centered, float rotation, float scale) {
		try {
			if (region == null) {
				return;
			}
			int width = region.getRegionWidth();
			int height = region.getRegionHeight();
			float eX = 0;
			float eY = 0;
			// if (gameState == 3) {
			// eX = X + originX;
			// eY = Y + originY;
			// } else {
			eX = X;
			eY = Y;
			// }
			if (centered) {
				eX -= (width / 2);
				eY -= (height / 2);
			}
			// we gotta round the floats
			int dX = Math.round(eX);
			int dY = Math.round(eY);
			if (centered) {
				batcher.draw(region, dX, dY, width / 2, height / 2, width, height, scale, scale, rotation);
			} else {
				batcher.draw(region, dX, dY, 0, 0, width, height, scale, scale, rotation);
			}
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public void drawRegionAbout(TextureRegion region, float X, float Y, float aboutX, float aboutY, boolean centered,
			float rotation, float scale) {
		try {
			if (region == null) {
				return;
			}
			int width = region.getRegionWidth();
			int height = region.getRegionHeight();
			float eX = X + originX;
			float eY = Y + originY;
			if (centered) {
				eX -= (width / 2);
				eY -= (height / 2);
			}
			// we gotta round the floats
			float orX = aboutX + originX;
			float orY = aboutY + originY;
			int dX = Math.round(eX);
			int dY = Math.round(eY);
			int oX = Math.round(orX);
			int oY = Math.round(orY);
			batcher.draw(region, dX, dY, oX, oY, width, height, scale, scale, rotation);
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public void drawFontAbs(int type, float X, float Y, String s, boolean centered, float scale, Color col) {
		try {
			if (s.length() < 1) {
				return;
			}
			float curX = X;
			float padding = 0 * scale;
			float spacing = 1.0f * scale;
			float total = 0;
			float oX, oY;
			// get a quick count of width
			if (centered) {
				total = Bearplane.assets.getStringWidth(s, scale, padding, spacing);
				oX = Math.round(-total / 2);
				oY = Math.round((scale * -16.0f) / 2);
			} else {
				oX = 0;
				oY = 0;
			}
			batcher.setColor(col);
			for (char c : s.toCharArray()) {
				int ascii = (int) c;
				if (Bearplane.assets.fontWidth[ascii] > 0) {
					drawRegion(Bearplane.assets.font[type][ascii],
							Math.round(curX + padding + oX + curCam.position.x - Bearplane.game.getGameWidth() / 2),
							Math.round(Y + oY + curCam.position.y - Bearplane.game.getGameHeight() / 2), false, 0,
							scale);
					curX += Bearplane.assets.fontWidth[ascii] * scale + padding * 2 + spacing;
				}
			}
			batcher.setColor(Color.WHITE);
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public void drawFontAbs(int type, float X, float Y, String s, boolean centered, float scale) {
		drawFontAbs(type, X, Y, s, centered, scale, Color.WHITE);
	}

	public void drawFont(int type, float X, float Y, String s, boolean centered, float scale, Color col) {
		try {
			if (s.length() < 1) {
				return;
			}
			float curX = X;
			float padding = 0 * scale;
			float spacing = 1.0f * scale;
			float total = 0;
			float oX, oY;
			// get a quick count of width
			if (centered) {
				total = Bearplane.assets.getStringWidth(s, scale, padding, spacing);
				oX = Math.round(-total / 2);
				oY = Math.round((scale * -16.0f) / 2);
			} else {
				oX = 0;
				oY = 0;
			}
			batcher.setColor(col);
			for (char c : s.toCharArray()) {
				int ascii = (int) c;
				if (Bearplane.assets.fontWidth[ascii] > 0) {
					drawRegion(Bearplane.assets.font[type][ascii], Math.round(curX + padding + oX), Math.round(Y + oY),
							false, 0, scale);
					curX += Bearplane.assets.fontWidth[ascii] * scale + padding * 2 + spacing;
				}
			}
			batcher.setColor(Color.WHITE);
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public void drawFont(int type, float X, float Y, String s, boolean centered, float scale) {
		drawFont(type, X, Y, s, centered, scale, Color.WHITE);
	}

	public static int getRelativeX(int x) {
		// return x;
		return Math.round(((float) x / Gdx.graphics.getWidth()) * viewWidth - originX);
	}

	public static int getRelativeY(int y) {
		// return y;
		return Math.round(((float) y / Gdx.graphics.getHeight()) * viewHeight - originY);
	}

	private static void letterBox() {
		try {
			int x = Math.round(cam.position.x) - Math.round(Bearplane.game.getGameWidth() / 2 + originX);
			int y = Math.round(cam.position.y) - Math.round(Bearplane.game.getGameHeight() / 2 + originY);
			// ensure our letterbox area is completely black (or filled with
			// whatever letterbox design we choose
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(0, 0, 0, 1);
			if (originY > 0) {
				shapeRenderer.rect(x, y - 1, viewWidth, originY + 1); // Top bar
				shapeRenderer.rect(x, y + viewHeight - originY, viewWidth, originY + 1); // Bottom
																							// bar
			} else if (originX > 0) {
				shapeRenderer.rect(x - 1, y, originX + 1, viewHeight); // Left bar
				shapeRenderer.rect(x + viewWidth - originX, y, originX, viewHeight + 1); // Right
																							// bar
			}
			shapeRenderer.end();
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public static void moveCameraBy(float x, float y) {
		try {
			cam.position.y += y;
			cam.position.x += x;
			cam.update();
			batcher.setProjectionMatrix(cam.combined);
			shapeRenderer.setProjectionMatrix(cam.combined);
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public static void moveCameraTo(float x, float y) {
		try {
			cam.position.y = Math.round(y);
			cam.position.x = Math.round(x);
			cam.update();
			batcher.setProjectionMatrix(cam.combined);
			shapeRenderer.setProjectionMatrix(cam.combined);
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public static void changeCamera(OrthographicCamera c) {
		try {
			curCam = c;
			batcher.setProjectionMatrix(c.combined);
			shapeRenderer.setProjectionMatrix(c.combined);
		} catch (Exception e) {
			Log.error(e);

		}
	}

	public static int gameWidth;
	public static int gameHeight;

	public static void setupScreen(float _gameWidth, float _gameHeight) {
		try {
			gameWidth = (int) _gameWidth;
			gameHeight = (int) _gameHeight;
			screenWidth = Gdx.graphics.getWidth();
			screenHeight = Gdx.graphics.getHeight();
			float screenR = (float) screenWidth / (float) screenHeight;
			float gameR = _gameWidth / _gameHeight;
			if (screenR == gameR) {
				originX = 0;
				originY = 0;
				viewWidth = _gameWidth;
				viewHeight = _gameHeight;
			} else if (screenR > gameR) {
				viewWidth = _gameHeight * screenR;
				viewHeight = _gameHeight;
				originX = (int) ((viewWidth - _gameWidth) / 2.0f);
				originY = 0;
			} else if (screenR < gameR) {
				viewWidth = _gameWidth;
				viewHeight = _gameWidth / screenR;
				originX = 0;
				originY = (int) ((viewHeight - _gameHeight) / 2.0f);
			}
			// input.ratio = screenR / gameR;
			// Set up our camera, which handles the screen scaling, use viewWidth to
			// include letterbox area
			cam = new OrthographicCamera();
			cam.setToOrtho(true, Math.round(viewWidth), Math.round(viewHeight));
			curCam = cam;
			// Create our sprite batcher and shape renderer from the camera
			batcher = new SpriteBatch();
			batcher.setProjectionMatrix(cam.combined);
			shapeRenderer = new ShapeRenderer();
			shapeRenderer.setProjectionMatrix(cam.combined);
		} catch (Exception e) {
			Log.error(e);

		}
	}

	// Input

	public abstract void enterPressedInList(String id);

	public abstract void listChanged(String id, int sel);

	public abstract void buttonPressed(String id);

	public abstract void enterPressedInField(String id);

	public abstract void mouseDown(int x, int y, int button);

	public abstract void mouseUp(int x, int y, int button);

	public abstract void checkBox(String id);

}
