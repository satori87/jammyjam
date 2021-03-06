package com.bg.jammyjam.core;

import com.bg.bearplane.gui.Scene;
import com.bg.jammyjam.scenes.AwakePlayScene;
import com.bg.jammyjam.scenes.PlayScene;
import com.bg.jammyjam.scenes.SleepPlayScene;

public class PlotEngine {
	public static long lastStoryPointTimestamp = System.currentTimeMillis(); 
	
	public static void triggerStoryPoint(StoryPoint sp) {
		
		// prevent multiple times in a row
		if(System.currentTimeMillis() - lastStoryPointTimestamp < 2000)
			return;
		
		lastStoryPointTimestamp = System.currentTimeMillis();
		
		if( ( sp.active_sleep && Scene.scene instanceof SleepPlayScene)
				|| (sp.active_awake && Scene.scene instanceof AwakePlayScene)) {
			for(DialogData dlg : sp.dialogs) {				
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
					if(foundStoryPoint(sp, dlg)) {
						break;
					}
				}
			}
		}		
	}
	public static void obtainItem(Item item) {
		if(item.interact == false)
			return;
		if(!JammyJam.game.player.obtainedItems.contains(item)) {
			JammyJam.game.player.obtainedItems.add(item);
			item.onScreen = false;
			if(!item.text.isEmpty())
			{
				DialogData data = new DialogData(item.name, item.text);
				data.itemParent = item;
				PlayScene.dialogQueue.add(data);
				if(item.name.compareTo("ZombiePotion") == 0)
					JammyJam.isZombie = true;
			}
		}
	}
		
	public static void npcInteraction(NonPlayableCharacter npc) {
		// prevent multiple times in a row
		if(System.currentTimeMillis() - lastStoryPointTimestamp < 2000)
			return;
		
		lastStoryPointTimestamp = System.currentTimeMillis();
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
	private static DialogData lastNpcDlg = null;
	private static boolean foundNpcDialog(NonPlayableCharacter npc, DialogData dlg) {
		if(dlg.wasDisplayed) {
			if(lastNpc != null && lastNpcDlg != null) {
				doNpcActions(npc, dlg);
				return true;
			}
			return false;
		}
		dlg.wasDisplayed = true;
		lastNpc = npc;
		lastNpcDlg = dlg;
		
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
		if(dlg.win_game) {
			DialogData data = new DialogData("Won Game", "Congrats kid, you snagged the baddie and saved the day. Keep an eye out for the full version of this game someday.");
			PlayScene.dialogQueue.add(data);
			if(JammyJam.gameIsWon)
				Scene.change("menu");
			JammyJam.gameIsWon = true;
		}
	}
	
	private static StoryPoint lastStoryPoint = null;
	private static DialogData lastStoryDlg = null;
	private static boolean foundStoryPoint(StoryPoint sp, DialogData dlg) {
		if(dlg.wasDisplayed) {
			if(lastStoryPoint != null && lastStoryDlg != null) {
				doStoryActions(sp, dlg);
				return true;
			}
			return false;
		}
		dlg.wasDisplayed = true;
		lastStoryPoint = sp;
		lastStoryDlg = dlg;
		
		doStoryActions(sp, dlg);		
		return true;
	}
	private static void doStoryActions(StoryPoint sp, DialogData dlg) {
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
