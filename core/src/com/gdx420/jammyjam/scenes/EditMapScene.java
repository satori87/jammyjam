package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.engine.Util;
import com.bg.bearplane.gui.Button;
import com.bg.bearplane.gui.CheckBox;
import com.bg.bearplane.gui.Field;
import com.bg.bearplane.gui.Frame;
import com.bg.bearplane.gui.Label;
import com.bg.bearplane.gui.ListBox;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.MapData;
import com.gdx420.jammyjam.core.Realm;
import com.gdx420.jammyjam.core.Shared;
import com.gdx420.jammyjam.core.Tile;

public class EditMapScene extends RenderEditMapScene {

	byte[][] chunks = new byte[1][1];

	Label hover;
	public int lastMonsterSel = 0;

	ListBox setList;
	ListBox mapList;

	Frame frmWarp = null;
	Label lblWarpMap;
	Label lblWarpX;
	Label lblWarpY;
	int lastWarpX = 0;
	int lastWarpY = 0;
	int lastWarpMap = 0;

	Frame frmNPC = null;
	Field fieldNPC;
	
	Frame frmStory = null;
	Field fieldStory;
	
	Frame frmItem = null;
	Field fieldItem;
	
	

	Field mapName;

	void startWarp() {
		int i = 0;
		int s = 36;
		int ii = 0;
		frmWarp = new Frame(this, "warpf", 584, 284, 384, 262, false, false, true);
		frames.put("warpf", frmWarp);
		frmWarp.visible = false;
		frmWarp.labels.put("warp0", new Label(this, "warp0", 776, 310, 2f, "Warp Tile", Color.WHITE, true));
		i = 0;
		lblWarpMap = new Label(this, "warp1", 776, 340 + i * s, 1f, lastWarpMap + "", Color.WHITE, true);
		frmWarp.labels.put("warp1", lblWarpMap);
		ii = 300 + i * 2;
		frmWarp.buttons.put(ii + "", new Button(this, ii + "", 656, 340 + i * s, 32, 24, "-"));
		ii = 301 + i * 2;
		frmWarp.buttons.put(ii + "", new Button(this, ii + "", 896, 340 + i * s, 32, 24, "+"));
		i = 1;
		lblWarpX = new Label(this, "warp3", 776, 340 + i * s, 1f, lastWarpX + "", Color.WHITE, true);
		frmWarp.labels.put("warp3", lblWarpX);
		ii = 300 + i * 2;
		frmWarp.buttons.put(ii + "", new Button(this, ii + "", 656, 340 + i * s, 32, 24, "-"));
		ii = 301 + i * 2;
		frmWarp.buttons.put(ii + "", new Button(this, ii + "", 896, 340 + i * s, 32, 24, "+"));
		i = 2;
		lblWarpY = new Label(this, "warp4", 776, 340 + i * s, 1f, lastWarpY + "", Color.WHITE, true);
		frmWarp.labels.put("warp4", lblWarpY);
		ii = 300 + i * 2;
		frmWarp.buttons.put(ii + "", new Button(this, ii + "", 656, 340 + i * s, 32, 24, "-"));
		ii = 301 + i * 2;
		frmWarp.buttons.put(ii + "", new Button(this, ii + "", 896, 340 + i * s, 32, 24, "+"));
		for (Button b : frmWarp.buttons.values()) {
			b.interval = 10;
		}
	}

	void startNPC() {		
		frmNPC = new Frame(this, "npcf", 584, 284, 384, 262, false, false, true);
		frames.put("npcf", frmNPC);
		frmNPC.visible = false;
		frmNPC.labels.put("npctile", new Label(this, "npc0", 776, 310, 2f, "npc Tile", Color.WHITE, true));
		fieldNPC = frmNPC.addField("npc", 15, 2, 630, 360, 300);
	}
	
