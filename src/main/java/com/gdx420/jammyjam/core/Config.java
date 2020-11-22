package com.gdx420.jammyjam.core;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.bg.bearplane.engine.BaseConfig;

public class Config extends BaseConfig implements Serializable {

	public int volMusic = 60;
	public int volSound = 60;
	public String name = "Gdx";
	
	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
		
	}

}
