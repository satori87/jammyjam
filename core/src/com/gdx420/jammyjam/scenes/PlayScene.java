package com.gdx420.jammyjam.scenes;


import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Input.Keys;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.gui.Dialog;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.Assets;
import com.gdx420.jammyjam.core.DialogData;
import com.gdx420.jammyjam.core.Item;
import com.gdx420.jammyjam.core.JammyJam;
import com.gdx420.jammyjam.core.MapData;
import com.gdx420.jammyjam.core.NonPlayableCharacter;
import com.gdx420.jammyjam.core.PlotEngine;
import com.gdx420.jammyjam.core.Realm;
import com.gdx420.jammyjam.core.Shared;
import com.gdx420.jammyjam.core.StoryPoint;
import com.gdx420.jammyjam.core.Tile;

public class PlayScene extends LiveMapScene {

	LocalTime startSleepTime = LocalTime.of(22, 0);
	LocalTime startAwakeTime = LocalTime.of(8, 0);
	

	public static Queue<DialogData> dialogQueue = new LinkedList<DialogData>();

	public PlayScene() {

	}

	public void start() {
		super.start();
	}
	public void update() {
		if(dialogToDisplay != null || dialogQueue.size() > 0)
			updateDialog();
		else
			updatePlay();
	}
	
	public void updatePlay() {
		int playerTilePositionX = (JammyJam.game.player.x + 16) / 32;
		int playerTilePositionY = (JammyJam.game.player.y + 16) / 32;
		Tile currentTile = null;
		if (MapData.inBounds(playerTilePositionX, playerTilePositionY)) {
			currentTile = Realm.mapData[Realm.curMap].tile[playerTilePositionX][playerTilePositionY];
		}
		if (currentTile != null) {
			// on an item
			if (currentTile.att[0] == Shared.Attributes.ITEM.ordinal()
					|| currentTile.att[1] == Shared.Attributes.ITEM.ordinal()) {
				for (Item item : JammyJam.game.loadedItems) {
					if ((currentTile.attStr[0] != null && currentTile.attStr[0].compareTo(item.name) == 0)
							|| (currentTile.attStr[1] != null && currentTile.attStr[1].compareTo(item.name) == 0)) {
						PlotEngine.obtainItem(item);
					}
				}
			}

			// next to a plot point
			for (int dir = 0; dir < 8; dir++) {
				Tile neighbor = Realm.mapData[Realm.curMap].getNeighbor(playerTilePositionX, playerTilePositionY, dir);
				if (neighbor != null) {
					if (neighbor.att[0] == Shared.Attributes.STORYPOINT.ordinal()
							|| neighbor.att[1] == Shared.Attributes.STORYPOINT.ordinal()) {
						for(StoryPoint sp : JammyJam.game.storyPoints) {
							if((currentTile.attStr[0] != null && currentTile.attStr[0].compareTo(sp.name) == 0) 
									|| (currentTile.attStr[1] != null && currentTile.attStr[1].compareTo(sp.name) == 0)) {
								PlotEngine.triggerPlot(sp);
							}
						}
					}
				}
			}
		}
	}
	
	// CREATE DIALOG HERE (and update)
	public void updateDialog() {
		if(dialogQueue.size() > 0) {
			DialogData data = dialogQueue.poll();
			String words = data.dialog;
			if(data.npcParent != null)
				words = data.npcParent.name + ": " + words;
			if(data.itemParent != null)
				words = data.itemParent.name + " Found!  " + words;
			dialogToDisplay = new Dialog(this, data.name, 400, words, new String[]{"Hmmm..."},new String[]{"0"});
			dialogToDisplay.start(tick);
		}		
	}

	private static boolean pressedEnter = false;
	void checkKeys() {
		if(dialogToDisplay != null) {
			if (input.keyDown[Keys.ENTER]) {	
				pressedEnter = true;
			}
			else if(pressedEnter){
				dialogToDisplay.choose(dialogToDisplay.choices.get(0));
				dialogToDisplay = null;
				pressedEnter = false;
			}
		}else {
			checkVerticalMovement();
			checkHorizontalMovement();
			checkMapChange();
			if (JammyJam.game.player.x < 5)
				JammyJam.game.player.x = 5;
			if (JammyJam.game.player.y < 5)
				JammyJam.game.player.y = 5;
			if (JammyJam.game.player.x >= Shared.MAP_WIDTH * 32 - 5)
				JammyJam.game.player.x = Shared.MAP_WIDTH * 32 - 5;
			if (JammyJam.game.player.y >= Shared.MAP_WIDTH * 32 - 5)
				JammyJam.game.player.y = Shared.MAP_WIDTH * 32 - 5;
			checkWarp();
		}
		
		if (input.keyDown[Keys.ESCAPE]) {
			Scene.change("menu");
		}
		
		if(input.keyDown[Keys.B]) {
			dialogToDisplay = new Dialog(this, "hey", 400, "heyy", new String[]{"Hmmm..."},new String[]{"0"});
			dialogToDisplay.start(tick);
		}
	}

