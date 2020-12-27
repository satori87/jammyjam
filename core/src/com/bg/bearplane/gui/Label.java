package com.bg.bearplane.gui;

import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.engine.Util;

public class Label extends Component {

	public boolean blinking = false;
	public Color blinkCol = Color.RED;
	public boolean blink = false;
	public int blinkterval = 300;
	public long blinkStamp = 0;
	
	public boolean useAbsDraw = false;
	
	public boolean wrap = false;
	public int wrapw = 0;

	public float scale = 1.0f;
	public Color color = Color.WHITE;

	public Label(Scene scene, String id, int x, int y, float scale, String text, Color color, boolean centered) {
		super(scene,id,x,y);
		this.scale = scale;
		this.color = color;
		this.text = text;
		this.centered = centered;
	}

	public void blink(Color c, int b) {
		blinkCol = c;
		blinking = true;
		blinkterval = b;
	}

	public void render() {
		// render thyself, peasant
		Color c = color;
		if (blinking && blink) {
			c = blinkCol;
		}
		
		if(useAbsDraw) {
			
			if (wrap) {
				int u = 0;
				for (String b : Util.wrapText(2, wrapw, text)) {
					scene.drawFontAbs(0, x, y + u * 30, b, false, scale, Color.WHITE);
					u++;
				}
			} else {
				scene.drawFontAbs(0, x, y, text, centered, scale, c);
			}
			
		} else {
		
			if (wrap) {
				int u = 0;
				for (String b : Util.wrapText(2, wrapw, text)) {
					scene.drawFont(0, x, y + u * 30, b, false, scale, Color.WHITE);
					u++;
				}
			} else {
				scene.drawFont(0, x, y, text, centered, scale, c);
			}
		
		}

	}

	public void update() {
		if (tick > blinkStamp) {
			blink = !blink;
			blinkStamp = tick + blinkterval;
		}
	}
}
