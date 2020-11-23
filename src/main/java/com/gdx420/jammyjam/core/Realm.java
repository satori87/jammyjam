package com.gdx420.jammyjam.core;

import com.bg.bearplane.engine.BaseRealm;

public class Realm extends BaseRealm {

	public static Realm realm;

	public Realm(Assets assets) {
		super(assets);
		realm = this;
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

}
