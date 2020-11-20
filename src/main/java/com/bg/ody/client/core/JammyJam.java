package com.bg.ody.client.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.engine.BearTool;
import com.bg.bearplane.engine.Bearable;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.engine.Timer;
import com.bg.bearplane.gui.Scene;
import com.bg.bearplane.net.BearNet;
import com.bg.bearplane.net.TCPClient;
import com.bg.bearplane.net.packets.DisconnectError;
import com.bg.bearplane.net.packets.Logon;
import com.bg.bearplane.net.packets.PingPacket;
import com.bg.ody.client.scenes.MenuScene;
import com.bg.ody.client.scenes.OptionsScene;
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


	public JammyJam(Assets a) {
		super();
		Log.info("Jammy Jam Initializing");
		try {
			game = this;
			assets = a;
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

}