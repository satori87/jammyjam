package com.gdx420.jammyjam.core;

public class NonPlayableCharacter extends Character {
	
	public String name = new String();
	public String tile_sheet = new String();
	public int source_x = -1;
	public int source_y = -1;
	public int width = -1;
	public int height = -1;
	
	public Dialog[] dialogs;
	
	public boolean onScreen = false;	
}
