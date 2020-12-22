package com.bg.bearplane.gui;

import java.util.ArrayList;
import com.badlogic.gdx.Input.Keys;
import com.bg.bearplane.engine.Bearplane;
import com.bg.bearplane.engine.Util;
import com.bg.bearplane.engine.Log;

public class ListBox extends Component {

	public ArrayList<String> list = new ArrayList<String>();

	public int sel = 0;
	public int hover = 0;

	public int scroll = 0;

	public boolean useFrame = true;

	Frame frame;

	public ListBox(Scene scene, String id, int x, int y, int width, int height) {
		super(scene, id, x, y);
		frame = new Frame(scene, id, x, y, width, height, false, false, true);
		this.width = width;
		height = ((height - 10) / 20) * 20 + 10;
		this.height = height;
	}

	public int getListItemHeight() {
		return (height - 10) / 20;
	}

	int last = 0;

	public void update() {

		last = sel;
		hover = -1;
		try {
			frame.updateComponent(tick);
			int sx = Scene.input.mouseX;
			int sy = Scene.input.mouseY;
			int my = ((sy - y - 5) / 20) + scroll;
			for (Integer i : Scene.input.keyPress) {
				switch (i) {
				case Keys.ENTER:
					scene.enterPressedInList(id);
					break;
				}
			}

			if (Util.inBox(sx, sy, x + 5, x + 5 + width, y + 5, y + 5 + height)) {
				if (Scene.input.keyDown[Keys.PAGE_DOWN]) {
					setScroll(scroll + 10);
				}
				if (Scene.input.keyDown[Keys.PAGE_UP]) {
					setScroll(scroll - 10);
				}
				while (!Scene.input.scrolls.isEmpty()) {
					int i = Scene.input.scrolls.removeFirst();
					setScroll(scroll + i * 5);
				}
				if (my >= 0 && my < list.size()) {
					if (Scene.input.mouseDown[0]) {
						sel = my;
						if (sel != last) {
							scene.listChanged(id, sel);
						}
					} else {
						hover = my;
					}
				}
			}
			if (sel >= list.size()) {
				sel = list.size() - 1;
			}
			if (sel < 0) {
				sel = 0;
			}
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}

	public void setScroll(int s) {
		scroll = s;
		if (scroll >= list.size() - getListItemHeight()) {
			scroll = list.size() - getListItemHeight();
		}
		if (scroll < 0) {
			scroll = 0;
		}
	}

	public void render() {
		try {
			int t = 4;
			int imax = list.size();
			int ii = 0;
			for (int i = 0; i < getListItemHeight(); i++) {
				ii = i + scroll;
				if (ii == sel && sel < imax) {
					t = 3;
				} else if (hover >= 0 && ii == hover && hover < imax) {
					t = 1;
				} else {
					t = 4;
				}
				scene.draw(Bearplane.assets.bg[t], x, y + 5 + i * 20, 0, y + 5 + i * 20, width, 20);
				if (ii < list.size()) {
					scene.drawFont(0, x + 10, y + 5 + i * 20 + 3, list.get(ii), false, 1);
				}
			}
			frame.renderComponent();
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}

}
