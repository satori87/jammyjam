package com.gdx420.jammyjam.core;

import com.bg.bearplane.engine.BaseAssets;

public class Assets extends BaseAssets {

	public void preload() {		
		//add any customer preloading here
		preloadAllPNGFromDir("assets");
		preloadAllPNGFromDir("assets/tiles");
		preloadAllPNGFromDir("assets/tiles/0");
		preloadAllPNGFromDir("assets/tiles/1");
		preloadAllPNGFromDir("assets/tiles/2");
		preloadAllPNGFromDir("assets/tiles/3");
		preloadAllPNGFromDir("assets/tiles/4");
		preloadAllPNGFromDir("assets/tiles/5");
		preloadAllPNGFromDir("assets/tiles/6");
		
		preloadAllPNGFromDir("assets/editor");
		
		
	}

	public void load() {
		//add any custom loading here
	}
}
