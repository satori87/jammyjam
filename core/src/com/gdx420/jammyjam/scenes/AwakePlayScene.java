package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.Input.Keys;
import com.bg.bearplane.gui.Dialog;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.JammyJam;
import java.time.LocalTime;

public class AwakePlayScene extends PlayScene {
	
	public AwakePlayScene() {

	}

	
	public void start() {
		super.start();
		JammyJam.game.awakeTimeManager.setGameClock(LocalTime.of(8, 0));
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

		if(input.keyDown[Keys.SPACE]) {
			int oldMultiplier = JammyJam.game.awakeTimeManager.gameTimeMultiplier;
			JammyJam.game.awakeTimeManager.gameTimeMultiplier = 10;
			JammyJam.game.awakeTimeManager.tickForward();
			JammyJam.game.awakeTimeManager.gameTimeMultiplier = oldMultiplier;
		}
		
	}
	
	void changeToSleepScene() {
		Scene.change("sleepPlayScene");
		JammyJam.game.sleepTimeManager.setGameClock(startSleepTime);
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
