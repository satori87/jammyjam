package com.bg.ody.client.core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.bg.bearplane.engine.Bearplane;
import com.bg.bearplane.engine.Log;

public class ClientMain {

	public static void main(String[] args) {
		try {			
			Bearplane bearGame = new Bearplane(new JammyJam(), args);					
			new LwjglApplication(bearGame, bearGame.getApplicationConfiguration());
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}

}