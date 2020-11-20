package com.bg.ody.client.core;

import com.bg.bearplane.engine.StandardAssets;

public class Assets extends StandardAssets {

	public void preload() {		
		//add any customer preloading here
		preloadAllPNGFromDir("assets");		
	}

	public void load() {
		//add any custom loading here
	}
}