	void startStory() {		
		frmStory = new Frame(this, "Storyf", 584, 284, 384, 262, false, false, true);
		frames.put("Storyf", frmStory);
		frmStory.visible = false;
		frmStory.labels.put("Storytile", new Label(this, "Story0", 776, 310, 2f, "Story Tile", Color.WHITE, true));
		fieldStory = frmStory.addField("Story", 15, 3, 630, 360, 300);
	}
	
	void startItem() {		
		frmItem = new Frame(this, "Itemf", 584, 284, 384, 262, false, false, true);
		frames.put("Itemf", frmItem);
		frmItem.visible = false;
		frmItem.labels.put("Itemtile", new Label(this, "Item0", 776, 310, 2f, "Item Tile", Color.WHITE, true));
		fieldItem = frmItem.addField("Item", 15, 4, 630, 360, 300);
	}

	public void start() {
		int ii = 0;
		super.start();
		buttons.clear();
		frames.clear();
		labels.clear();
		Button b = null;
		hover = new Label(this, "hover", 840, 564, 1f, "", Color.WHITE, false);
		labels.put("hover", hover);
		frames.put("h0", new Frame(this, "h0", 15, 35, 522, 522, false, false));
		frames.put("h1", new Frame(this, "h1", 554, 35, 522, 522, false, false));

		frames.put("h2", new Frame(this, "h2", 554, 585 - 4, 42, 42, false, false));
		frames.put("h3", new Frame(this, "h3", 554 + 64, 585 - 4, 10 + 384, 42, false, false));

		for (int i = 0; i < 5; i++) {
			checkBoxes.put(i + "", new CheckBox(this, i + "", i * 100 + 36, 585));
			buttons.put(i + "", new Button(this, i + "", i * 100 + 84, 585, 72, 24, Shared.layerName[i], true));
		}
		buttons.get("0").toggled = true;
		for (int i = 0; i < 5; i++) {
			checkBoxes.put((i + 5) + "", new CheckBox(this, (i + 5) + "", i * 100 + 36, 585 + 34));
			ii = i + 5;
			buttons.put(ii + "",
					new Button(this, ii + "", i * 100 + 84, 585 + 34, 72, 24, Shared.layerName[i + 5], true));
		}
		for (int i = 0; i < 10; i++) {
			vis[i] = true;
			checkBoxes.get(i + "").toggled = true;
		}

		for (int i = 0; i < 2; i++) {
			ii = 200 + i;
			b = new Button(this, ii + "", i * 90 + 600 - 90, 740, 72, 24, Shared.panelName[i], false);
			buttons.put(ii + "", b);
		}

		Field t = addField("mapnum", 3, 0, 650, 700, 160, false, null);

		fields.put("mapnum", t);
		t.allowLetters = false;
		t.allowSpecial = false;

		lblTileSet = new Label(this, "tileset", 554 + 263, 18, 1f, "Buildings", Color.WHITE, true);
		buttons.put("100", new Button(this, "100", 554 + 263 - 150, 18, 24, 24, "<"));
		buttons.put("101", new Button(this, "101", 554 + 263 + 150, 18, 24, 24, ">"));
		labels.put("tileset", lblTileSet);
		lblName = new Label(this, "name", 276, 20, 2f, "", Color.WHITE, true);
		labels.put("name", lblName);

		mapName = addField("mapName", 30, 1, 210, 650, 400, true);
		labels.put("mapnamelbl", new Label(this, "mapnamelbl", 100, 650, 1f, "Map Name:", Color.WHITE, true));
		addButton("205", 530, 690, 96, 32, "Rename");
		fields.put("mapName", mapName);

		setupAttPanels();

	}

