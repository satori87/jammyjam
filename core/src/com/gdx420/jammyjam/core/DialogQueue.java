package com.gdx420.jammyjam.core;

import java.util.LinkedList;
import java.util.Queue;

import com.bg.bearplane.gui.DialogDisplay;
import com.bg.bearplane.gui.Scene;

public class DialogQueue {
	private static Queue<DialogData> dialogQueue = new LinkedList<DialogData>();
	public static DialogDisplay dialogToDisplay = null;
			
	public static void add(DialogData data) {
		if(!dialogQueue.contains(data)) {
			if(dialogToDisplay == null) {
				dialogQueue.add(data);
			} else if(dialogToDisplay.text.compareTo(data.dialog) != 0) {
					dialogQueue.add(data);
			}
		}
	}
	
	public static boolean any() {
		return (dialogQueue.size() > 0);
	}
	
	public static void updateDisplayDialog(long tick) {

		if(dialogToDisplay != null && dialogToDisplay.active)
			dialogToDisplay.update(tick);
	}
	
	public static void update() {
		if(dialogToDisplay != null && dialogToDisplay.active == false) {
			if(Scene.tick > dialogToDisplay.endTick + 300 ) {
				dialogToDisplay = null;
			}
		}
		
		if (dialogToDisplay == null && dialogQueue.size() > 0) {
			DialogData data = dialogQueue.poll();
			String words = data.dialog;
			dialogToDisplay = new DialogDisplay(Scene.scene, data.name, 400, words, new String[] { "Hmmm..." }, new String[] { "0" });
			dialogToDisplay.start(Scene.tick);
			dialogToDisplay.active = true;
		}
	}
	
	public static void render() {
		if(dialogToDisplay != null && dialogToDisplay.active)
			dialogToDisplay.render();
	}
	
	public static void confirmDialog(long tick) {
		if(dialogToDisplay != null)
		{
			dialogToDisplay.choose(dialogToDisplay.choices.get(0));
			dialogToDisplay.active = false;	
			dialogToDisplay.endTick = tick;
		}
	}
	
	public static boolean isDialogDisplayed() {
		if (dialogToDisplay != null && dialogToDisplay.active) {
			return true;
		}
		return false;
	}
}
