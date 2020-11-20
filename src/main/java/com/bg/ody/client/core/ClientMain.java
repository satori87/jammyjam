package com.bg.ody.client.core;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bg.bearplane.engine.BearGame;
import com.bg.bearplane.engine.Bearable;
import com.bg.bearplane.engine.Log;

public class ClientMain {

	public static void main(String[] args) {
		try {
			Log.init(args);
			// You have to make 3 objects, Assets, game, and network register objects
			// (optional)
			// Network register object should be same as used for server
			Assets assets = new Assets();
			Bearable game = new JammyJam(assets);
			BearGame bearGame = new BearGame(game, assets, null);


			Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			
			LwjglApplicationConfiguration c = BearGame.getApplicationConfiguration(BearGame.game.getGameName(),
					dimension.width, dimension.height, false, false);
			c.addIcon("assets/bearnecessities/tod.png", FileType.Local);
			new LwjglApplication(bearGame, c);
			List<String> arg = new ArrayList<String>();
			for (String s : args) {
				arg.add(s);
			}

		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}

}