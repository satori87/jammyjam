package com.bg.bearplane.ai.pathfinding.tiled;

public interface Pathfindable {

	public int getGraphWidth();
	
	public int getGraphHeight();	
	
	public FlatTiledNode[][] getTiles();
	
	public boolean inBounds(int x, int y);
	
	public boolean isValidConnection(FlatTiledNode n, int dir, int xOffset, int yOffset);
	
	public float getConnectionCost(FlatTiledNode n, int dir, int xOffset, int yOffset);
	
}
