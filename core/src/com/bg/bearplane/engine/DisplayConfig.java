package com.bg.bearplane.engine;

import java.io.Serializable;

public class DisplayConfig extends BaseConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	public int DISPLAY_MODE = DisplayMode.WINDOW;
	public int WINDOW_WIDTH = 1366;
	public int WINDOW_HEIGHT = 768;
	public boolean RESIZABLE = false;
	public boolean ISVSYNC = true;

}
