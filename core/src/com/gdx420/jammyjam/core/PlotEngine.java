package com.gdx420.jammyjam.core;

import com.gdx420.jammyjam.scenes.PlayScene;

public class PlotEngine {
	public static void triggerPlot(StoryPoint sp) {
		
		System.out.println("StoryPoint triggered: " + sp.name);
	}
	public static void obtainItem(Item item) {
		if(!JammyJam.game.player.obtainedItems.contains(item)) {
			JammyJam.game.player.obtainedItems.add(item);
			item.onScreen = false;
			if(!item.text.isEmpty())
			{
				DialogData data = new DialogData(item.name, item.text);
				data.itemParent = item;
				PlayScene.dialogQueue.add(data);
			}
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
	
	private static NonPlayableCharacter lastNpc = null;
	private static DialogData lastDlg = null;
	private static boolean foundNpcDialog(NonPlayableCharacter npc, DialogData dlg) {
		if(dlg.wasDisplayed) {
			if(lastNpc != null && lastDlg != null) {
				doNpcActions(npc, dlg);
				return true;
			}
			return false;
		}
		dlg.wasDisplayed = true;
		lastNpc = npc;
		lastDlg = dlg;
		
		doNpcActions(npc, dlg);		
		return true;
	}
	
	private static void doNpcActions(NonPlayableCharacter npc, DialogData dlg) {
		dlg.npcParent = npc;
		PlayScene.dialogQueue.add(dlg);
		
		if(!dlg.item_given.isEmpty()) {
			for(Item item : JammyJam.game.loadedItems) {
				if(item.name.compareTo(dlg.item_given) == 0) {
					obtainItem(item);		
				}
			}
		}
	}
}
