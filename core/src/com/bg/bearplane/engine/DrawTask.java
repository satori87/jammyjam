package com.bg.bearplane.engine;

import com.badlogic.gdx.graphics.Color;

public class DrawTask {
	public int set = 0;
	public int num = 0;
	public int x = 0;
	public int y = 0;
	public int sx = 0;
	public int sy = 0;
	public int w = 0;
	public int h = 0;
	public int i = 0;
	public int f = 0;
	public int type = 0;
	public float size = 1.0f;
	public String text = "";
	public Color col = Color.WHITE;
	public String tex = "";
	float scale = 0f;

	public DrawTask(int i, int set, int tile, int x, int y) {
		this.i = i;
		this.set = set;
		this.num = tile;
		this.x = x;
		this.y = y;
		type = 0;
	}

	public DrawTask(int i, int set, int sprite, int f, int x, int y) {
		this.i = i;
		this.set = set;
		this.num = sprite;
		this.f = f;
		this.x = x;
		this.y = y;
		type = 1;
	}

	public DrawTask(int i, String text, float size, Color color, int x, int y) {
		this.i = i;
		type = 2;
		this.text = text;
		this.size = size;
		this.x = x;
		this.y = y;
		col = color;
	}

	public DrawTask(int i, String tex, int dx, int dy, int sx, int sy, int w, int h) {
		type = 3;
		this.i = i;
		this.tex = tex;
		this.x = dx;
		this.y = dy;
		this.sx = sx;
		this.sy = sy;
		this.w = w;
		this.h = h;
	}
	
	public DrawTask(int i, int id, int dx, int dy, float scale) {
		type = 4;
		this.i = i;
		f = id;
		x = dx;
		y = dy;
		this.scale = scale;
	}


}
