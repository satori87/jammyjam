package com.gdx420.jammyjam.core;

import java.io.Serializable;

import com.bg.bearplane.engine.Util;

public class MapData implements Serializable {

	private static final long serialVersionUID = -1260740057557047017L;
	public int version = 0;
	public Tile[][] tile = new Tile[Shared.MAP_WIDTH][Shared.MAP_WIDTH];

	int mmx = 0;
	int mmy = 0;
	int cx = 0;
	int cy = 0;
	int px = 0;
	int py = 0;
	int p = 0;
	int[] edge = new int[4];
	int[] corner = new int[4];
	
	public int exit[] = new int[4];

	public MapData() {
		for (int x = 0; x < Shared.MAP_WIDTH; x++) {
			for (int y = 0; y < Shared.MAP_WIDTH; y++) {
				tile[x][y] = new Tile(x, y);
				tile[x][y].tile[0] = Util.randInt(1, 20);
			}
		}
	}


	public Tile getTile(int mx, int my) {
		if (inBounds(mx, my)) {
			return tile[mx][my];
		}
		return new Tile();
	}

	public static boolean inBounds(int x, int y) {
		return (x >= 0 && y >= 0 && x < Shared.MAP_WIDTH && y < Shared.MAP_WIDTH);
	}


	public Tile getNeighbor(int mx, int my, int dir) {
		if (dir == 0) {
			my--;
		} else if (dir == 1) {
			my++;
		} else if (dir == 2) {
			mx--;
		} else if (dir == 3) {
			mx++;
		} else if (dir == 4) {
			mx--;
			my--;
		} else if (dir == 5) {
			mx++;
			my--;
		} else if (dir == 6) {
			mx--;
			my++;
		} else if (dir == 7) {
			mx++;
			my++;
		}
		if (inBounds(mx, my)) {
			return tile[mx][my];
		}
		return null;
	}

}
