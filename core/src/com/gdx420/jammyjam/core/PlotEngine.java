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
		for(DialogData dlg : npc.dialogs) {				
			int matchedItemsCount = 0;
			int itemsRequiredCount = 0;
			for(String required : dlg.items_required) {
				if(required.isEmpty())
					continue;
				itemsRequiredCount++;
				for(Item item : JammyJam.game.player.obtainedItems) {			
					if(required.compareTo(item.name) == 0) {
						matchedItemsCount++;
						break;
					}
				}
			}
			if(matchedItemsCount == itemsRequiredCount) {				
				if(foundNpcDialog(npc, dlg)) {
					break;
				}
			}
		}
	}
	
	private static boolean foundNpcDialog(NonPlayableCharacter npc, DialogData dlg) {
		if(dlg.wasDisplayed)
			return false;
		dlg.wasDisplayed = true;
		
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
