package com.bg.ody.client.core;

import com.bg.bearplane.engine.BearEssentials;
import com.bg.bearplane.engine.BearNecessities;

public class Assets extends BearNecessities implements BearEssentials {

	public void preload() {		
		//add any customer preloading here
		loadAllPNGFromDir("assets");		
	}

	public void load() {
		//add any custom loading here
	}
}
