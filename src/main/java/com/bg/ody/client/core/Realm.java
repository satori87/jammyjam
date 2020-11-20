package com.bg.ody.client.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.engine.Util.Coord;
import com.bg.bearplane.engine.Effect;
import com.bg.bearplane.engine.Floater;
import com.bg.bearplane.gui.Scene;

public class Realm {

	public static Realm realm;

	long tick = 0;

	// instances
	private static List<Effect> effects = new ArrayList<Effect>();
	private static List<Floater> floaters = new ArrayList<Floater>();

	public Realm() {		
		realm = this;
	}

	

	public static void addFloater(Coord e, int x, int y, String text, Color col, long aliveAt) {
		Floater f = new Floater(e, x, y, text, col, aliveAt);
		floaters.add(f);
	}

	public void update(long tick) {
		this.tick = tick;
		Effect fx = null;
		Iterator<Effect> itr = effects.iterator();
		List<Floater> drops = new ArrayList<Floater>();
		for (Floater f : floaters) {
			if (f.active) {
				f.update(tick);
			} else {
				drops.add(f);
			}
		}
		for (Floater f : drops) {
			floaters.remove(f);
		}
		while (itr.hasNext()) {
			fx = itr.next();
			if (fx != null && fx.fx != null) {
				fx.fx.update(Gdx.graphics.getDeltaTime());
				if (fx.fx.isComplete()) {
					fx.fx.free();
					effects.remove(fx);
				}
			}
		}
	}
	
	public void load() {
		//load our world stuff here
	}

	public void render() {
		for (Floater f : floaters) {
			f.render(tick);
		}
	}

	public static Effect addEffect(int type, int i, int x, int y, float scale) {
		Effect bfx = null;
		try {
			bfx = new Effect(effects.size(), type, i, x, y, scale);
			bfx.fx = Assets.effectPool.get(type).obtain();
			bfx.fx.setPosition(x, y);
			bfx.fx.scaleEffect(0.1f);
			effects.add(bfx);
		} catch (Exception e) {
			return null;
		}
		return bfx;
	}

	public static void addFX() {
		//use addEffect here
	}

	public static void renderFX(int i) {
		Effect fx = null;
		Iterator<Effect> itr = effects.iterator();
		while (itr.hasNext()) {
			fx = itr.next();
			if (fx.i == i) {
				fx.fx.draw(Scene.batcher);
			}
		}
	}

	public static void resetMap() {
		for (Effect e : effects) {
			e.fx.free();
		}
		effects.clear();
		addFX();
	}

}
