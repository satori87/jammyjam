package com.bg.bearplane.gui;

import com.bg.bearplane.engine.Bearplane;
import com.bg.bearplane.engine.Util;
import com.bg.bearplane.engine.Log;

public class CheckBox extends Component {

	public boolean toggled = false;

	public CheckBox(Scene scene, String id, int x, int y) {
		super(scene, id, x, y);
	}

	public CheckBox(Scene scene, String id, int x, int y, String text) {
		super(scene, id, x, y);
		this.text = text;
	}

	public void update() {
		try {
			int mX = Scene.input.mouseX;
			int mY = Scene.input.mouseY;
			if (disabled) {
				return;
			}
			if (Scene.input.mouseDown[0]) {
				if (Util.inCenteredBox(mX, mY, x, y, 13, 13)) {
					if (Scene.input.wasMouseJustClicked[0]) {
						Scene.input.wasMouseJustClicked[0] = false;
						toggled = !toggled;
						scene.checkBox(id);
					}
				}
			}
		} catch (Exception e) {
			Log.error(e);
			
		}
	}

	public void render() {
		try {
			// render thyself, peasant
			scene.drawRegion(Bearplane.assets.checkBox[toggled ? 1 : 0], x - 6, y - 6, false, 0, 1);
			if (text.length() > 0) {
				scene.drawFont(0, x + 14, y-6, text, false, 1f);
				// scene.drawFont(type, X, Y, s, toggled, scale);
			}
		} catch (Exception e) {
			Log.error(e);
			
		}
	}

}
