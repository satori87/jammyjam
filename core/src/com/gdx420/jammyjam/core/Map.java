package com.gdx420.jammyjam.core;

public class Map {
	
	
	int id;

	
	public Map(int id) {
		this.id = id;
	}
	
	public MapData data() {
		return Realm.mapData[id];
	}
	
	
	
}
