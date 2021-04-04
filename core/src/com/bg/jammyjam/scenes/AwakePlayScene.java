package com.bg.jammyjam.scenes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.bg.bearplane.gui.Dialog;
import com.bg.bearplane.gui.Scene;
import com.bg.jammyjam.core.Assets;
import com.bg.jammyjam.core.DialogData;
import com.bg.jammyjam.core.JammyJam;
import com.bg.jammyjam.core.Realm;
import com.bg.jammyjam.core.Shared;

import java.time.LocalTime;

public class AwakePlayScene extends PlayScene {
	
	public AwakePlayScene() {

	}

	
	public void start() {
		super.start();
		JammyJam.game.awakeTimeManager.setGameClock(LocalTime.of(8, 0));		

		JammyJam.game.sleepTimeManager.setGameClock(LocalTime.of(22, 0));
		DialogData data = new DialogData("Awake Intro", "What a strange dream. It wasn't real... or was it? Somehow I seemed to know things... things I shouldn't have known.");
		PlayScene.dialogQueue.add(data);
		data = new DialogData("Awake Intro2", "It really felt like I was a private eye... I think I should follow up on what I dreamt about. Maybe there are still some clues out there.");
		PlayScene.dialogQueue.add(data);
		data = new DialogData("Awake Intro3", "Where can I find evidence of Jan's fate? Perhaps her house... I'll start next door. Can't hurt to ask around...");
		PlayScene.dialogQueue.add(data);
	}

	public void update() {
		super.update();
		checkKeys();

		if(JammyJam.game.awakeTimeManager.getCurrentGameTime().compareTo(startSleepTime) >= 0) {
			changeToSleepScene();
		}
	}
	
	void checkKeys() {
		super.checkKeys();

		if(input.keyDown[Keys.TAB]) {
			int oldMultiplier = JammyJam.game.awakeTimeManager.gameTimeMultiplier;
			JammyJam.game.awakeTimeManager.gameTimeMultiplier = 10;
			JammyJam.game.awakeTimeManager.tickForward();
			JammyJam.game.awakeTimeManager.gameTimeMultiplier = oldMultiplier;
		}
		
	}
	
	void changeToSleepScene() {
		Scene.change("sleepPlayScene");
		JammyJam.game.sleepTimeManager.setGameClock(startSleepTime);
		
		changeMap(Shared.START_MAP);
		player().x = Shared.START_X;
		player().y = Shared.START_Y;
		if(JammyJam.musicLoop != null)
			JammyJam.musicLoop.stop();
		JammyJam.musicLoop = Assets.sounds.get("Dream_Music2");
		JammyJam.musicLoop.loop();		
	}

	public void render() {
		super.render();
		drawFontAbs(0, JammyJam.GAME_WIDTH / 2, JammyJam.GAME_HEIGHT - 20, JammyJam.game.awakeTimeManager.getGameTimeString() + " (Awake)", true, 3f);
	}

	@Override
	public void buttonPressed(String id) {		
		super.buttonPressed(id);
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
