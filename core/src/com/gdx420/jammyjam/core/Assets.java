package com.gdx420.jammyjam.core;

import com.bg.bearplane.engine.BaseAssets;

public class Assets extends BaseAssets {

	public void preload() {		
		//add any customer preloading here
		preloadAllPNGFromDir("assets");
		preloadAllPNGFromDir("assets/tiles");
		preloadAllPNGFromDir("assets/editor");
		preloadAllPNGFromDir("assets/sprites");
		
		
	}

	public void load() {
		//add any custom loading here
	}
}
