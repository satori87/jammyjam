package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.bg.bearplane.gui.DialogDisplay;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.Assets;
import com.gdx420.jammyjam.core.AudioManager;
import com.gdx420.jammyjam.core.DialogData;
import com.gdx420.jammyjam.core.DialogQueue;
import com.gdx420.jammyjam.core.JammyJam;
import com.gdx420.jammyjam.core.Realm;
import com.gdx420.jammyjam.core.Shared;

import java.time.LocalTime;

public class AwakePlayScene extends PlayScene {
	
	public AwakePlayScene() {

	}

	
	public void start() {
		super.start();
		JammyJam.game.awakeTimeManager.setGameClock(LocalTime.of(8, 0));		

		JammyJam.game.sleepTimeManager.setGameClock(LocalTime.of(22, 0));
		DialogData data = new DialogData("Awake Intro", "What a strange dream. It wasn't real... or was it? Somehow I seemed to know things... things I shouldn't have known.");
		DialogQueue.add(data);
		data = new DialogData("Awake Intro2", "It really felt like I was a private eye... I think I should follow up on what I dreamt about. Maybe there are still some clues out there.");
		DialogQueue.add(data);
		data = new DialogData("Awake Intro3", "Where can I find evidence of Jan's fate? Perhaps her house... I'll start next door. Can't hurt to ask around...");
		DialogQueue.add(data);
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
		AudioManager.playSleepMusic();
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
