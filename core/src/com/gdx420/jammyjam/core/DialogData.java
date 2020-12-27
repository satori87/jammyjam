package com.gdx420.jammyjam.core;

public class DialogData {
	public String name = new String();
	public String []items_required = new String[Shared.NUM_ITEMS_PER_DIALOG];
	public String dialog = new String();
	public String item_given = new String();
	
	public boolean wasDisplayed = false;
}
