package com.bg.bearplane.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.engine.Util.Coord;
import com.bg.bearplane.gui.Scene;

public abstract class BaseRealm {

	public BaseAssets assets;
	long tick = 0;

	// instances
	private List<Effect> effects = new ArrayList<Effect>();
	private List<Floater> floaters = new ArrayList<Floater>();

	public BaseRealm(BaseAssets assets) {		
		this.assets = assets;
	}

	public void addFloater(Coord e, int x, int y, String text, Color col, long aliveAt) {
		Floater f = new Floater(e, x, y, text, col, aliveAt);
		floaters.add(f);
	}

	public void updateBase() {
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
	
	public abstract void update();
	
	public abstract void load();

	public void renderFloaters() {
		for (Floater f : floaters) {
			f.render(tick);
		}
	}

	public Effect addEffect(int type, int i, int x, int y, float scale) {
		Effect bfx = null;
		try {
			bfx = new Effect(effects.size(), type, i, x, y, scale);
			bfx.fx = assets.effectPool.get(type).obtain();
			bfx.fx.setPosition(x, y);
			bfx.fx.scaleEffect(0.1f);
			effects.add(bfx);
		} catch (Exception e) {
			return null;
		}
		return bfx;
	}

	public abstract void addFX();

	public void renderFX(int i) {
		Effect fx = null;
		Iterator<Effect> itr = effects.iterator();
		while (itr.hasNext()) {
			fx = itr.next();
			if (fx.i == i) {
				fx.fx.draw(Scene.batcher);
			}
		}
	}

	public void reset() {
		for (Effect e : effects) {
			e.fx.free();
		}
		effects.clear();
		addFX();
	}

}