package com.gdx420.jammyjam.core;

import com.bg.bearplane.engine.Bearplane;
import com.bg.bearplane.engine.Log;

public class ClientMain {

	public static void main(String[] args) {
		try {				
			Bearplane.createApplication(new Bearplane(new JammyJam(), args));
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}

}