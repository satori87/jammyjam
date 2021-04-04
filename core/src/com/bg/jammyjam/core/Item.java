package com.bg.jammyjam.core;

public class Item {
	public String name = new String();
	public String tile_sheet = new String();
	public int source_x = -1;
	public int source_y = -1;
	public int width = -1;
	public int height = -1;
	public boolean active_sleep = false;
	public boolean active_awake = false;
	public boolean interact = true;
	
	public String text = new String();
	
	public boolean onScreen = false;
	public int x;
	public int y;

}
