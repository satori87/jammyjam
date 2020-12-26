package com.gdx420.jammyjam.core;

public class NonPlayableCharacter extends Character {
	
	public boolean onScreen = false;

	public boolean checkCollision(Character character, int radius) {
		float xVector = character.x - x;
		float yVector = character.y - y;
		float distanceSqaured =  (xVector * xVector) + (yVector * yVector);
		if(distanceSqaured < Math.pow(radius+radius, 2))
			return true;
		return false;
	}
}
