package com.gdx420.jammyjam.core;

import java.util.ArrayList;
import java.util.List;

import com.bg.bearplane.gui.Scene;

import com.gdx420.jammyjam.scenes.AwakePlayScene;
import com.gdx420.jammyjam.scenes.SleepPlayScene;

public class PlotEngine {
	public static List<Renderable> clueList = new ArrayList<Renderable>();
	public static void clearClueCache() { 
		clueList.clear();
	}
	
	public static boolean checkStoryPoint(StoryPoint sp) {
		boolean clueExists = false;
		for(Renderable r : clueList) {
			if(r.x == sp.x && r.y == sp.y) {
				clueExists = true;
			}
		}
		if(!clueExists) {
			Renderable clue = null;
			for(Renderable r : clueList) {
				if(r == null) {
					clue = r;
					continue;
				}
			}
			if(clue == null) {
				clue = new Renderable();
				clue.sprite = "magnifying-glass";
				clue.x = sp.x;
				clue.y = sp.y;
				clue.width = clue.height = 32;
			}
			clueList.add(clue);
		}
		if(JammyJam.isSpaceBarPressed)
			return true;
		return false;
	}	
	
	public static void triggerStoryPoint(StoryPoint sp) {
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
				DialogData data = new DialogData(item.name, item.name + " Found!  " + item.text);
				DialogQueue.add(data);
				if(item.name.compareTo("ZombiePotion") == 0)
					JammyJam.isZombie = true;
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
	private static DialogData lastNpcDlg = null;
	private static boolean foundNpcDialog(NonPlayableCharacter npc, DialogData dlg) {
		if(dlg.wasDisplayed) {
			if(lastNpc != null && lastNpcDlg != null) {
				doNpcActions(npc, dlg);
				for(Renderable clue : clueList) {
					if(npc.x == clue.x && npc.y == clue.y) {
						clue = null; // factory reset
					}
				}
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
		DialogData data = new DialogData(npc.name, npc.name + " : " + dlg.dialog);
		DialogQueue.add(dlg);
		
		if(!dlg.item_given.isEmpty()) {
			for(Item item : JammyJam.game.loadedItems) {
				if(item.name.compareTo(dlg.item_given) == 0) {
					obtainItem(item);		
				}
			}
		}
		if(dlg.win_game) {			
			JammyJam.gameIsWon = true;
		}
	}
	
	private static boolean foundStoryPoint(StoryPoint sp, DialogData dlg) {
		doStoryActions(sp, dlg);		
		return true;
	}
	private static void doStoryActions(StoryPoint sp, DialogData dlg) {		
		DialogQueue.add(dlg);
		
		if(!dlg.item_given.isEmpty()) {
			for(Item item : JammyJam.game.loadedItems) {
				if(item.name.compareTo(dlg.item_given) == 0) {
					obtainItem(item);		
				}
			}
		}
	}
}
