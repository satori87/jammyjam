package com.gdx420.jammyjam.core;

import java.io.Serializable;

import com.bg.bearplane.engine.Util;

public class Tile implements Serializable {
	private static final long serialVersionUID = -280115424790111828L;
	public int[] set = new int[7];
	public int[] tile = new int[7];
	public int[] shiftX = new int[7];
	public int[] shiftY = new int[7];
	public int[] att = new int[2];
	public int[][] attData = new int[2][10];
	public boolean[] wall = new boolean[4];

	public Tile() {

	}

	public Tile(int x, int y) {

	}


}
