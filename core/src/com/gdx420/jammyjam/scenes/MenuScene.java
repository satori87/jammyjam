package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.gui.Dialog;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.Assets;
import com.gdx420.jammyjam.core.JammyJam;

public class MenuScene extends Scene {
	

	public static String status = "";

	//Dialog d;
	
	public MenuScene() {

	}

	public void start() {
		super.start();
		int hw = JammyJam.GAME_WIDTH / 2;
		int hh = JammyJam.GAME_HEIGHT / 2;
		int y = 64;
		//d = new Dialog(this, "test", 400,"woah man", new String[]{"Good, good","I dun lik it","Woah no way bruh"},new String[]{"0","1","2"});
		//d.start(tick);
		addLabel("status", hw, hh + 256, 2f, "", Color.WHITE, true);
		addFrame("frame", hw, hh + y, 288, 300, true, true);

		addLabel("title", hw, hh - 200, 4f, JammyJam.game.getGameName(), Color.WHITE, true);

		addButton("play", hw,  hh - 96 + y, 256, 48, "Play Demo");
		//addButton("lol", hw, hh - 32 + y, 256, 48, "Edit Map");
		addButton("options", hw, hh + 32 + y, 256, 48, "Controls");
		addButton("quit", hw, hh + 96 + y, 256, 48, "Quit");
		if(JammyJam.musicLoop != null)
			JammyJam.musicLoop.stop();
		JammyJam.musicLoop = Assets.sounds.get("Dream_Music1");
		JammyJam.musicLoop.loop();

		
	}

	public void update() {
		//d.update(tick);
	}

	public void render() {
		//d.render();
		getLabel("status").text = status;
		
		
	}

	@Override
	public void buttonPressed(String id) {	
		Log.debug("pressed " + id);
		switch (id) {
		case "play":
			//Scene.change("play");
			Scene.change("sleepPlayScene");
			if(JammyJam.musicLoop != null)
				JammyJam.musicLoop.stop();
			JammyJam.musicLoop = Assets.sounds.get("Dream_Music2");
			JammyJam.musicLoop.loop();		
			break;
		case "lol":
			Scene.change("edit");
			break;
		case "options":
			Scene.change("options");
			break;
		case "quit":
			System.exit(0);
			break;
		}
	}

	@Override
	public void enterPressedInField(String id) {
		
	}

	@Override
	public void enterPressedInList(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listChanged(String id, int sel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDown(int x, int y, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(int x, int y, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkBox(String id) {
		// TODO Auto-generated method stub
		
	}

}
