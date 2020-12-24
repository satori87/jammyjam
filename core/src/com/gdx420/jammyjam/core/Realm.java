package com.gdx420.jammyjam.core;

import java.io.File;
import com.bg.bearplane.engine.BaseRealm;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.engine.Util;

public class Realm extends BaseRealm {

	public static Realm realm;

	public static MapData[] mapData = new MapData[Shared.NUM_MAPS];
	
	public static int curMap = 0;
	
	public Map map;
	
	public Realm(Assets assets) {
		super(assets);
		realm = this;
		File f;
		
		for(int i = 0; i < Shared.NUM_MAPS; i++) {
			loadMap(i);
			//mapData[i] = new MapData();
		}
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void load() {

	}
	
	@Override
	public void addFX() {
		
	}
	
	public static void loadMap(int i) {
		try {
			mapData[i] = (MapData) Util.importJSON("maps/map" + i + ".map", MapData.class);
			if(mapData[i] != null) {
				return;
			}
		} catch (Exception e) {
			Log.error(e);			
		}
		mapData[i] = new MapData();
	}
	
	public static MapData getNeighbor(int m, int d) {
		if (m >= 0 && m < Shared.NUM_MAPS) {
			MapData md = mapData[m];
			if (d < 4) {
				if (md.exit[d] >= 0 && md.exit[d] < Shared.NUM_MAPS) {
					return mapData[md.exit[d]];
				}
			} else {
				// add support for corners here
			}
		}
		return null;
	}

}
