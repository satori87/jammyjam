package com.gdx420.jammyjam.core;

import java.util.ArrayList;
import java.util.List;

import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.scenes.AwakePlayScene;

public class Player extends Character {

	public List<Item> obtainedItems = new ArrayList<Item>();

	public static final int MAX_FRAMES = 54;
	public static final int MOVE_STEP = 7;
	
	public Player(int _x, int _y) {
		super();
		x = _x;
		y = _y;
	}
	
	public String getSprite(Scene scene) {
		if(scene instanceof AwakePlayScene) {
			return "Guy_1";
		} else {
			return "Guy_Detective";
		}
	}



}
