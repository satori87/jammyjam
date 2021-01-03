package com.gdx420.jammyjam.scenes;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.bg.bearplane.engine.DrawTask;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.engine.Util;
import com.bg.bearplane.gui.DialogDisplay;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.Assets;
import com.gdx420.jammyjam.core.AudioManager;
import com.gdx420.jammyjam.core.DialogData;
import com.gdx420.jammyjam.core.DialogQueue;
import com.gdx420.jammyjam.core.Item;
import com.gdx420.jammyjam.core.JammyJam;
import com.gdx420.jammyjam.core.MapData;
import com.gdx420.jammyjam.core.NonPlayableCharacter;
import com.gdx420.jammyjam.core.Player;
import com.gdx420.jammyjam.core.PlotEngine;
import com.gdx420.jammyjam.core.Realm;
import com.gdx420.jammyjam.core.Shared;
import com.gdx420.jammyjam.core.StoryPoint;
import com.gdx420.jammyjam.core.Tile;

public class PlayScene extends LiveMapScene {

	LocalTime startSleepTime = LocalTime.of(22, 0);
	LocalTime startAwakeTime = LocalTime.of(8, 0);

	

	long lastMapChange = 0;

	public PlayScene() {

	}

	public void start() {
		super.start();
		changeMap(111);
		JammyJam.game.player.dir = 2;
	}

	public void update() {
			updatePlay();
	}

