package com.bg.ody.client.core;

import java.io.File;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.bg.bearplane.engine.BearEssentials;
import com.bg.bearplane.engine.BearNecessities;
import com.bg.bearplane.engine.Log;

public class Assets extends BearNecessities implements BearEssentials {

	public static Cursor[] cursor = new Cursor[3];

	static String[] effectnames;

	public void preload() {
		try {
			File f = null;
			f = new File(new File(".").getCanonicalPath() + "/assets/effects");
			if (f != null) {
				effectnames = f.list();
				if (effectnames != null) {
					for (String s : effectnames) {
						if (s.substring(s.length() - 2).equals(".p")) {
							manager.load("assets/effects/" + s, ParticleEffect.class);
						}
					}
				}
			}
			loadAllPNGFromDir("assets");
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}

	public void load() {
		try {
			String n = "";
			for (String s : textureList.keySet()) {
				textures.put(s, manager.get(textureList.get(s)));
			}

			if (effectnames != null) {
				for (String s : effectnames) {
					if (s.substring(s.length() - 2).equals(".p")) {
						n = s.substring(0, s.length() - 2);
						ParticleEffect pe = manager.get("assets/effects/" + s);
						pe.flipY();
						Realm.effectData.put(Integer.parseInt(n), pe);
						ParticleEffectPool pec = new ParticleEffectPool(pe, 1, 20);
						Realm.effectPool.put(Integer.parseInt(n), pec);
					}
				}
			}
		} catch (Exception e) {
			Log.error(e);
			System.exit(0);
		}
	}
}
