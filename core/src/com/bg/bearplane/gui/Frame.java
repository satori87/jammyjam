package com.bg.bearplane.gui;

import java.util.HashMap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bg.bearplane.engine.Bearplane;

public class Frame extends Component {

	public boolean useBackground = true;
	public boolean useFrame = true;

	public boolean relative = false;

	public HashMap<String, Frame> frames = new HashMap<String, Frame>();
	public HashMap<String, Button> buttons = new HashMap<String, Button>();
	public HashMap<String, Label> labels = new HashMap<String, Label>();
	public HashMap<String, Field> fields = new HashMap<String, Field>();
	public HashMap<String, CheckBox> checkBoxes = new HashMap<String, CheckBox>();
	public HashMap<String, ListBox> listBoxes = new HashMap<String, ListBox>();
	public HashMap<String, DialogDisplay> dialogDisplaies = new HashMap<String, DialogDisplay>();

	Frame parent = null;

	public boolean useAbsDraw = false;

	public Frame() {

	}

	public Frame(Scene scene) {
		x = 0;
		y = 0;
		width = 0;
		height = 0;
		useBackground = false;
		centered = false;
		useFrame = false;
	}

	public Frame(Scene scene, String id, int x, int y, int width, int height, boolean useBackground, boolean centered,
			boolean useFrame) {
		super(scene, id, x, y);
		this.width = width;
		this.height = height;
		this.useBackground = useBackground;
		this.centered = centered;
		this.useFrame = useFrame;
	}

	public Frame(Scene scene, String id, int x, int y, int width, int height, boolean useBackground, boolean centered) {
		this(scene, id, x, y, width, height, useBackground, centered, true);
	}

	public Button addButton(String id, int x, int y, int width, int height, String text) {
		return addButton(id, x, y, width, height, text, false);
	}

	public Button addButton(String id, int x, int y, int width, int height, String text, boolean toggle) {
		return buttons.put(id, new Button((Scene) this, id, x, y, width, height, text, toggle));
	}

	public static Button getButton(HashMap<String, Button> buttons, String id) {
		return buttons.get(id);
	}

	public Button getButton(String id) {
		return buttons.get(id);
	}

	public Frame addFrame(String id, int x, int y, int w, int h, boolean bg, boolean center) {
		return frames.put(id, new Frame((Scene) this, id, x, y, w, h, bg, center));
	}

	public Frame getFrame(String id) {
		return frames.get(id);
	}

	public Frame getFrame(HashMap<String, Frame> frames, String id) {
		return frames.get(id);
	}

	public Label addLabel(String id, int x, int y, float s, String t, Color c, boolean center) {
		return labels.put(id, new Label((Scene) this, id, x, y, s, t, c, center));
	}

	public static Label getLabel(HashMap<String, Label> labels, String id) {
		return labels.get(id);
	}

	public Label getLabel(String id) {
		return labels.get(id);
	}

	public Field addField(String id, int max, int tab, int x, int y, int width, boolean centered, Frame frame) {
		Scene s = scene;
		if (this instanceof Scene) {
			s = (Scene) this;
		}
		Field f = new Field(s, id, max, tab, x, y, width, centered, frame);
		fields.put(id, f);
		s.registerTab(f);
		return f;
	}

	public Field addField(String id, int max, int tab, int x, int y, int width, boolean centered) {
		return addField(id, max, tab, x, y, width, centered, null);
	}

	public Field addField(String id, int max, int tab, int x, int y, int width) {
		return addField(id, max, tab, x, y, width, false, null);
	}

	public void update() {
		for (Frame d : frames.values()) {
			d.updateComponent(tick);
		}
		for (Button b : buttons.values()) {
			b.updateComponent(tick);
		}
		for (Field t : fields.values()) {
			t.updateComponent(tick);
		}
		for (CheckBox c : checkBoxes.values()) {
			c.updateComponent(tick);
		}
		for (ListBox c : listBoxes.values()) {
			c.updateComponent(tick);
		}
	}

	public void render() {

		if (centered) {
			this.x -= (width / 2);
			this.y -= (height / 2);
		}
		if (parent != null && relative) {
			this.x += parent.x;
			this.y += parent.y;
		}
		TextureRegion[] framePiece = Bearplane.assets.framePiece;


		if (useAbsDraw) {

			if (useBackground) {
				scene.drawAbs(Bearplane.assets.bg[1], x + 4, y + 4, width - 8, height - 8, 0, 0, width, height);
			}
			if (useFrame) {
				// draw top left
				scene.drawRegionAbs(framePiece[0], x, y, false, 0, 1);
				// top right
				scene.drawRegionAbs(framePiece[1], x + width - 32, y, false, 0, 1);
				// bottom left
				scene.drawRegionAbs(framePiece[2], x, y + height - 32, false, 0, 1);
				// bottom right
				scene.drawRegionAbs(framePiece[3], x + width - 32, y + height - 32, false, 0, 1);

				// left side
				for (int b = 32; b < height - 32; b += 32) {
					scene.drawRegionAbs(framePiece[4], x, y + b, false, 0, 1);
				}
				// right side
				for (int b = 32; b < height - 32; b += 32) {
					scene.drawRegionAbs(framePiece[5], x + width - 32, y + b, false, 0, 1);
				}
				// top side
				for (int b = 32; b < width - 32; b += 32) {
					scene.drawRegionAbs(framePiece[6], x + b, y, false, 0, 1);
				}
				// bottom side
				for (int b = 32; b < width - 32; b += 32) {
					scene.drawRegionAbs(framePiece[7], x + b, y + height - 32, false, 0, 1);
				}
			}
		} else {

			if (useBackground) {
				scene.draw(Bearplane.assets.bg[1], x + 4, y + 4, width - 8, height - 8, 0, 0, width, height);
			}
			if (useFrame) {
				// draw top left
				scene.drawRegion(framePiece[0], x, y, false, 0, 1);
				// top right
				scene.drawRegion(framePiece[1], x + width - 32, y, false, 0, 1);
				// bottom left
				scene.drawRegion(framePiece[2], x, y + height - 32, false, 0, 1);
				// bottom right
				scene.drawRegion(framePiece[3], x + width - 32, y + height - 32, false, 0, 1);

				// left side
				for (int b = 32; b < height - 32; b += 32) {
					scene.drawRegion(framePiece[4], x, y + b, false, 0, 1);
				}
				// right side
				for (int b = 32; b < height - 32; b += 32) {
					scene.drawRegion(framePiece[5], x + width - 32, y + b, false, 0, 1);
				}
				// top side
				for (int b = 32; b < width - 32; b += 32) {
					scene.drawRegion(framePiece[6], x + b, y, false, 0, 1);
				}
				// bottom side
				for (int b = 32; b < width - 32; b += 32) {
					scene.drawRegion(framePiece[7], x + b, y + height - 32, false, 0, 1);
				}
			}
		}
		if (centered) {
			this.x += (width / 2);
			this.y += (height / 2);
		}
		if (parent != null && relative) {
			this.x -= parent.x;
			this.y -= parent.y;
		}
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
		for (ListBox c : listBoxes.values()) {
			c.renderComponent();
		}
	}

}
