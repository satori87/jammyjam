package com.gdx420.jammyjam.core;

import com.bg.bearplane.engine.Bearable;
import com.bg.bearplane.engine.Bearplane;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.scenes.AwakePlayScene;
import com.gdx420.jammyjam.scenes.EditMapScene;
import com.gdx420.jammyjam.scenes.MenuScene;
import com.gdx420.jammyjam.scenes.OptionsScene;
import com.gdx420.jammyjam.scenes.PlayScene;
import com.gdx420.jammyjam.scenes.SleepPlayScene;
import com.gdx420.jammyjam.scenes.UpdateScene;

public class JammyJam implements Bearable {

	public static final boolean IS_RELEASE = false; // change to true for release
	public static final int GAME_WIDTH = 1366;
	public static final int GAME_HEIGHT = 768;
	public static final String GAME_NAME = "Jammy Jam";
	public static final String CLIENT_VERSION = "bananas17";
	public static final String EFFECTS_PATH = "assets/effects";
	public static final String ASSETS_PATH = "assets";
	public static final String CONFIG_FILE = "config.txt";

	public static JammyJam game;
	public static Assets assets = new Assets();
	public Realm realm = new Realm(assets);
	public Config config = new Config();
	public Player player = new Player(300, 200);

	// timing
	long tick = 0;
	public GameTimeManager awakeTimeManager = new GameTimeManager();
	public GameTimeManager sleepTimeManager = new GameTimeManager();
	

	// state variables
	public boolean playing = false;

	// scenes
	public static OptionsScene optionsScene = new OptionsScene();
	public static UpdateScene updateScene = new UpdateScene();
	public static EditMapScene editMapScene = new EditMapScene();
	public static AwakePlayScene awakePlayScene;
	public static SleepPlayScene sleepPlayScene;

	public JammyJam() {
		game = this;
		//config = (Config)Bearplane.loadConfig(CONFIG_FILE, config); this broke somehow
	}

	public void create() {
		Log.info("Jammy Jam Initializing");	
		editMapScene = new EditMapScene();
		awakePlayScene = new AwakePlayScene();
		sleepPlayScene = new SleepPlayScene();
		
	}

	@Override
	public void addTimers() {
		try {
			Bearplane.addTimer(250);
			Bearplane.addTimer(1000);
		} catch (Exception e) {
			Log.error(e);
		}
	}

	@Override
	public void doTimer(int interval) {
		try {
			switch (interval) {
			case 250:
				break;
			case 1000:
				secondTimer();
				break;
			}
		} catch (Exception e) {
			Log.error(e);
		}
	}

	void secondTimer() {
		if(Scene.scene instanceof AwakePlayScene) {
			awakeTimeManager.tickForward();
		} else if(Scene.scene instanceof SleepPlayScene) {
			sleepTimeManager.tickForward();
		}
	}

	@Override
	public void update() {
		try {
			//shouldnt actually need to do a whole lot here :)
			//realm and scene have their updates called for them, there's timers, and most logic is more appropriately done in Realm or Map
		} catch (Exception e) {
			Log.error(e);
		}
	}

	@Override
	public void addScenes() {
		Scene.addScene("menu", new MenuScene());
		Scene.addScene("update", updateScene);
		Scene.addScene("options", optionsScene);
		Scene.addScene("edit", editMapScene);
		Scene.addScene("awakePlayScene", awakePlayScene);
		Scene.addScene("sleepPlayScene",  sleepPlayScene);
	}

	@Override
	public void loaded() {
		realm.load();
		Scene.change("menu");
	}

	@Override
	public void dispose() {
		assets.dispose();
	}

	@Override
	public int getGameWidth() {
		return GAME_WIDTH;
	}

	@Override
	public int getGameHeight() {
		return GAME_HEIGHT;
	}

	@Override
	public String getGameName() {
		return GAME_NAME;
	}

	public String getClientVersion() {
		return CLIENT_VERSION;
	}

	public String getEffectsPath() {
		return EFFECTS_PATH;
	}

	public String getAssetsPath() {
		return ASSETS_PATH;
	}

	public int getDisplayMode() {
		return config.DISPLAY_MODE;
	}

	public boolean isResizable() {
		return config.RESIZABLE;
	}

	public int getWindowWidth() {
		return config.WINDOW_WIDTH;
	}

	public int getWindowHeight() {
		return config.WINDOW_HEIGHT;
	}

	public boolean isvSync() {
		return config.ISVSYNC;
	}

	public Assets getAssets() {
		return assets;
	}

	public Object getNetwork() {
		return null;
	}

	public boolean isRelease() {
		return IS_RELEASE;
	}
	
	public Realm getRealm() {
		return realm;
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void pause() {

		
	}
	
	public void setTick(long tick) {
		this.tick = tick;
	}
	
}