	void checkWarp() {
		int x = (JammyJam.game.player.x + 16) / 32;
		if(x >= Shared.MAP_WIDTH)
			x = Shared.MAP_WIDTH - 1;
		if(x < 0)
			x = 0;
		int y = (JammyJam.game.player.y + 16) / 32;
		if(y >= Shared.MAP_WIDTH)
			y = Shared.MAP_WIDTH - 1;
		if(y < 0)
			y = 0;
		MapData data = Realm.mapData[Realm.curMap];
		if (data.tile[x][y] != null && data.tile[x][y].att[0] == Shared.Attributes.WARP.ordinal()) {
			changeMap(data.tile[x][y].attData[0][0]);
			JammyJam.game.player.x = data.tile[x][y].attData[0][1] * 32 - 16;
			JammyJam.game.player.y = data.tile[x][y].attData[0][2] * 32 - 16;
		}
	}

	int oldY = 0;
	int oldX = 0;

	void checkVerticalMovement() {
		oldY = JammyJam.game.player.y;
		if (input.keyDown[Keys.UP]) {
			JammyJam.game.player.y -= 5;
		} else if (input.keyDown[Keys.DOWN]) {
			JammyJam.game.player.y += 5;
		}
		if (checkCollision())
			JammyJam.game.player.y = oldY;
	}

	void checkHorizontalMovement() {
		oldX = JammyJam.game.player.x;

		if (input.keyDown[Keys.LEFT]) {
			JammyJam.game.player.x -= 5;
		} else if (input.keyDown[Keys.RIGHT]) {
			JammyJam.game.player.x += 5;
		}
		if (checkCollision())
			JammyJam.game.player.x = oldX;
	}

	boolean checkCollision() {
		int playerTilePositionX = (JammyJam.game.player.x + 16) / 32;
		int playerTilePositionY = (JammyJam.game.player.y + 16) / 32;

		return (checkWallCollision(playerTilePositionX, playerTilePositionY)
				|| checkNpcCollision(playerTilePositionX, playerTilePositionY));
	}

	boolean checkWallCollision(int playerTilePositionX, int playerTilePositionY) {
		Tile currentTile = null;
		if (MapData.inBounds(playerTilePositionX, playerTilePositionY)) {
			currentTile = Realm.mapData[Realm.curMap].tile[playerTilePositionX][playerTilePositionY];
		}
		if (currentTile != null && (currentTile.att[0] == Shared.Attributes.WALL.ordinal()
				|| currentTile.att[1] == Shared.Attributes.WALL.ordinal())) {
			return true;
		}
		return false;
	}

	boolean checkNpcCollision(int playerTilePositionX, int playerTilePositionY) {
		boolean result = false;
		for (NonPlayableCharacter npc : JammyJam.game.npcList) {
			if (npc.onScreen) {
				int npcTilePositionX = (npc.x + 16) / 32;
				int npcTilePositionY = (npc.y + 16) / 32;
				if (npcTilePositionX == playerTilePositionX && npcTilePositionY == playerTilePositionY) {
					PlotEngine.npcInteraction(npc);
					result = true;
				}
			}
		}
		return result;
	}

	boolean checkMapChange() {

		if (JammyJam.game.player.x < 5) {
			if (exitMap(2)) {
				JammyJam.game.player.x = Shared.MAP_WIDTH * 32 - 5;
				return true;
			}
		}
		if (JammyJam.game.player.x > Shared.MAP_WIDTH * 32 - 5) {
			if (exitMap(3)) {
				JammyJam.game.player.x = 5;
				return true;
			}
		}
		if (JammyJam.game.player.y < 5) {
			if (exitMap(0)) {
				JammyJam.game.player.y = Shared.MAP_WIDTH * 32 - 5;
				return true;
			}
		}
		if (JammyJam.game.player.y > Shared.MAP_WIDTH * 32 - 5) {
			if (exitMap(1)) {
				JammyJam.game.player.y = 5;
				return true;
			}
		}
		return false;
	}

	void changeMap(int mapTo) {
		Realm.curMap = mapTo;
		JammyJam.game.spawnNPCs(Realm.curMap);
		JammyJam.game.spawnItems(Realm.curMap);
		processed = false; // forces redraw
	}

	boolean exitMap(int direction) {
		MapData data = Realm.mapData[Realm.curMap];
		if (data.exit[direction] > 0) {
			changeMap(data.exit[direction]);
			return true;
		}
		return false;

	}
	
	public void render() {
		super.render();

		for (NonPlayableCharacter npc : JammyJam.game.npcList) {
			if (npc.onScreen)
				draw(Assets.textures.get(npc.tile_sheet), npc.x, npc.y, npc.source_x, npc.source_y, npc.width,
						npc.height);
		}

		for (Item item : JammyJam.game.loadedItems) {
			if (item.onScreen && !item.tile_sheet.isEmpty())
				draw(Assets.textures.get(item.tile_sheet), item.x, item.y, item.source_x, item.source_y, item.width,
						item.height);
		}

		draw(Assets.textures.get("sprites"), JammyJam.game.player.x, JammyJam.game.player.y, 64, 0, 32, 32);
		
		
	}

	@Override
	public void buttonPressed(String id) {
		switch(id) {
		case "dialog0":
			dialogToDisplay.choose(dialogToDisplay.choices.get(0));
			dialogToDisplay = null;
			pressedEnter = false;			
			break;
		}
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
		System.out.println("Mouse: " + x + ", " + y + ", Button: " + id);

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