	@Override
	public void switchTo() {
		try {
			input.mouseDown[0] = false;
			input.wasMouseJustClicked[0] = false;
			super.switchTo();
			// Shared.GAME_WIDTH = 1376;
			// Gdx.graphics.setWindowedMode(Shared.GAME_WIDTH, Shared.GAME_HEIGHT);
			// setupScreen(Shared.GAME_WIDTH, Shared.GAME_HEIGHT);
			scrollX = Math.round(cam.position.x / 32 - Shared.GAME_WIDTH / 64);
			scrollY = Math.round(cam.position.y / 32 - Shared.GAME_HEIGHT / 64);
			moveCameraTo(Shared.GAME_WIDTH / 2, Shared.GAME_HEIGHT / 2);

			listBoxes.clear();

			setList = new ListBox(this, "setlist", 1104, 35, 256, 530);
			listBoxes.put("setlist", setList);
			for (String s : Shared.tilesets) {
				setList.list.add(s);
			}

			mapList = new ListBox(this, "maplist", 1104, 35, 256, 530);
			updateMapList();
			mapList.sel = Realm.curMap;
			mapList.visible = false;
			listBoxes.put("maplist", mapList);

			if (editMode == 9) {
				switchAtt(att);
			}

		} catch (Exception e) {
			Log.error(e);
		}
	}

	void updateMapList() {
		mapList.list.clear();
		mapList.sel = 0;
		for (int i = 0; i < Shared.NUM_MAPS; i++) {
			if (Realm.mapData[i] != null) {
				mapList.list.add(i + ": " + Realm.mapData[i].name);
			}
		}
		mapList.sel = Realm.curMap;
	}

	void setupAttPanels() {
		startWarp();
		startNPC();
		startStory();
		startItem();
	}

	@Override
	public void listChanged(String id, int sel) {
		switch (id) {
		case "setlist": // tileset
			break;
		case "maplist": // map
			attData[0] = sel;
			lastWarpMap = sel;
			break;
		}
	}

	@Override
	public void buttonPressed(String iid) {
		int id = Integer.parseInt(iid);
		super.buttonPressed(iid);
		int mid = 0;
		if (id < 10) {
			editMode = id;
			for (int i = 0; i < 10; i++) {
				buttons.get(i + "").toggled = false;
			}
			buttons.get(id + "").toggled = true;
			tileBoxing = false;
			tileBox = false;
			mapBoxing = false;
			// walling = false;
			halting = false;

			if (editMode != 9) {
				hideFrames();
				setList.visible = true;
			} else {
				setList.visible = false;
			}
		}
		switch (id) {
		case 100:
			curSet--;
			if (curSet < 0) {
				curSet = Shared.tilesets.length - 1;
			}
			setList.sel = curSet;
			tileBoxing = false;
			tileBox = false;
			mapBoxing = false;
			// walling = false;
			halting = false;
			break;
		case 101:
			curSet++;
			if (curSet > Shared.tilesets.length - 1) {
				curSet = 0;
			}
			setList.sel = curSet;
			tileBoxing = false;
			tileBox = false;
			mapBoxing = false;
			// walling = false;
			halting = false;
			break;
		case 200:
			importMap();
			// change("mapOptions");
			break;
		case 201:
			exportMap();
			// importMap();
			break;
		case 202:
			// export();
			break;
		case 203:
			// test();
			break;
		case 204:
			// discard();
			break;
		case 205: // update name
			map().name = mapName.text;
			updateMapList();
			break;

		}
		if (id >= 300 && id < 400) {
			switch (id % 300) {
			case 0:
				attData[0]--;
				if (attData[0] < 0)
					attData[0] = 0;
				lastWarpMap = attData[0];
				mapList.sel = lastWarpMap;
				break;
			case 1:
				attData[0]++;
				if (attData[0] > 2000)
					attData[0] = 2000;
				lastWarpMap = attData[0];
				mapList.sel = lastWarpMap;
				break;
			case 2:
				attData[1]--;
				if (attData[1] < 0)
					attData[1] = 0;
				lastWarpX = attData[0];
				break;
			case 3:
				attData[1]++;
				if (attData[1] >= Shared.MAP_WIDTH)
					attData[1] = Shared.MAP_WIDTH - 1;
				lastWarpX = attData[0];
				break;
			case 4:
				attData[2]--;
				if (attData[2] < 0)
					attData[2] = 0;
				lastWarpY = attData[0];
				break;
			case 5:
				attData[2]++;
				if (attData[2] >= Shared.MAP_WIDTH)
					attData[2] = Shared.MAP_WIDTH - 1;
				lastWarpY = attData[0];
				break;
			}
		}
	}

