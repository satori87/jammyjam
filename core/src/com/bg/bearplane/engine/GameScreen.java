package com.bg.bearplane.engine;

import com.badlogic.gdx.Screen;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.DialogQueue;

public class GameScreen implements Screen {

	public Bearable game;

	long tick;

	public GameScreen(Bearable game) {
		super();
		this.game = game;
	}

	@Override
	public void render(float delta) {
		try {
			// libGDX calls render only so we must distinguish
			// between update and render ourselves
			// ignore delta, we use our own timing system in Scene
			tick = System.currentTimeMillis();
			game.setTick(tick);
			for (Timer t : Bearplane.timers.values()) {
				t.update(tick);
			}
			DialogQueue.update();
			Scene.updateScene();
			game.getRealm().tick = tick;
			game.getRealm().updateBase();
			game.getRealm().update();
			game.update();
			Scene.renderScene();
		} catch (Exception e) {
			Log.error(e);
		}
	}

	@Override
	public void resize(int width, int height) {
		try {
			Scene.setupScreen(game.getGameWidth(), game.getGameHeight());
		} catch (Exception e) {
			Log.error(e);
		}
	}

	@Override
	public void show() {
		game.show();
	}

	@Override
	public void hide() {
		game.hide();
	}

	@Override
	public void pause() {
		game.pause();
	}

	@Override
	public void resume() {
		game.resume();
	}

	@Override
	public void dispose() {
		// Leave blank
	}
}
