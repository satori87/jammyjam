package com.bg.ody.client.core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.bg.bearplane.engine.BearGame;
import com.bg.bearplane.engine.Bearable;
import com.bg.bearplane.engine.Log;

public class ClientMain {

	public static void main(String[] args) {
		try {
			Log.init(args);
			Bearable game = new JammyJam();
			BearGame bearGame = new BearGame(game, null);					
			new LwjglApplication(bearGame, bearGame.getApplicationConfiguration());
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}

}