	@Override
	public void enterPressedInField(String id) {
		super.enterPressedInField(id);
	}

	void updateBox() {
		int i = 16;
		int bux = boxUp % i;
		int buy = boxUp / i;
		int bdx = boxDown % i;
		int bdy = boxDown / i;
		int leftX = bux < bdx ? bux : bdx;
		int topY = buy < bdy ? buy : bdy;
		boxStart = topY * i + leftX;
		int rightX = bux < bdx ? bdx : bux;
		int bottomY = buy < bdy ? bdy : buy;
		boxEnd = bottomY * i + rightX;
	}

	void scrollMap() {
		int scrollSpeed = 1;
		if (tick > scrollStamp) {
			scrollStamp = tick + 30;
			if (input.keyDown[Keys.UP]) {
				scrollY -= scrollSpeed;
			} else if (input.keyDown[Keys.DOWN]) {
				scrollY += scrollSpeed;
			}
			if (input.keyDown[Keys.LEFT]) {
				scrollX -= scrollSpeed;
			} else if (input.keyDown[Keys.RIGHT]) {
				scrollX += scrollSpeed;
			}
		}
		if (scrollX < -4) {
			scrollX = -4;
		}
		if (scrollX > Shared.MAP_WIDTH - 12) {
			scrollX = Shared.MAP_WIDTH - 12;
		}
		if (scrollY > Shared.MAP_WIDTH - 12) {
			scrollY = Shared.MAP_WIDTH - 12;
		}
		if (scrollY < -4) {
			scrollY = -4;
		}
	}

	void test() {
		// TestMapScene ps = (TestMapScene) scenes.get("testMap");
		change("testMap");
	}

	void export() {
		try {
			// map().version++;
			// OutputStream outputStream = new DeflaterOutputStream(new
			// FileOutputStream("file.bin"));
			// Output output = new Output(outputStream);
			// Kryo kryo = new Kryo();
			// kryo.writeObject(output, map());
			// output.close();
		} catch (Exception e) {
			Log.error(e);
		}
	}

	void commit() {
		// lock();
		// byte[] str = Util.serialize(Realm.mapData[Realm.curMap]);
		// chunks = Util.divideArray(str, Shared.CHUNK_SIZE);
		// for (int i = 0; i < chunks.length; i++) {
		// Odyssey.game.sendTCP(new Chunk(chunks[i], Realm.curMap, i == chunks.length -
		// 1 ? 1 : 0, i));
		// }
	}

	void discard() {
		// int m = Realm.curMap;
		// Realm.mapData[m] = null;
		// Realm.loadMap(m);
		// Realm.pmap[m] = new PMap();
		// Realm.mapData[m].checkAll(Realm.pmap[m]);
		// Scene.lock();
		// Odyssey.game.sendTCP(new DiscardMap());
	}

	void importMap() {
		try {

			int m = Integer.parseInt(fields.get("mapnum").text);
			Realm.curMap = m;
			MapData md = (MapData) Util.importJSON("maps/map" + m + ".map", MapData.class);
			if (md != null) {
				Realm.mapData[m] = md;
			}
			updateMapList();

		} catch (Exception e) {
			Log.error(e);
		}
	}

	void exportMap() {
		try {
			int m = Integer.parseInt(fields.get("mapnum").text);
			Util.exportJSON("maps/map" + m + ".map", map());
			importMap();
		} catch (Exception e) {
			Log.error(e);
		}
	}

	void checkKeys() {
		for (Integer a : Scene.input.keyPress) {
			switch (a) {
			case Keys.C:
				// if (input.keyDown[Keys.CONTROL_LEFT] || input.keyDown[Keys.CONTROL_RIGHT]) {
				// if(!mapBoxing && !tileBoxing) {
				// copy = true;
				// }
				// }
				break;
			case Keys.SHIFT_LEFT:
			case Keys.SHIFT_RIGHT:
				shift = !shift;
				break;
			case Keys.F4:
				// test();
				break;
			case Keys.F5:
				// commit();
				break;
			case Keys.F6:
				// importMap();
				break;
			}
		}
	}

