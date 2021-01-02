package com.gdx420.jammyjam.core;

public class SystemSettings {
	
	private static boolean musicEnabled = false;
	private static boolean mapEditorAvailable = true;
	
	
	public static boolean getMusicEnabled() { return musicEnabled; }
	public static void setMusicEnabled(boolean setEnabled) { musicEnabled = setEnabled; }
	
	public static boolean getMapEditorAvailable() { return mapEditorAvailable; }
	public static void setMapEditorAvailable(boolean setMapEditorAvailable) { mapEditorAvailable = setMapEditorAvailable; }

}
