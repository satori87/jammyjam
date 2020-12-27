package com.gdx420.jammyjam.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.bg.bearplane.engine.Bearable;
import com.bg.bearplane.engine.Bearplane;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.engine.Util;
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
	public List<NonPlayableCharacter> npcList = new ArrayList<NonPlayableCharacter>();
	public List<Item> loadedItems = new ArrayList<Item>();

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
		
		loadItems();
		spawnItems(Realm.curMap);
		
		loadNPCs();		
		spawnNPCs(Realm.curMap);		
		
	}
	
	public void loadNPCs() {
		Path dir = Paths.get(new File("").getAbsolutePath() + "/scripts/NPCs/");
		
		DirectoryStream<Path> stream;
		try {
			stream = Files.newDirectoryStream(dir);
			for (Path file: stream) {
		        System.out.println("Loading NPC: " + file.getFileName());
				npcList.add((NonPlayableCharacter) Util.importJSON("/scripts/NPCs/" + file.getFileName(), NonPlayableCharacter.class));
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	public void loadItems() {
		Path dir = Paths.get(new File("").getAbsolutePath() + "/scripts/Items/");
		
		DirectoryStream<Path> stream;
		try {
			stream = Files.newDirectoryStream(dir);
			for (Path file: stream) {
		        System.out.println("Item NPC: " + file.getFileName());
		        loadedItems.add((Item) Util.importJSON("scripts/Items/" + file.getFileName(), Item.class));
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		assets.dispose();
	}

	public void spawnNPCs(int currentMap) {
		
		for (NonPlayableCharacter npc : npcList) {
			npc.onScreen = false;
		}
		
		for(int x = 0; x < Shared.MAP_WIDTH; x++)
			for(int y = 0; y < Shared.MAP_HEIGHT; y++)
				if(Realm.mapData[currentMap].tile[x][y].att[0] == Shared.Attributes.NPC_SPAWN.ordinal() 
					|| Realm.mapData[currentMap].tile[x][y].att[1] == Shared.Attributes.NPC_SPAWN.ordinal())
				{
					npcList.get(0).onScreen = true;
					npcList.get(0).x = x * 32;
					npcList.get(0).y = y * 32;
				}
	}

	public void spawnItems(int currentMap) {
		
		for (Item item : loadedItems) {
			item.onScreen = false;
		}
		
		for(int x = 0; x < Shared.MAP_WIDTH; x++)
			for(int y = 0; y < Shared.MAP_HEIGHT; y++)
				if(Realm.mapData[currentMap].tile[x][y].att[0] == Shared.Attributes.ITEM.ordinal() 
					|| Realm.mapData[currentMap].tile[x][y].att[1] == Shared.Attributes.ITEM.ordinal())
				{
					for (Item item : loadedItems) {
						if((Realm.mapData[currentMap].tile[x][y].attStr[0] != null
								&& item.name.compareTo(Realm.mapData[currentMap].tile[x][y].attStr[0]) == 0)
							||(Realm.mapData[currentMap].tile[x][y].attStr[1] != null
								&& item.name.compareTo(Realm.mapData[currentMap].tile[x][y].attStr[1]) == 0)) {
							System.out.println(Realm.mapData[currentMap].tile[x][y].attStr[0]);
							System.out.println(Realm.mapData[currentMap].tile[x][y].attStr[1]);
							item.onScreen = true;
							loadedItems.get(0).x = x * 32;
							loadedItems.get(0).y = y * 32;
						}
					}				
				}
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
