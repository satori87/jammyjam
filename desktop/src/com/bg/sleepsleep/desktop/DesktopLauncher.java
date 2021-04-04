package com.bg.sleepsleep.desktop;

import com.bg.bearplane.engine.Bearplane;
import com.bg.bearplane.engine.Log;
import com.bg.jammyjam.core.JammyJam;

public class DesktopLauncher {

	public static void main(String[] args) {
		try {				
			Bearplane.createApplication(new Bearplane(new JammyJam(), args));
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}

}