package com.bg.bearplane.gui;

public interface Focusable {

	public void gainFocus();
	
	public void loseFocus();
	
	public int getTabIndex();
	
	public boolean canFocus();
	
	public String getID();
	
}