	public void updatePlay() {		
		
		if (JammyJam.gameIsWon) {			
			Scene.change("winScene");
			return;
		}

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
					if (!item.onScreen)
						continue;

					// on an item tile
					if ((currentTile.attStr[0] != null && currentTile.attStr[0].compareTo(item.name) == 0)
							|| (currentTile.attStr[1] != null && currentTile.attStr[1].compareTo(item.name) == 0)) {
						PlotEngine.obtainItem(item);
					}					
				}
			}

			PlotEngine.clearClueCache();
			// on a plot point
			for (StoryPoint sp : JammyJam.game.storyPoints) {
				if (!sp.onScreen)
					continue;
				
				if ((currentTile.attStr[0] != null && currentTile.attStr[0].compareTo(sp.name) == 0)
						|| (currentTile.attStr[1] != null && currentTile.attStr[1].compareTo(sp.name) == 0)) {
					if(PlotEngine.checkStoryPoint(sp))
						PlotEngine.triggerStoryPoint(sp);
				}
			}

			// next to a plot point
			for (int dir = 0; dir < 8; dir++) {
				Tile neighbor = Realm.mapData[Realm.curMap].getNeighbor(playerTilePositionX, playerTilePositionY, dir);
				if (neighbor != null) {
					if (neighbor.att[0] == Shared.Attributes.STORYPOINT.ordinal()
							|| neighbor.att[1] == Shared.Attributes.STORYPOINT.ordinal()) {
						for (StoryPoint sp : JammyJam.game.storyPoints) {
							if (!sp.onScreen)
								continue;

							if ((neighbor.attStr[0] != null && neighbor.attStr[0].compareTo(sp.name) == 0)
									|| (neighbor.attStr[1] != null && neighbor.attStr[1].compareTo(sp.name) == 0)) {
								if(PlotEngine.checkStoryPoint(sp))
									PlotEngine.triggerStoryPoint(sp);
							}
						}
					}
				}
			}
		}
	}

	public Player player() {
		return JammyJam.game.player;
	}

	private static boolean pressedEnter = false;

	void checkKeys() {
		warp = false;
		if (DialogQueue.isDialogDisplayed()) {
			if (input.keyDown[Keys.ENTER]) {
				pressedEnter = true;
			} else if (pressedEnter) {
				confirmDialog();
			}
		} else {
			checkVerticalMovement();
			checkHorizontalMovement();
			if (player().x != oldX || player().y != oldY) {
				player().walkStep++;
				if (player().walkStep >= Player.MAX_FRAMES) {
					player().walkStep = 0;
				}
			}
			checkMapChange();
			if (JammyJam.game.player.x < 5)
				JammyJam.game.player.x = 5;
			if (JammyJam.game.player.y < 5)
				JammyJam.game.player.y = 5;
			if (JammyJam.game.player.x >= Shared.MAP_WIDTH * 32 - 5)
				JammyJam.game.player.x = Shared.MAP_WIDTH * 32 - 5;
			if (JammyJam.game.player.y >= Shared.MAP_WIDTH * 32 - 5)
				JammyJam.game.player.y = Shared.MAP_WIDTH * 32 - 5;

		}

		if (input.keyDown[Keys.ESCAPE]) {
			Scene.change("menu");
			AudioManager.playMenuMusic();
		}

		if (input.keyDown[Keys.SPACE]) {
			JammyJam.isSpaceBarPressed = true;
		}
		else {
			if(JammyJam.isSpaceBarPressed) {
				// lifted button			
			}
			JammyJam.isSpaceBarPressed = false;
		}
	}
	
	void confirmDialog() {
		DialogQueue.confirmDialog(tick);
		pressedEnter = false;
	}

	void warp(int x, int y) {

		MapData data = Realm.mapData[Realm.curMap];
		if (data.tile[x][y] != null && data.tile[x][y].att[0] == Shared.Attributes.WARP.ordinal()) {
			changeMap(data.tile[x][y].attData[0][0]);
			JammyJam.game.player.x = data.tile[x][y].attData[0][1] * 32 - 16;
			JammyJam.game.player.y = data.tile[x][y].attData[0][2] * 32 - 16;
		}
	}

	int oldY = 0;
	int oldX = 0;

	boolean movedVert;
	boolean movedHor;

	void checkVerticalMovement() {

		movedVert = false;
		oldY = JammyJam.game.player.y;
		if (input.keyDown[Keys.UP]) {
			JammyJam.game.player.y -= Player.MOVE_STEP;
			JammyJam.game.player.dir = 0;
			movedVert = true;
		} else if (input.keyDown[Keys.DOWN]) {
			JammyJam.game.player.y += Player.MOVE_STEP;
			JammyJam.game.player.dir = 2;
			movedVert = true;
		}
		if (checkCollision() && !warp) {
			movedVert = false;
			JammyJam.game.player.y = oldY;
		}
	}

	void checkHorizontalMovement() {
		movedHor = false;
		if (movedVert)
			return;
		oldX = JammyJam.game.player.x;

		if (input.keyDown[Keys.LEFT]) {
			JammyJam.game.player.x -= Player.MOVE_STEP;
			JammyJam.game.player.dir = 1;
			movedHor = true;
		} else if (input.keyDown[Keys.RIGHT]) {
			JammyJam.game.player.x += Player.MOVE_STEP;
			JammyJam.game.player.dir = 3;
			movedHor = true;
		}
		if (checkCollision() && !warp) {
			movedHor = false;

			JammyJam.game.player.x = oldX;
		}
	}

	boolean warp = false;

	boolean checkCollision() {
		int playerTilePositionX = (JammyJam.game.player.x + 16) / 32;
		int playerTilePositionY = (JammyJam.game.player.y + 16) / 32;
		Tile t;

		if (tick > lastMapChange + 1000) {
			for (int x = playerTilePositionX - 1; x <= playerTilePositionX + 1; x++) {
				for (int y = playerTilePositionY - 1; y <= playerTilePositionY + 1; y++) {
					if (MapData.inBounds(x, y)) {
						t = Realm.mapData[Realm.curMap].tile[x][y];
						if (t.att[0] == 3) {
							if (Util.distance(x * 32 + 16, y * 32 + 16, JammyJam.game.player.x,
									JammyJam.game.player.y + 32) <= 36) {
								warp(x, y);
								Log.debug("WARP");
								warp = true;
								return false;
							}
						}

					}
				}
			}
		}
		for (NonPlayableCharacter npc : JammyJam.game.npcList) {
			if (npc.onScreen) {
				if (Util.distance(npc.x, npc.y, player().x, player().y) <48) {
					if(JammyJam.isSpaceBarPressed) {
						PlotEngine.npcInteraction(npc);
					}
					if (Util.distance(npc.x, npc.y, player().x, player().y) <32) {
						return true;
					}
				}
			}
		}
		boolean b1 = checkWallCollision(playerTilePositionX, playerTilePositionY)
				;
		playerTilePositionX = (JammyJam.game.player.x - 16) / 32;
		boolean b2 = checkWallCollision(playerTilePositionX, playerTilePositionY)
				;

		return (b1 || b2);
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
					// if(isSpaceBarPressed) {
					PlotEngine.npcInteraction(npc);
					result = true;
					// }
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
		lastMapChange = tick;
		Realm.curMap = mapTo;
		JammyJam.game.spawnNPCs(Realm.curMap);
		JammyJam.game.spawnItems(Realm.curMap);
		JammyJam.game.spawnStoryPoints(Realm.curMap);
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

		for (Item item : JammyJam.game.loadedItems) {
			if (item.onScreen && !item.tile_sheet.isEmpty())
				draw(Assets.textures.get(item.tile_sheet), item.x, item.y, item.source_x, item.source_y, item.width,
						item.height);
		}
		

		for(com.gdx420.jammyjam.core.Renderable clue : PlotEngine.clueList) {
			if(clue != null) {
				final int halfTile = 16;
				draw(Assets.textures.get(clue.sprite), clue.x-clue.width/2 + halfTile, clue.y-clue.height/2 + halfTile, 0, 0, clue.width, clue.height);
			}
		}


	}

	@Override
	public void buttonPressed(String id) {
		switch (id) {
		case "dialog0":
			confirmDialog();			
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
