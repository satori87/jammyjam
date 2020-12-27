package com.gdx420.jammyjam.scenes;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

import com.badlogic.gdx.Input.Keys;
import com.bg.bearplane.engine.Util;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.Assets;
import com.gdx420.jammyjam.core.JammyJam;
import com.gdx420.jammyjam.core.MapData;
import com.gdx420.jammyjam.core.NonPlayableCharacter;
import com.gdx420.jammyjam.core.PlotEngine;
import com.gdx420.jammyjam.core.Realm;
import com.gdx420.jammyjam.core.Shared;
import com.gdx420.jammyjam.core.Tile;

public class PlayScene extends LiveMapScene {
		
	LocalTime startSleepTime = LocalTime.of(22,  0);
	LocalTime startAwakeTime = LocalTime.of(8,  0);
	
	
	public PlayScene() {

	}

	public void start() {
		super.start();		
	}

	public void update() {
		int playerTilePositionX = (JammyJam.game.player.x + 16) / 32;
		int playerTilePositionY = (JammyJam.game.player.y + 16) / 32;		
		Tile currentTile = null;
		if(MapData.inBounds(playerTilePositionX, playerTilePositionY)) {
			currentTile = Realm.mapData[Realm.curMap].tile[playerTilePositionX][playerTilePositionY];
		}
		if(currentTile != null) {
			// on an item
			if(currentTile.att[0] == Shared.Attributes.ITEM.ordinal() || currentTile.att[1] == Shared.Attributes.ITEM.ordinal()){
				PlotEngine.obtainItem(currentTile);
			}
			
			// next to a plot point
			for(int dir = 0; dir < 8; dir++) {
				Tile neighbor = Realm.mapData[Realm.curMap].getNeighbor(playerTilePositionX, playerTilePositionY, dir);
				if(neighbor != null) {			
					if(neighbor.att[0] == Shared.Attributes.STORYPOINT.ordinal() || neighbor.att[1] == Shared.Attributes.STORYPOINT.ordinal()){
						PlotEngine.triggerPlot(neighbor);
					}
				}
			}
		}	
	}
	
	void checkKeys() {
		checkVerticalMovement();
		checkHorizontalMovement();
		
		checkMapChange();
				
		if(input.keyDown[Keys.ESCAPE]) {
			Scene.change("menu");
		}		
	}
	
	void checkVerticalMovement() {
		int oldY = JammyJam.game.player.y;
		
		if (input.keyDown[Keys.UP]) {
			JammyJam.game.player.y -= 1.0f;
		} else 
			if(input.keyDown[Keys.DOWN]) {
			JammyJam.game.player.y += 1.0f;
		}
		if(checkCollision())
			JammyJam.game.player.y = oldY;		
	}
	
	void checkHorizontalMovement() {
		int oldX = JammyJam.game.player.x;	
		
		if(input.keyDown[Keys.LEFT]) {
			JammyJam.game.player.x -= 1.0f;
		} else if (input.keyDown[Keys.RIGHT]) {
			JammyJam.game.player.x += 1.0f;
		}
		if(checkCollision())
			JammyJam.game.player.x = oldX;
	}
	
	boolean checkCollision() {
		int playerTilePositionX = (JammyJam.game.player.x + 16) / 32;
		int playerTilePositionY = (JammyJam.game.player.y + 16) / 32;
		
		return (checkWallCollision(playerTilePositionX, playerTilePositionY) || checkNpcCollision(playerTilePositionX, playerTilePositionY));
	}
	
	boolean checkWallCollision(int playerTilePositionX, int playerTilePositionY) {		
		Tile currentTile = null;
		if(MapData.inBounds(playerTilePositionX, playerTilePositionY)) {
			currentTile = Realm.mapData[Realm.curMap].tile[playerTilePositionX][playerTilePositionY];
		}
		if(currentTile != null 
				&& (currentTile.att[0] == Shared.Attributes.WALL.ordinal() || currentTile.att[1] == Shared.Attributes.WALL.ordinal())){
			return true;
		}
		return false;
	} 
	
	boolean checkNpcCollision(int playerTilePositionX, int playerTilePositionY) {
		boolean result = false;
		for (NonPlayableCharacter npc : JammyJam.game.npcList) {
			if(npc.onScreen) {
				int npcTilePositionX = (npc.x + 16) / 32;
				int npcTilePositionY = (npc.y + 16) / 32;
				if(npcTilePositionX == playerTilePositionX && npcTilePositionY == playerTilePositionY) {
					PlotEngine.npcInteraction(npc);
					result = true;
				}
			}
		}
		return result;
	}
	
	void checkMapChange() {
		
		if(JammyJam.game.player.x < 0) {
			changeMap(0);
			JammyJam.game.player.x = Shared.GAME_WIDTH - 5;
		}
		if(JammyJam.game.player.x > Shared.GAME_WIDTH) {
			changeMap(2);
			JammyJam.game.player.x = 5;
		}
		if(JammyJam.game.player.y < 0) {
			changeMap(1);			
			JammyJam.game.player.y = Shared.GAME_HEIGHT - 5;
		}
		if(JammyJam.game.player.y > Shared.GAME_HEIGHT) {
			changeMap(3);
			JammyJam.game.player.y = 5;
		}
	}
	
	void changeMap(int direction) {
		Realm.curMap++;
		if(Realm.curMap >= Realm.loadedMapsCount)
			Realm.curMap = 0;
		super.processed = false; // forces redraw
		
		JammyJam.game.spawnNPCs(Realm.curMap);
	}

	public void render() {
		super.render();
		
		for (NonPlayableCharacter npc : JammyJam.game.npcList) {
			if(npc.onScreen)
				draw(Assets.textures.get("sprites"), npc.x,npc.y,128,0,32,32);
		}

		draw(Assets.textures.get("sprites"), JammyJam.game.player.x,JammyJam.game.player.y,64,0,32,32);
	}

	@Override
	public void buttonPressed(String id) {		
		
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
