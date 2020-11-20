package com.bg.ody.client.core;

import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.engine.BearTool.Position;
import com.bg.bearplane.gui.Scene;

public class Floater {
	public float x;
	public float y;
	public float initY;
	public float speed;
	public String text;
	public Color col;
	public Color fadeCol;
	public boolean active;
	public float alpha;
	public int dist;
	public int fadedist;
	public long aliveAt;
	public Position e;
	
	int mx = 0;

	public Floater(Position e, int x, int y, String text, Color col, long aliveAt) {
		this.speed = 0.5f;
		this.fadeCol = Color.WHITE;
		this.active = true;
		this.alpha = 1.0f;
		this.dist = 32;
		this.fadedist = 18;
		this.aliveAt = 0L;
		this.x = (float) x;
		this.y = (float) y;
		this.initY = (float) y;
		this.text = text;
		this.col = col;
		this.aliveAt = aliveAt;
		this.e = e;
	}

	public void update(long tick) {
		if (tick < aliveAt) {
			return;
		}
		y -= speed;
		float diff = initY - y;
		if (diff >= dist) {
			active = false;
		} else if (diff >= fadedist) {
			alpha -= speed / diff;
		}
	}

	public void render(long tick) {
		if (tick < aliveAt || !active) {
			return;
		}
		// col.a = alpha;
		Color c = new Color(col.r, col.g, col.b, alpha);

		if (e == null) {
			return;
		}
		Scene.scene.drawFont(0, e.x + Math.round(x), e.y + Math.round(y), text, true, 1.4f, c);
	}

}