	void updateLabels() {
		switch (att) {

		case 3: // warp
			lblWarpMap.text = "Map " + attData[0] + ": " + Realm.mapData[attData[0]].name;
			lblWarpX.text = "X: " + attData[1] + "";
			lblWarpY.text = "Y: " + attData[2] + "";
			break;

		}
		lblTileSet.text = Shared.tilesets[curSet];
		if (curHoverX >= 0 && curHoverX < Shared.MAP_WIDTH && curHoverY >= 0 && curHoverY < Shared.MAP_WIDTH) {
			hover.text = "Map: " + Realm.curMap + " X: " + curHoverX + " Y: " + curHoverY;
		} else {
			hover.text = "Map: " + Realm.curMap;
		}
		hover.text += " (" + input.mouseX + "," + input.mouseY + ")";
	}

	public void update() {
		super.update();
		curSet = setList.sel;
		int sx = input.mouseX;
		int sy = input.mouseY;
		lblName.text = map().name;
		int mx = ((sx - 20) / 32);
		int my = ((sy - 40) / 32);
		mapMouseX = sx - 20;
		mapMouseY = sy - 40;
		if (mx >= 0 && mx < 16 && my >= 0 && my < 16) {
			curHoverX = mx + scrollX;
			curHoverY = my + scrollY;
		} else {
			curHoverX = -1;
			curHoverY = -1;
		}

		int tx = (sx - 559) / 32;
		int ty = (sy - 40) / 32;
		int dmx = 0;
		int dmy = 0;
		if (shift) {
			dmx = (sx - 20) - mx * 32;
			dmy = (sy - 40) - my * 32;
		}

		checkKeys();
		updateLabels();
		scrollMap();
		if (tileBoxing) {
			if (tx > 15) {
				tx = 15;
			}
			if (ty > 15) {
				ty = 15;
			}
			if (tx < 0) {
				tx = 0;
			}
			if (ty < 0) {
				ty = 0;
			}
			boxUp = ty * 16 + tx;
			updateBox();
		} else if (mapBoxing) {
			if (mx > 15) {
				mx = 15;
			}
			if (my > 15) {
				my = 15;
			}
			if (mx < 0) {
				mx = 0;
			}
			if (my < 0) {
				my = 0;
			}
			boxUp = my * 16 + mx;
			updateBox();
		}

		if (Util.inBox(sx, sy, 20, 20 + 522, 40, 40 + 522)) {

			if (curSelTile > 0 || tileBox || editMode == 7 || editMode == 8 || editMode == 9) {
				if (input.mouseDown[0]) {
					LClickMap(mx, my, dmx, dmy);
				} else if (input.mouseDown[1]) {
					RClickMap(mx, my);
				}
			}

		} else if (Util.inBox(sx, sy, 559, 559 + 512, 40, 40 + 512)) {

		} else if (Util.inBox(sx, sy, 690, 690 + 320, 2, 34)) {
			if (input.mouseDown[0]) {
				// curSet = (sx - 690) / 32;
				// tileBoxing = false;
				// tileBox = false;
			}
		}
		// clicked map
	}

