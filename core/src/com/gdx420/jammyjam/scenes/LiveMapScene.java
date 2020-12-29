package com.gdx420.jammyjam.scenes;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bg.bearplane.engine.Util;
import com.bg.bearplane.engine.DrawTask;
import com.bg.bearplane.engine.Effect;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.gui.Scene;
import com.bg.bearplane.gui.Field;
import com.gdx420.jammyjam.core.Assets;
import com.gdx420.jammyjam.core.JammyJam;
import com.gdx420.jammyjam.core.MapData;
import com.gdx420.jammyjam.core.NonPlayableCharacter;
import com.gdx420.jammyjam.core.Player;
import com.gdx420.jammyjam.core.Realm;
import com.gdx420.jammyjam.core.Shared;
import com.gdx420.jammyjam.core.Tile;

public class LiveMapScene extends Scene {

	// Sprite character;

	public static OrthographicCamera fCam = new OrthographicCamera();

	FrameBuffer mapBG = new FrameBuffer(Format.RGBA8888, Shared.MAP_WIDTH * 32, Shared.MAP_WIDTH * 32, false);
	FrameBuffer mapFG = new FrameBuffer(Format.RGBA8888, Shared.MAP_WIDTH * 32, Shared.MAP_WIDTH * 32, false);

	ArrayList<DrawTask> drawList = new ArrayList<DrawTask>();

	public boolean processed = false;

	Field text;

	public void start() {
		super.start();
		autoCenter = false;
		text = new Field(this, "0", 141, 0, 0, 0, 555, true);
		text.visible = false;
		text.allowSpecial = true;
		fields.put("0", text);
		text.updateAnyway = true;
		text.focus = true;
		fCam.setToOrtho(false, Shared.MAP_WIDTH * 32, Shared.MAP_WIDTH * 32);
		// character = new Sprite(0, 1);
	}

	int mx = 0;
	int my = 0;
	int mmx = 0;
	int mmy = 0;

	public void update() {
		mx = (input.mouseX / 32) + (int) (cam.position.x / 32) - Shared.GAME_WIDTH / 64;
		my = (input.mouseY / 32) + (int) (cam.position.y / 32) - Shared.GAME_HEIGHT / 64;
		mmx = (int) (input.mouseX + cam.position.x - Shared.GAME_WIDTH / 2);
		mmy = (int) (input.mouseY + cam.position.y - Shared.GAME_HEIGHT / 2);

		// Odyssey.game.curChatText = text.text;
		checkKeys();
		// if (this instanceof PlayScene) {
		// character = Realm.players.get(Odyssey.game.cid);
		// } else {
		// character.update(tick);
		// }
		checkMouse();
		// super.update();

	}

	long doorStamp = 0;

	void checkMouse() {

	}

