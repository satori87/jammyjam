package com.bg.jammyjam.core;

public class Shared {

	public static final int MAP_WIDTH = 48;
	public static final int GAME_WIDTH = 1366;
	public static final int GAME_HEIGHT = 768;
	public static final int NUM_MAPS = 201;
	public static final int MOVE_TIME = 500;
	public static final int BOUNDING_BOX = 5;
	public static final int NUM_ITEMS_PER_DIALOG = 10;
	public static final int START_MAP = 111;
	public static final int  START_X = 305;
	public static final int  START_Y = 280;
	
	
	public static enum Attributes {
		NO_ATTRIBUTE, WALL, MONSTER, WARP, DOOR, EFFECT, LIGHT, NPC_SPAWN, STORYPOINT, ITEM
	}

	public static String[] layerName = new String[] { "Ground", "BG1", "BG2", "Mid", "FG1", "FG2", "Ceiling", "Wall",
			"Shadow", "Att" };
	public static String[] panelName = new String[] { "Load", "Save" };
	public static String[] tilesets = new String[] { "animDoors", "bridges_1", "bridges_2", "Building_Adobe",
			"Building_Brick_1", "Building_Brick_2", "Building_Brick_3", "Building_Brick_4", "Building_Brick_5",
			"Building_Church", "Building_Church_2", "Building_Decor", "Building_Decor_1", "Building_Industrial_1",
			"Building_Industrial_2", "Building_Roman_1", "Building_Roman_2", "Building_Roman_Decor", "Building_Rustic",
			"Building_Wood", "decor_1", "decor_2", "Indoor_Decor_1", "Indoor_Decor_2", "Indoor_Floors_1",
			"Indoor_Floors_2", "Indoor_Floors_Walls_1", "Indoor_Furniture_1", "Indoor_Furniture_2",
			"Indoor_Furniture_3", "Indoor_Stairs", "Indoor_Walls_1", "Items_1", "Outdoor_Beach", "Outdoor_Camp",
			"Outdoor_Cliff_1", "Outdoor_Cliff_2", "Outdoor_Decor_1", "Outdoor_Decor_2", "Outdoor_Decor_3",
			"Outdoor_Fences_1", "Outdoor_Ground_1", "Outdoor_Ground_2", "Outdoor_Ground_3", "Outdoor_Ground_4",
			"Outdoor_Ground_Water_1", "Outdoor_Lights", "Outdoor_Path_1", "Outdoor_Path_2", "Outdoor_Path_3",
			"Outdoor_Path_4", "Outdoor_Trees_1", "Outdoor_Trees_2", "Outdoor_Trees_3", "Outdoor_Vegetation_1",
			"Outdoor_Vegetation_2", "Outdoor_Walls", "Roof_1", "Wall_Doors", "Wall_Outdoor_Decor",
			"Wall_Outdoor_Fences_Decor", "Wall_Outdoor_Windows", "cars_bear", "cars_crane", "cars_gold", "cars_police",
			"cars_silver", "cars_white", "road_markings_white", "road_markings_yellow", "road_signs_de", "street_dark",
			"street_light", "street_misc", "Outdoor_Trees_4", "dungeonex", "markings" };

	public static String[] spritesets = new String[] { "Girl_1", "Girl_2", "Guy_1", "Guy_2", "Guy_3", "Guy_4", "Guy_5",
			"Guy_6", "Guy_Detective", "Guy_Police" };

}
