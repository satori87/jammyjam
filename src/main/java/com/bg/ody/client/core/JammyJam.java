package com.bg.ody.client.core;

import com.bg.bearplane.engine.Bearable;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.engine.Timer;
import com.bg.bearplane.gui.Scene;
import com.bg.ody.client.scenes.MenuScene;
import com.bg.ody.client.scenes.OptionsScene;
import com.bg.ody.client.scenes.PlayScene;
import com.bg.ody.client.scenes.UpdateScene;
import com.esotericsoftware.kryo.util.IntMap;

public class JammyJam implements Bearable {

	public static JammyJam game;
	public static Assets assets = new Assets();
	public Realm realm = new Realm();

	// timing
	long tick = 0;
	public IntMap<Timer> timers = new IntMap<Timer>();

	// state variables
	public boolean playing = false;

	// scenes
	public static OptionsScene optionsScene = new OptionsScene();
	public static UpdateScene updateScene = new UpdateScene();
	public static PlayScene playScene = new PlayScene();


	public JammyJam() {
		game = this;
	}
	
	public void create() {
		Log.info("Jammy Jam Initializing");
	}

	@Override
	public void addTimers() {
		try {
			timers.put(250, new Timer(this, 250));
			timers.put(1000, new Timer(this, 1000));
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
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
			System.exit(0);
		}
	}

	void secondTimer() {

	}

	@Override
	public void doTimers(long tick) {
		try {
			this.tick = tick;
			for (Timer t : timers.values()) {
				t.update(tick);
			}
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}

	@Override
	public void update() {
		try {
			Realm.realm.update(tick);
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}

	@Override
	public void addScenes() {
		Scene.addScene("menu", new MenuScene());
		Scene.addScene("update", updateScene);
		Scene.addScene("options", optionsScene);
		Scene.addScene("play", playScene);
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
		return Prefs.GAME_WIDTH;
	}

	@Override
	public int getGameHeight() {
		return Prefs.GAME_HEIGHT;
	}

	@Override
	public String getGameName() {
		return Prefs.GAME_NAME;
	}
	
	public String getClientVersion() {
		return Prefs.CLIENT_VERSION;
	}
	
	public String getEffectsPath() {
		return Prefs.EFFECTS_PATH;
	}
	
	public String getAssetsPath() {
		return Prefs.ASSETS_PATH;
	}	
	
	public int getDisplayMode() {
		return Prefs.DISPLAY_MODE;
	}
	
	public boolean isResizable() {
		return Prefs.RESIZABLE;
	}
	
	public int getWindowWidth() {
		return Prefs.WINDOW_WIDTH;
	}
	
	public int getWindowHeight() {
		return Prefs.WINDOW_HEIGHT;
	}
	
	public boolean isvSync() {
		return Prefs.ISVSYNC;
	}
	
	public Assets getAssets() {
		return assets;
	}
	
	public Object getNetwork() {
		return null;
	}
	
	public boolean isRelease() {
		return Prefs.IS_RELEASE;
	}
	
}
