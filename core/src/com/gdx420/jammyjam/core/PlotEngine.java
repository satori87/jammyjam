package com.gdx420.jammyjam.core;

public class PlotEngine {
	public static void triggerPlot(Tile tile) {
		//System.out.println("StoryPoint triggered: " + tile.att[0] + ", " + tile.att[1]);
	}
	public static void obtainItem(Item item) {
		if(!JammyJam.game.player.obtainedItems.contains(item)) {
			JammyJam.game.player.obtainedItems.add(item);
			System.out.println("Found " + item.name);
			if(!item.text.isBlank())
				System.out.println("It reads: " + item.text);
		}
	}
		
	public static void npcInteraction(NonPlayableCharacter npc) {
		boolean itemDialogFound = false;
		boolean useDefault = true;
		for(Dialog dlg : npc.dialogs) {
			for(Item item : JammyJam.game.player.obtainedItems) {				
				if(dlg.item_required.compareTo(item.name) == 0) {
					itemDialogFound = foundNpcDialog(npc, dlg);
					useDefault = false;
					break;
				}
				if(itemDialogFound || useDefault)
					break;
			}
		}
		if(useDefault) {			
			for(Dialog dlg : npc.dialogs) {				
				if(dlg.item_required.compareTo("") == 0) {
					itemDialogFound = foundNpcDialog(npc, dlg);
					break;
				}
			}
		}
	}
	private static NonPlayableCharacter lastNpc = null;
	private static Dialog lastDialog = null;
	private static boolean foundNpcDialog(NonPlayableCharacter npc, Dialog dlg) {
		if(lastNpc == npc && lastDialog == dlg) {
				return false;
		}
		lastNpc = npc;
		lastDialog = dlg;
		
		System.out.println(npc.name + ": " + dlg.dialog);
		
		if(!dlg.item_given.isEmpty()) {
			for(Item item : JammyJam.game.loadedItems) {
				if(item.name.compareTo(dlg.item_given) == 0) {
					obtainItem(item);		
				}
			}
		}
			
		return true;
	}
}
