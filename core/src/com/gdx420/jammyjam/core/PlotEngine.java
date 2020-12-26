package com.gdx420.jammyjam.core;

public class PlotEngine {
	public static void triggerPlot(Tile tile) {
		System.out.println("StoryPoint triggered: " + tile.att[0] + ", " + tile.att[1]);
	}
	public static void obtainItem(Tile tile) {
		System.out.println("Item obtained: " + tile.att[0] + ", " + tile.att[1]);
	}
	public static void npcInteraction(NonPlayableCharacter npc) {
		System.out.println("NPC interaction: " + npc.toString());
	}
}
