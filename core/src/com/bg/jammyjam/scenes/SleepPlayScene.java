package com.bg.jammyjam.scenes;

import java.time.LocalTime;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.bg.bearplane.gui.Scene;
import com.bg.jammyjam.core.Assets;
import com.bg.jammyjam.core.DialogData;
import com.bg.jammyjam.core.JammyJam;
import com.bg.jammyjam.core.Realm;
import com.bg.jammyjam.core.Shared;
import com.badlogic.gdx.graphics.Color;

public class SleepPlayScene extends PlayScene {
	
	public static Sound musicLoop = null;
	
	public SleepPlayScene() {

	}

	public void start() {
		super.start();
		
		JammyJam.game.sleepTimeManager.setGameClock(LocalTime.of(22, 0));
		DialogData data = new DialogData("Sleep Intro", "The night is a motionless plum; dark, moist, quiet... too quiet. Dame next door been missing weeks and every eye has turned blind.");
		PlayScene.dialogQueue.add(data);
		data = new DialogData("Sleep Intro2", "The air drips off the windows and calmness drains from me. This is my bedroom, but something is off... Hmm... I must be asleep. But what is different?");
		PlayScene.dialogQueue.add(data);
		data = new DialogData("Sleep Intro3", "And then I remember Jan. A solid oak hardens inside me. I must help find her... or what's left of her. The night is young and I'm on the case...");
		PlayScene.dialogQueue.add(data);
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
								
		if(input.keyDown[Keys.TAB]) {
			int oldMultiplier = JammyJam.game.sleepTimeManager.gameTimeMultiplier;
			JammyJam.game.sleepTimeManager.gameTimeMultiplier = 10;
			JammyJam.game.sleepTimeManager.tickForward();
			JammyJam.game.sleepTimeManager.gameTimeMultiplier = oldMultiplier;
		}
	}

	void changeToAwakeScene() {
		Scene.change("awakePlayScene");
		JammyJam.game.awakeTimeManager.setGameClock(startAwakeTime);
		changeMap(Shared.START_MAP);
		player().x = Shared.START_X;
		player().y = Shared.START_Y;
		if(JammyJam.musicLoop != null)
			JammyJam.musicLoop.stop();
		JammyJam.musicLoop = Assets.sounds.get("Dream_Music1");
		JammyJam.musicLoop.loop();
	}

	public void render() {
		forceCol = new Color(103f/255f, 89f/255f, 209f/255f,1);
		super.render();
		forceCol = null;
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
