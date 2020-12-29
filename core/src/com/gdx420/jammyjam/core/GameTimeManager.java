package com.gdx420.jammyjam.core;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameTimeManager {
	
	public void setGameClock(LocalTime timeInGame) {
		currentGameTime = timeInGame;
	}
		
	public int gameTimeMultiplier = 10; 	
	
	public void tickForward() {
		currentGameTime = currentGameTime.plusMinutes((int)(gameTimeMultiplier));
	}
	
	public String getGameTimeString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
		return currentGameTime.format(formatter);
	}
	
	public LocalTime getCurrentGameTime() {
		return currentGameTime;
	}
	
	private LocalTime currentGameTime;
}
