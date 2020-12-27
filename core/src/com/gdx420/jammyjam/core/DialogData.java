package com.gdx420.jammyjam.core;

public class DialogData {
	public String name = new String();
	public String []items_required = new String[Shared.NUM_ITEMS_PER_DIALOG];
	public String dialog = new String();
	public String item_given = new String();
	public String warp = new String();
	
	public DialogData() {}
	public DialogData(String _name, String _dialog) {
		name = _name;
		dialog = _dialog;
	}
	
	public boolean wasDisplayed = false;
	// warning only one of these will be set, always check for null first
	public NonPlayableCharacter npcParent = null;
	public Item itemParent = null;
}
