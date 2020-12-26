package com.gdx420.jammyjam.scenes;

import java.time.LocalTime;

import com.badlogic.gdx.Input.Keys;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.Assets;
import com.gdx420.jammyjam.core.JammyJam;
import com.gdx420.jammyjam.core.MapData;
import com.gdx420.jammyjam.core.NonPlayableCharacter;
import com.gdx420.jammyjam.core.PlotEngine;
import com.gdx420.jammyjam.core.Realm;
import com.gdx420.jammyjam.core.Tile;

public class PlayScene extends LiveMapScene {
		
	LocalTime startSleepTime = LocalTime.of(22,  0);
	LocalTime startAwakeTime = LocalTime.of(8,  0);
	
	enum Attributes {
		NO_ATTRIBUTE,
		WALL,
		MONSTER,
		WARP,
		DOOR,
		EFFECT,
		LIGHT,
		NPC_SPAWN,
		STORYPOINT,
		ITEM		
	}
	
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
			if(currentTile.att[0] == Attributes.ITEM.ordinal() || currentTile.att[1] == Attributes.ITEM.ordinal()){
				System.out.println("obtain item");
				PlotEngine.obtainItem(currentTile);
			}
			
			// next to a plot point
			for(int dir = 0; dir < 8; dir++) {
				Tile neighbor = Realm.mapData[Realm.curMap].getNeighbor(playerTilePositionX, playerTilePositionY, dir);
				if(neighbor != null) {			
					if(neighbor.att[0] == Attributes.STORYPOINT.ordinal() || neighbor.att[1] == Attributes.STORYPOINT.ordinal()){
						System.out.println("trigger plot");
						PlotEngine.triggerPlot(neighbor);
					}
				}
			}
		}	
	}
	
	void checkKeys() {
		checkVerticalMovement();
		checkHorizontalMovement();
		
		
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
		if(checkWallCollision())
			JammyJam.game.player.y = oldY;		
	}
	
	void checkHorizontalMovement() {
		int oldX = JammyJam.game.player.x;	
		
		if(input.keyDown[Keys.LEFT]) {
			JammyJam.game.player.x -= 1.0f;
		} else if (input.keyDown[Keys.RIGHT]) {
			JammyJam.game.player.x += 1.0f;
		}
		if(checkWallCollision())
			JammyJam.game.player.x = oldX;
	}
	
	boolean checkWallCollision() {
		int playerTilePositionX = (JammyJam.game.player.x + 16) / 32;
		int playerTilePositionY = (JammyJam.game.player.y + 16) / 32;		
		Tile currentTile = null;
		if(MapData.inBounds(playerTilePositionX, playerTilePositionY)) {
			currentTile = Realm.mapData[Realm.curMap].tile[playerTilePositionX][playerTilePositionY];
		}
		if(currentTile != null && currentTile.att[0] == Attributes.WALL.ordinal() || currentTile.att[1] == Attributes.WALL.ordinal()){
			return true;
		}
		return false;
	}

	public void render() {
		super.render();

		draw(Assets.textures.get("sprites"), (int)JammyJam.game.player.x,(int)JammyJam.game.player.y,64,0,32,32);
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