	void LClickMap(int mx, int my, int dmx, int dmy) {
		if (!mapBoxing) {
			if (tick > leftCoolDown) {
				if (vis[editMode]) {
					if (editMode < 7) {
						if (tileBox) {
							leftCoolDown = tick + 100;
							placeBox(mx, my, editMode, dmx, dmy);
						} else if (!halting) {
							if (alting) {
								changeTile(map().tile[mx + scrollX][my + scrollY].set[editMode],
										map().tile[mx + scrollX][my + scrollY].tile[editMode]);
							} else {
								int mmx = mx + scrollX;
								int mmy = my + scrollY;
								if (inBounds(mmx, mmy)) {
									Tile t = map().tile[mx + scrollX][my + scrollY];
									oldSet = t.set[editMode];
									oldTile = t.tile[editMode];
									if (curSelSet != oldSet || curSelTile != oldTile || editMode == 0) {
										t.set[editMode] = curSelSet;
										t.tile[editMode] = curSelTile;
										t.shiftX[editMode] = 0;
										t.shiftY[editMode] = 0;

										if (editMode > 0) {
											t.shiftX[editMode] = dmx;
											t.shiftY[editMode] = dmy;
										}
									}
								}
							}
						}
					} else if (editMode == 9) {
						if (!halting) {
							int msx = mx + scrollX;
							int msy = my + scrollY;
							int i = shift ? 1 : 0;
							if (alting) {
								if (map().tile[msx][msy].att[i] > 0) {
									att = map().tile[msx][msy].att[i];
									for (int c = 0; c < 10; c++) {
										attData[c] = map().tile[msx][msy].attData[i][c];
									}
									switch (att) {
									case 3: // warp
										lastWarpMap = attData[0];
										mapList.sel = lastWarpMap;
										lastWarpX = attData[1];
										lastWarpY = attData[2];
										break;
									case 2: // spawn
										break;
									case 4: // door

										break;
									case 5: // fx

										break;
									case 6: // light
										break;
									case 7: // npc
										fieldNPC.text = map().tile[msx][msy].attStr[i];
										break;
									case 8: // story
										fieldStory.text = map().tile[msx][msy].attStr[i];
										break;
									case 9: // item
										fieldItem.text = map().tile[msx][msy].attStr[i];
										break;
									}
									switchAtt(att);
								}
							} else {
								if (MapData.inBounds(msx, msy)) {
									map().tile[msx][msy].att[i] = att;
									for (int c = 0; c < 10; c++) {
										map().tile[msx][msy].attData[i][c] = attData[c];
									}
									switch(att) {
									case 7:
										map().tile[msx][msy].attStr[i] = fieldNPC.text;
										break;
									case 8:
										map().tile[msx][msy].attStr[i] = fieldStory.text;
										break;
									case 9:
										map().tile[msx][msy].attStr[i] = fieldItem.text;
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	void RClickMap(int mx, int my) {
		if (!mapBoxing) {
			if (vis[editMode]) {
				if (editMode < 7) {
					if (alting) {
						if (map().tile[mx + scrollX][my + scrollY].tile[editMode] > 0) {
							curSet = map().tile[mx + scrollX][my + scrollY].set[editMode];
						}
					} else {
						int mmx = mx + scrollX;
						int mmy = my + scrollY;
						// int s = map().tile[mmx][mmy].set[editMode];
						if (inBounds(mmx, mmy)) {
							Tile t = map().tile[mmx][mmy];
							if (t.tile[editMode] > 0) {
								t.set[editMode] = 0;
								t.tile[editMode] = 0;
								t.shiftX[editMode] = 0;
								t.shiftY[editMode] = 0;

							}
						}
					}
				} else if (editMode == 7) {

				} else if (editMode == 8) {

				} else if (editMode == 9) {
					if (!halting) {
						int msx = mx + scrollX;
						int msy = my + scrollY;
						int i = shift ? 1 : 0;
						if (alting) {

						} else {
							map().tile[msx][msy].att[i] = 0;
							for (int c = 0; c < 10; c++) {
								map().tile[msx][msy].attData[i][c] = 0;
							}
						}
					}
				}
			}
		}
	}

	public void render() {
		super.render();

	}

	void changeTile(int set, int tile) {
		if (tile == 0 || (set == curSelSet && tile == curSelTile)) {
			return;
		}

		// first check if its already in recent
		boolean f = false;
		for (int i = 0; i < 12; i++) {
			if (recentSets[i] == curSelSet && recentTiles[i] == curSelTile) {
				f = true;
			}
		}
		if (!f) {
			for (int i = 11; i > 0; i--) {
				recentTiles[i] = recentTiles[i - 1];
				recentSets[i] = recentSets[i - 1];
			}
			recentTiles[0] = curSelTile;
			recentSets[0] = curSelSet;
		}
		curSelTile = tile;
		curSelSet = set;
	}

	public void mouseUp(int x, int y, int button) {
		halting = false;
		int tx = (x - 559) / 32;
		int ty = (y - 40) / 32;
		// if (ctrl) { // start box select
		if (tileBoxing) {
			tileBoxing = false;
			if (tx < 0) {
				tx = 0;
			}
			if (ty < 0) {
				ty = 0;
			}
			if (tx > 15) {
				tx = 15;
			}
			if (ty > 15) {
				ty = 15;
			}
			boxUp = ty * 16 + tx;
			updateBox();
			tileBox = true;
			boxSet = curSet;
		} else if (mapBoxing) {
			if (boxButton == button) {
				mapBoxing = false;
				tx = (x - 20) / 32;
				ty = (y - 40) / 32;
				if (tx < 0) {
					tx = 0;
				}
				if (ty < 0) {
					ty = 0;
				}
				if (tx > 15) {
					tx = 15;
				}
				if (ty > 15) {
					ty = 15;
				}
				boxUp = ty * 16 + tx;
				updateBox();
				if (editMode < 7) {
					// if (alting) {

					// } else {
					placeField(boxButton == 0 ? curSelSet : 0, boxButton == 0 ? curSelTile : 0, editMode);
					// }
				} else if (editMode == 7) {

				} else if (editMode == 9) {
					placeAttField(tx, ty, boxButton == 0 ? 1 : 0);
				}
			}
		}
		if (Util.inBox(x, y, 20, 20 + 522, 40, 40 + 522)) {

		} else if (Util.inBox(x, y, 559, 559 + 512, 40, 40 + 512)) {

		}
	}

	void placeAttField(int mx, int my, int val) {
		int sx = boxStart % 16;
		int sy = boxStart / 16;
		int ex = boxEnd % 16;
		int ey = boxEnd / 16;
		for (int x = sx; x <= ex; x++) {
			for (int y = sy; y <= ey; y++) {
				int i = shift ? 1 : 0;
				if (val > 0) {
					map().tile[x + scrollX][y + scrollY].att[i] = att;
					for (int c = 0; c < 10; c++) {
						map().tile[x + scrollX][y + scrollY].attData[i][c] = attData[c];
					}
				} else {
					map().tile[x + scrollX][y + scrollY].att[i] = 0;
					for (int c = 0; c < 10; c++) {
						map().tile[x + scrollX][y + scrollY].attData[i][c] = 0;
					}
				}

			}
		}
		mapBoxing = false;
		if (input.mouseDown[0] || input.mouseDown[1]) {
			halting = true;
		}
	}

	void placeField(int set, int tile, int i) {
		int sx = boxStart % 16;
		int sy = boxStart / 16;
		int ex = boxEnd % 16;
		int ey = boxEnd / 16;
		int mx = 0;
		int my = 0;
		for (int x = sx; x <= ex; x++) {
			for (int y = sy; y <= ey; y++) {
				mx = x + scrollX;
				my = y + scrollY;
				if (inBounds(mx, my)) {
					map().tile[mx][my].set[i] = set;
					map().tile[mx][my].tile[i] = tile;
					map().tile[mx][my].shiftX[i] = 0;
					map().tile[mx][my].shiftY[i] = 0;

				}
			}
		}

		mapBoxing = false;
		if (input.mouseDown[0] || input.mouseDown[1]) {
			halting = true;
		}

	}

	void placeBox(int mx, int my, int i, int dmx, int dmy) {
		int sx = boxStart % 16;
		int sy = boxStart / 16;
		int ex = boxEnd % 16;
		int ey = boxEnd / 16;
		int dx = 0;
		int dy = 0;
		int layer = i;
		for (int x = sx; x <= ex; x++) {
			for (int y = sy; y <= ey; y++) {
				dx = x - sx + mx + scrollX;
				dy = y - sy + my + scrollY;
				if (inBounds(dx, dy)) {
					if (alting) {
						layer = 4;
						if (y == ey) {
							layer = i;
						}
					}
					map().tile[dx][dy].set[layer] = boxSet;
					map().tile[dx][dy].tile[layer] = y * 16 + x + 1;
					if (layer > 0) {
						map().tile[dx][dy].shiftX[layer] = dmx;
						map().tile[dx][dy].shiftY[layer] = dmy;
					}
				}
			}
		}
		if (input.mouseDown[0] || input.mouseDown[1]) {
			halting = true;
		}
	}

	Tile getTile(int mx, int my) {
		if (inBounds(mx, my)) {
			return map().tile[mx][my];
		}
		return null;
	}

	public void mouseDown(int x, int y, int button) {
		try {
			// non dialog, non button, non text touches. overload this in specific scene

			if (Util.inBox(x, y, 20, 20 + 522, 40, 40 + 522)) {
				// clicked map
				// only use this for single click instances! it will only be called the one time
				// on mousedown
				// for painting, use update()
				int mx = ((x - 20) / 32);
				int my = ((y - 40) / 32);
				if (editMode < 7 || editMode == 7 || editMode == 9) {
					if (button == 1 && tileBox) {
						tileBox = false;
					} else if (ctrling && !tileBoxing && !tileBox && !mapBoxing) {
						mapBoxing = true;
						boxDown = my * 16 + mx;
						boxButton = button;
						boxUp = boxDown;
						updateBox();
					}
				}
			} else if (Util.inBox(x, y, 559, 559 + 512, 40, 40 + 512)) {
				int tx = (x - 559) / 32;
				int ty = (y - 40) / 32;
				if (editMode < 7) {
					// clicked tiles
					if (tileBox) {
						tileBox = false;
					}
					if (ctrling) { // start box select
						if (!tileBoxing) {
							tileBoxing = true;
							boxDown = ty * 16 + tx;
							boxUp = boxDown;
						}
					} else {
						changeTile(curSet, ty * 16 + tx + 1);
					}
				} else if (editMode == 7) {

				} else if (editMode == 8) {

				} else if (editMode == 9) {
					int a = ty * 16 + tx + 1;
					if (a != att && validAtt(a)) {
						att = a;
						for (int c = 0; c < 6; c++) {
							attData[c] = 0;
						}
						switchAtt(a);
					}
				}
			} else if (Util.inBox(x, y, 554 + 64, 554 + 64 + 384, 585 - 4, 585 - 4 + 32)) {
				int r = (x - 618) / 32;
				if (button == 0) {
					changeTile(recentSets[r], recentTiles[r]);
				} else {
					curSet = recentSets[r];
				}
			} else if (Util.inBox(x, y, 559, 559 + 32, 586, 586 + 32)) {
				curSet = curSelSet;
			}
		} catch (Exception e) {
			Log.error(e);
		}
	}

	void switchAtt(int a) {
		hideFrames();
		switch (a) {
		case 1: // wall
			break;
		case 2: // spawn

			break;
		case 3: // warp
			mapList.visible = true;
			mapList.sel = lastWarpMap;
			attData[0] = lastWarpMap;
			attData[1] = lastWarpX;
			attData[2] = lastWarpY;
			frmWarp.visible = true;
			break;
		case 4: // door

			break;
		case 5: // fx
			break;
		case 6: // light
			break;
		case 7: // NPC
			frmNPC.visible = true;
			break;
		case 8: //story
			frmStory.visible = true;
			break;
		case 9:
			frmItem.visible = true;
			break;
		}
	}

	boolean validAtt(int a) {
		if (a <= 56) {
			return true;
		}
		return false;
	}

	void hideFrames() {

		mapList.visible = false;
		setList.visible = false;
		frmWarp.visible = false;
		frmNPC.visible = false;
		frmStory.visible = false;
		frmItem.visible = false;
	}

	public void checkBox(int id) {
		if (id < 10) {
			vis[id] = checkBoxes.get(id + "").toggled;
		} else {

		}
	}

}
