package com.bg.bearplane.engine;

public interface Bearable {

	public void create();
	
	public void update();
	
	public void dispose();
	
	public void addScenes();
	
	public int getGameWidth();
	
	public int getGameHeight();
	
	public String getGameName();
	
	public void loaded();

	public void addTimers();
	
	public void doTimer(int interval);
	
	public String getClientVersion();
	
	public String getEffectsPath();
	
	public String getAssetsPath();
	
	public int getDisplayMode();
		
	public boolean isResizable();
	
	public int getWindowWidth();
	
	public int getWindowHeight();
	
	public boolean isvSync();
	
	public BaseAssets getAssets();
	
	public boolean isRelease();
	
	public BaseRealm getRealm();
	
	public void resume();
	
	public void hide();
	
	public void show();
	
	public void pause();
	
	public void setTick(long tick);	
	
}
