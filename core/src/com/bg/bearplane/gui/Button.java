package com.bg.bearplane.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bg.bearplane.engine.Bearplane;
import com.bg.bearplane.engine.Util;
import com.bg.bearplane.engine.Log;

public class Button extends Component {

	public boolean dontRepeat = false;
	public int interval = 0;

	public boolean click = false;
	public boolean sel = false;

	public boolean toggle = false;
	public boolean toggled = false;

	public float fontSize = 32f / 24f;

	public boolean useAbsDraw = false;
	
	public Button() {

	}

	public Button(Scene scene, String id, int x, int y, int width, int height, String text, boolean toggle) {
		super(scene, id, x, y);
		this.width = width;
		this.height = height;
		this.text = text;
		this.toggle = toggle;
		fontSize = height / 24f;
	}

	public Button(Scene scene, String id, int x, int y, int width, int height, String text) {
		this(scene, id, x, y, width, height, text, false);
	}

	public void update() {
		justClicked = false;
		try {
			// click = false;
			int mX = Scene.input.mouseX;
			int mY = Scene.input.mouseY;
			if (disabled) {
				return;
			}
			if (Scene.input.mouseDown[0]) {

				if (Util.inCenteredBox(mX, mY, x, y, width, height)) {

					if (Scene.input.wasMouseJustClicked[0]) {
						Scene.input.wasMouseJustClicked[0] = false;				
						click();
						stamp = tick + 500;
					} else {
						if (tick > stamp && interval > 0) {
							for (int i = 0; i < interval; i++) {
								click();
							}
							click();
						}
					}
				} else {
					click = false;
				}
			} else if (Scene.input.mouseDown[1]) {
				if (Util.inCenteredBox(mX, mY, x, y, width, height)) {
					if (Scene.input.wasMouseJustClicked[1]) {
						Scene.input.wasMouseJustClicked[1] = false;
						for (int i = 0; i < interval; i++) {
							click();
						}
						stamp = tick + 500;
					} else {
						if (tick > stamp) {
							for (int i = 0; i < interval; i++) {
								click();
							}
						}
					}
				} else {
					click = false;
				}
			} else {
				click = false;
			}
		} catch (Exception e) {
			Log.error(e);
		}
	}

	public boolean justClicked = false;

	void click() {
		justClicked = true;
		try {
			if (!toggle) {
				click = true;
			} else {
				toggled = !toggled;
			}
			scene.buttonPressed(id);
		} catch (Exception e) {
			Log.error(e);
			
		}
	}

	public void render() {
		try {
			// render thyself, peasant
			x -= (width / 2);
			y -= (height / 2);
			TextureRegion[][] button = Bearplane.assets.button;
			int p = 0;
			if (click || sel || disabled || (toggle && toggled)) {
				p = 1;
			} else {
				p = 0;
			}
			
			if(useAbsDraw) {
				
				for (int a = 8; a < height - 8; a += 8) {
					scene.drawAbs(Bearplane.assets.bg[p == 1 ? 3 : 4], x + 4, y + 4, 0, 0, width - 8, height - 8);
				}
				// draw top left
				scene.drawRegionAbs(button[p][0], x, y, false, 0, 1);
				// top right
				scene.drawRegionAbs(button[p][1], x + width - 8, y, false, 0, 1);
				// bottom left
				scene.drawRegionAbs(button[p][2], x, y + height - 8, false, 0, 1);
				// bottom right
				scene.drawRegionAbs(button[p][3], x + width - 8, y + height - 8, false, 0, 1);
	
				// left side
				for (int b = 8; b < height - 8; b += 8) {
					scene.drawRegionAbs(button[p][4], x, y + b, false, 0, 1);
				}
				// right side
				for (int b = 8; b < height - 8; b += 8) {
					scene.drawRegionAbs(button[p][5], x + width - 8, y + b, false, 0, 1);
				}
				// top side
				for (int b = 8; b < width - 8; b += 8) {
					scene.drawRegionAbs(button[p][6], x + b, y, false, 0, 1);
				}
				// bottom side
				for (int b = 8; b < width - 8; b += 8) {
					scene.drawRegionAbs(button[p][7], x + b, y + height - 8, false, 0, 1);
				}
				
			} else {
			
				for (int a = 8; a < height - 8; a += 8) {
					scene.draw(Bearplane.assets.bg[p == 1 ? 3 : 4], x + 4, y + 4, 0, 0, width - 8, height - 8);
				}
				// draw top left
				scene.drawRegion(button[p][0], x, y, false, 0, 1);
				// top right
				scene.drawRegion(button[p][1], x + width - 8, y, false, 0, 1);
				// bottom left
				scene.drawRegion(button[p][2], x, y + height - 8, false, 0, 1);
				// bottom right
				scene.drawRegion(button[p][3], x + width - 8, y + height - 8, false, 0, 1);
	
				// left side
				for (int b = 8; b < height - 8; b += 8) {
					scene.drawRegion(button[p][4], x, y + b, false, 0, 1);
				}
				// right side
				for (int b = 8; b < height - 8; b += 8) {
					scene.drawRegion(button[p][5], x + width - 8, y + b, false, 0, 1);
				}
				// top side
				for (int b = 8; b < width - 8; b += 8) {
					scene.drawRegion(button[p][6], x + b, y, false, 0, 1);
				}
				// bottom side
				for (int b = 8; b < width - 8; b += 8) {
					scene.drawRegion(button[p][7], x + b, y + height - 8, false, 0, 1);
				}
				
			}

			x += (width / 2);
			y += (height / 2);

			Color c = Color.WHITE;
			if (disabled) {
				c = Color.GRAY;
			}
			if(useAbsDraw) {
				scene.drawFontAbs(0, x, y + 1, text, true, fontSize, c);				
			} else {
				scene.drawFont(0, x, y + 1, text, true, fontSize, c);
			}
			
			
		} catch (Exception e) {
			Log.error(e);
			
		}
	}
}