	void checkKeys() {
		try {
			// overload me
			for (Integer i : input.keyPress) {
				switch (i) {
				case Keys.PAGE_DOWN:
					// Odyssey.game.chatScrollDown();
					break;
				case Keys.PAGE_UP:
					// Odyssey.game.chatScrollUp();
					break;
				case Keys.HOME:
					// Odyssey.game.toggleChat();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render() {
		// super.render();
		updateCamera();
		drawMap();
		// drawChat();
	}

	public void updateCamera() {
		int cx = JammyJam.game.player.x;
		int cy = JammyJam.game.player.y;
		int ncx = (int) cam.position.x;
		int ncy = (int) cam.position.y;
		if (cx - ncx > Shared.BOUNDING_BOX * 32) {
			ncx = cx - Shared.BOUNDING_BOX * 32;
		} else if (ncx - cx > (Shared.BOUNDING_BOX + 1) * 32) {
			ncx = cx + (Shared.BOUNDING_BOX + 1) * 32;
		}
		if (cy - ncy > Shared.BOUNDING_BOX * 32) {
			ncy = cy - Shared.BOUNDING_BOX * 32;
		} else if (ncy - cy > Shared.BOUNDING_BOX * 32) {
			ncy = cy + Shared.BOUNDING_BOX * 32;
		}
		moveCameraTo(ncx, ncy);
	}

	void drawMapLayer(int i) {
		for (DrawTask d : drawList) {
			if (d.i == i) {
				Scene.batcher.setColor(d.col);
				if (d.type == 0) {
					drawTile(d.set, d.num, d.x, d.y);
				} else if (d.type == 1) {
					drawSprite(d.set, d.num, d.f, d.x, d.y);
				} else if (d.type == 3) {
					draw(Assets.textures.get(d.tex), d.x, d.y, d.sx, d.sy, d.w, d.h);
				} else if (d.type == 4) {
					// Realm.effects.get(d.f).fx.draw(Scene.batcher);
				}
				Scene.batcher.setColor(Color.WHITE);
			}

		}
	}

	public void drawSprite(int s, int sprite, int f, int x, int y) {
		// Texture set = Assets.textures.get(Shared.spritesets[s]);
		// if (sprite > 0) {
		// sprite--;
		// int tileX = f * 32;
		// int tileY = sprite * 32;
		// draw(set, x, y, tileX, tileY, 32, 32);
		// }
	}

	public void drawTile(int s, int tile, int x, int y) {
		Texture set = Assets.textures.get(Shared.tilesets[s]);

		if (tile > 0) {
			tile--;
			int tileX = (tile % 16) * 32;
			int tileY = (tile / 16) * 32;
			draw(set, x, y, tileX, tileY, 32, 32);
		}
	}

	void drawMap() {
		if (!processed) {
			preprocessMap();
			processed = true;
		}
		TextureRegion region;
		region = new TextureRegion(mapBG.getColorBufferTexture(), 0, 0, Shared.MAP_WIDTH * 32, Shared.MAP_WIDTH * 32);
		region.flip(false, true);
		drawRegion(region, 0, 0, false, 0, 1);
		// Realm.renderFX(0);
		drawList.clear();
		processMidLayer();
		drawMapLayer(3);
		// Realm.renderFX(2);
		region = new TextureRegion(mapFG.getColorBufferTexture(), 0, 0, Shared.MAP_WIDTH * 32, Shared.MAP_WIDTH * 32);
		region.flip(false, true);
		drawRegion(region, 0, 0, false, 0, 1);
		// Realm.realm.render();
		// Realm.renderFX(3);

		processTextLayer();

	}

	void preprocessMap() {

		mapBG = new FrameBuffer(Format.RGBA8888, Shared.MAP_WIDTH * 32, Shared.MAP_WIDTH * 32, false);
		mapFG = new FrameBuffer(Format.RGBA8888, Shared.MAP_WIDTH * 32, Shared.MAP_WIDTH * 32, false);

		batcher.end();
		drawList.clear();

		mapBG.begin();
		batcher.begin();

		changeCamera(fCam);
		// draw to bg fb
		processLayer(0);
		processLayer(7);
		processLayer(9);
		processLayer(1);
		processLayer(2);
		drawMapLayer(0);
		drawMapLayer(7);
		drawMapLayer(9);
		drawMapLayer(1);
		drawMapLayer(2);
		batcher.end();
		mapBG.end();

		mapFG.begin();
		batcher.begin();

		changeCamera(fCam);
		drawList.clear();
		// draw to fg fb
		processLayer(8);
		processLayer(4);
		processLayer(5);
		processLayer(6);
		drawMapLayer(8);
		drawMapLayer(4);
		drawMapLayer(5);
		drawMapLayer(6);
		batcher.end();
		mapFG.end();

		batcher.begin();
		changeCamera(cam);

	}

	void processTextLayer() {
		/*
		 * String s = ""; Color col = Color.WHITE; float a = 0; if (this instanceof
		 * PlayScene) { for (Sprite c : Realm.players.values()) { if (c.map ==
		 * Realm.curMap) { if (c.dead) { a = (float) ((c.diedAt + 10000) - tick) /
		 * 10000f; a -= .4f; if (a < 0) a = 0; if (a > 1) a = 1; col = new Color(1, 1,
		 * 1, a); } else { col = Color.WHITE; } drawFont(0, c.trueX() + 16, c.trueY() -
		 * 8, c.name, true, 1f, col); } } for (Monster c : Realm.monsters.values()) { if
		 * (c.map == Realm.curMap) { if (c.dead) { a = (float) ((c.diedAt + 10000) -
		 * tick) / 10000f; a -= .4f; if (a < 0) a = 0; if (a > 1) a = 1; col = new
		 * Color(1, 1, 1, a); } else { col = Color.WHITE; } drawFont(0, c.trueX() + 16,
		 * c.trueY() - 8, c.name, true, 1f, col); } } for (Door c :
		 * Realm.doors.values()) { if (c.hover) { if (c.state == 0) { if (c.open) { s =
		 * "Close Door"; } else { s = "Open Door"; } } else if (c.state == 1) { s =
		 * "Opening"; } else { s = "Closing"; } col = Color.RED; if
		 * (c.inRange(Odyssey.game.getMe().x, Odyssey.game.getMe().y)) { col =
		 * Color.WHITE; } drawFont(0, c.getTrueX(), c.getTrueY(), s, true, 1f, col); } }
		 * }
		 */
	}

	int ww = 0;
	
	void processMidLayer() {
		int i = 3;
		int aa = 0;
		String a = "";
		ArrayList<ArrayList<DrawTask>> layerList = null;
		layerList = new ArrayList<ArrayList<DrawTask>>();
		for (int y = 0; y < Shared.MAP_WIDTH * 32 + 64; y++) {
			layerList.add(new ArrayList<DrawTask>());
		}
		Tile t = null;
		DrawTask dt = null;
		int mx = 0;
		int my = 0;
		int ly = 0;
		ww++;
		if(ww >= Player.MAX_FRAMES) {
			ww = 0;
		}
		if (this instanceof PlayScene) {
			Player p = JammyJam.game.player;
			dt = new DrawTask(i, p.getSprite(this), p.x - 16, p.y - 32, (p.walkStep/(Player.MAX_FRAMES/9)) * 64, p.dir * 64, 32, 64);
			ly = 32 + p.y;
			if (ly >= 0 && ly < Shared.MAP_WIDTH * 32 + 64) {
				layerList.get(ly).add(dt);
			}

			for (NonPlayableCharacter npc : JammyJam.game.npcList) {
				if (npc.onScreen) {
					dt = new DrawTask(i, npc.tile_sheet, npc.x - 16, npc.y - 32, (ww/(Player.MAX_FRAMES/9)) * 64, 128, 32, 64);
					ly = 32 + npc.y;
					if (ly >= 0 && ly < Shared.MAP_WIDTH * 32 + 64) {
						layerList.get(ly).add(dt);
					}
				}
			}
		}

		for (int y = 0; y < Shared.MAP_WIDTH; y++) {
			for (int x = 0; x < Shared.MAP_WIDTH; x++) {
				mx = x;
				my = y;
				if (MapData.inBounds(mx, my)) {
					t = Realm.mapData[Realm.curMap].tile[mx][my];
					if (t.tile[i] > 0) {
						dt = new DrawTask(i, t.set[i], t.tile[i], x * 32 + t.shiftX[i], y * 32 + t.shiftY[i]);
						layerList.get(32 + y * 32 + t.shiftY[i]).add(dt);
					}
				}
			}
		}

		for (ArrayList<DrawTask> sortedY : layerList) {
			for (DrawTask sortedX : sortedY) {
				drawList.add(sortedX);
			}
		}

	}

	void processLayer(int i) {
		String a = "";
		int aa = 0;
		ArrayList<ArrayList<DrawTask>> layerList = null;
		layerList = new ArrayList<ArrayList<DrawTask>>();
		for (int y = 0; y < Shared.MAP_WIDTH * 32; y++) {
			layerList.add(new ArrayList<DrawTask>());
		}
		Tile t = null;
		DrawTask dt = null;
		for (int y = 0; y < Shared.MAP_WIDTH; y++) {
			for (int x = 0; x < Shared.MAP_WIDTH; x++) {
				t = Realm.mapData[Realm.curMap].tile[x][y];
				if (i < 7) {
					if (t.tile[i] > 0) {
						dt = new DrawTask(i, t.set[i], t.tile[i], x * 32 + t.shiftX[i], y * 32 + t.shiftY[i]);
						layerList.get(y * 32 + t.shiftY[i]).add(dt);
					}
				}
			}
		}
		for (ArrayList<DrawTask> sortedY : layerList) {
			for (DrawTask sortedX : sortedY) {
				drawList.add(sortedX);
			}
		}
	}

	@Override
	public void buttonPressed(String id) {

	}

	@Override
	public void enterPressedInField(String id) {
		// Odyssey.game.processTextInput();
		// Odyssey.game.curChatText = "";
		// text.text = "";
	}

	public void switchTo() {
		super.switchTo();

		int cx = JammyJam.game.player.x + 16;
		int cy = JammyJam.game.player.y + 16;
		if (cx < Shared.GAME_WIDTH / 2) {
			cx = Shared.GAME_WIDTH / 2;
		}
		if (cx > Shared.MAP_WIDTH * 32 - Shared.GAME_WIDTH / 2) {
			cx = Shared.MAP_WIDTH * 32 - Shared.GAME_WIDTH / 2;
		}
		if (cy < Shared.GAME_HEIGHT / 2) {
			cy = Shared.GAME_HEIGHT / 2;
		}
		if (cy > Shared.MAP_WIDTH * 32 - Shared.GAME_HEIGHT / 2) {
			cy = Shared.MAP_WIDTH * 32 - Shared.GAME_HEIGHT / 2;
		}
		moveCameraTo(cx, cy);

	}

	@Override
	public void enterPressedInList(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listChanged(String id, int sel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(int x, int y, int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(int x, int y, int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkBox(String id) {
		// TODO Auto-generated method stub

	}
}
