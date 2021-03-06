package com.bg.jammyjam.scenes;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.engine.Util;
import com.bg.bearplane.gui.Dialog;
import com.bg.bearplane.gui.Scene;
import com.bg.jammyjam.core.Assets;
import com.bg.jammyjam.core.DialogData;
import com.bg.jammyjam.core.Item;
import com.bg.jammyjam.core.JammyJam;
import com.bg.jammyjam.core.MapData;
import com.bg.jammyjam.core.NonPlayableCharacter;
import com.bg.jammyjam.core.Player;
import com.bg.jammyjam.core.PlotEngine;
import com.bg.jammyjam.core.Realm;
import com.bg.jammyjam.core.Shared;
import com.bg.jammyjam.core.StoryPoint;
import com.bg.jammyjam.core.Tile;

public class PlayScene extends LiveMapScene {

	LocalTime startSleepTime = LocalTime.of(22, 0);
	LocalTime startAwakeTime = LocalTime.of(8, 0);

	public static Queue<DialogData> dialogQueue = new LinkedList<DialogData>();
	public static boolean isSpaceBarPressed = false;

	long lastMapChange = 0;

	public PlayScene() {

	}

	public void start() {
		super.start();
		changeMap(111);
		JammyJam.game.player.dir = 2;
	}

	public void update() {
		if (dialogToDisplay != null || dialogQueue.size() > 0)
			updateDialog();
		else
			updatePlay();
	}

	public void updatePlay() {
		if (JammyJam.gameIsWon) {
			Scene.change("menu");
			if (JammyJam.musicLoop != null)
				JammyJam.musicLoop.stop();
			JammyJam.musicLoop = Assets.sounds.get("Pursuit_seamless");
			JammyJam.musicLoop.loop();
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

					if ((currentTile.attStr[0] != null && currentTile.attStr[0].compareTo(item.name) == 0)
							|| (currentTile.attStr[1] != null && currentTile.attStr[1].compareTo(item.name) == 0)) {
						// if(isSpaceBarPressed)
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
						for (StoryPoint sp : JammyJam.game.storyPoints) {
							if (!sp.onScreen)
								continue;

							if ((neighbor.attStr[0] != null && neighbor.attStr[0].compareTo(sp.name) == 0)
									|| (neighbor.attStr[1] != null && neighbor.attStr[1].compareTo(sp.name) == 0)) {
								if (isSpaceBarPressed == true)
									PlotEngine.triggerStoryPoint(sp);
							}
						}
					}
				}
			}
		}
	}

	public static long lastDialogUpdate = System.currentTimeMillis();

	// CREATE DIALOG HERE (and update)
	public void updateDialog() {
		
		if(dialogToDisplay != null)
			return;

		// prevent multiple times in a row
		if(System.currentTimeMillis() - lastDialogUpdate < 2000)
			return;

		lastDialogUpdate = System.currentTimeMillis();

		if (dialogQueue.size() > 0) {
			DialogData data = dialogQueue.poll();
			String words = data.dialog;
			if (data.npcParent != null)
				words = data.npcParent.name + ": " + words;
			if (data.itemParent != null)
				words = data.itemParent.name + " Found!  " + words;
			dialogToDisplay = new Dialog(this, data.name, 400, words, new String[] { "Hmmm..." }, new String[] { "0" });
			dialogToDisplay.start(tick);
		}
	}

	public Player player() {
		return JammyJam.game.player;
	}

	private static boolean pressedEnter = false;

	void checkKeys() {
		warp = false;
		if (dialogToDisplay != null) {
			if (input.keyDown[Keys.ENTER]) {
				pressedEnter = true;
			} else if (pressedEnter) {
				dialogToDisplay.choose(dialogToDisplay.choices.get(0));
				dialogToDisplay = null;
				pressedEnter = false;
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
			if (JammyJam.musicLoop != null)
				JammyJam.musicLoop.stop();
			JammyJam.musicLoop = Assets.sounds.get("Dream_Music1");
			JammyJam.musicLoop.loop();
		}

		if (input.keyDown(Keys.SPACE))
			isSpaceBarPressed = true;
		else
			isSpaceBarPressed = false;
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
					PlotEngine.npcInteraction(npc);
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

	}

	@Override
	public void buttonPressed(String id) {
		switch (id) {
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
