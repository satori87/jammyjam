package com.gdx420.jammyjam.scenes;

import java.time.LocalTime;

import com.badlogic.gdx.Input.Keys;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.JammyJam;
import com.gdx420.jammyjam.core.Realm;
import com.badlogic.gdx.graphics.Color;

public class SleepPlayScene extends PlayScene {
	
	public SleepPlayScene() {

	}

	public void start() {
		super.start();
		
		JammyJam.game.sleepTimeManager.setGameClock(LocalTime.of(22, 0));
	}

	public void update() {
		super.update();
		checkKeys();
		
		if(JammyJam.game.sleepTimeManager.getCurrentGameTime().compareTo(startSleepTime) < 0 
				&& JammyJam.game.sleepTimeManager.getCurrentGameTime().compareTo(startAwakeTime) >= 0) {
			changeToAwakeScene();
		}
	}
	
	void checkKeys() {
		super.checkKeys();
								
		if(input.keyDown[Keys.SPACE]) {
			int oldMultiplier = JammyJam.game.sleepTimeManager.gameTimeMultiplier;
			JammyJam.game.sleepTimeManager.gameTimeMultiplier = 10;
			JammyJam.game.sleepTimeManager.tickForward();
			JammyJam.game.sleepTimeManager.gameTimeMultiplier = oldMultiplier;
		}
	}

	void changeToAwakeScene() {
		Scene.change("awakePlayScene");
		JammyJam.game.awakeTimeManager.setGameClock(startAwakeTime);
		JammyJam.game.spawnNPCs(Realm.curMap);
		JammyJam.game.spawnItems(Realm.curMap);
		JammyJam.game.spawnStoryPoints(Realm.curMap);
	}

	public void render() {
		super.render();
				
		drawFontAbs(0, JammyJam.GAME_WIDTH / 2, JammyJam.GAME_HEIGHT - 20, JammyJam.game.sleepTimeManager.getGameTimeString() + " (Sleeping)", true, 3f, Color.BLUE);
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
