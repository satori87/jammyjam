package com.bg.bearplane.gui;

public class ArrowButton extends Button {

	int dir = 0;

	public ArrowButton(Scene scene, String id, int x, int y, int dir) {
		super();
		this.scene = scene;
		this.id = id;
		this.x = x;
		this.y = y;
		this.dir = dir;

	}

}
