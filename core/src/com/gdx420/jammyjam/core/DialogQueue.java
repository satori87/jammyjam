package com.gdx420.jammyjam.core;

import java.util.LinkedList;
import java.util.Queue;

import com.bg.bearplane.gui.DialogDisplay;
import com.bg.bearplane.gui.Scene;

public class DialogQueue {
	private static Queue<DialogData> dialogQueue = new LinkedList<DialogData>();
			
	public static void add(DialogData data) {
		dialogQueue.add(data);
	}
	
	public static boolean any() {
		return (dialogQueue.size() > 0);
	}
	
	public static void update() {
		if (Scene.dialogToDisplay == null && dialogQueue.size() > 0) {
			DialogData data = dialogQueue.poll();
			String words = data.dialog;
			Scene.dialogToDisplay = new DialogDisplay(Scene.scene, data.name, 400, words, new String[] { "Hmmm..." }, new String[] { "0" });
			Scene.dialogToDisplay.start(Scene.tick);
		}
	}
}
