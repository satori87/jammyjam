package com.bg.ody.client.core;

import com.bg.bearplane.engine.Bearable;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.engine.Timer;
import com.bg.bearplane.gui.Scene;
import com.bg.ody.client.scenes.MenuScene;
import com.bg.ody.client.scenes.OptionsScene;
import com.bg.ody.client.scenes.PlayScene;
import com.bg.ody.client.scenes.UpdateScene;
import com.bg.ody.shared.Shared;
import com.esotericsoftware.kryo.util.IntMap;

public class JammyJam implements Bearable {

	public static JammyJam game;
	public static Assets assets;
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
		super();
		Log.info("Jammy Jam Initializing");
		try {
			game = this;
			assets = new Assets();
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
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
		return Shared.GAME_WIDTH;
	}

	@Override
	public int getGameHeight() {
		return Shared.GAME_HEIGHT;
	}

	@Override
	public String getGameName() {
		return Shared.GAME_NAME;
	}
	
	public String getClientVersion() {
		return Shared.CLIENT_VERSION;
	}
	
	public String getEffectsPath() {
		return Shared.EFFECTS_PATH;
	}
	
	public String getNecessitiesPath() {
		return Shared.NECESSITIES_PATH;
	}
	
	public boolean isFullscreen() {
		return Shared.FULLSCREEN;
	}
	
	public boolean isFauxFullscreen() {
		return Shared.FAUX_FULLSCREEN;
	}
	
	public boolean isResizable() {
		return Shared.RESIZABLE;
	}
	
	public boolean isvSync() {
		return Shared.ISVSYNC;
	}
	
	public Assets getAssets() {
		return assets;
	}
	
	public Object getNetwork() {
		return null;
	}
	
}
