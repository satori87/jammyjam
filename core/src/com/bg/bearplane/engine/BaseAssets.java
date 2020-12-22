package com.bg.bearplane.engine;

import java.io.File;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;

public abstract class BaseAssets {

	public TextureRegion[] framePiece;
	Texture frameTex;
	public TextureRegion[] checkBox = new TextureRegion[2];
	public TextureRegion frameTitle;	
	public TextureRegion[][] arrowButton = new TextureRegion[2][4];
	public TextureRegion[] sliderBar = new TextureRegion[3];
	public TextureRegion[] sliderPiece = new TextureRegion[2];
	public TextureRegion[][] button;
	public TextureRegion[][] field; // lit, section
	public TextureRegion[][] font; // type, ascii code
	public int[] fontWidth; // type, ascii code
	public Texture bg[] = new Texture[20];
	public int[] fontX;
	public final AssetManager manager = new AssetManager();
	Texture texture;
	public static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	public HashMap<String, String> textureList = new HashMap<String, String>();
	public IntMap<ParticleEffect> effectData = new IntMap<ParticleEffect>();
	public IntMap<ParticleEffectPool> effectPool = new IntMap<ParticleEffectPool>();
	static String[] effectnames;
	
	public void preloadNecessities() {
		try {
			for (int i = 0; i < 5; i++) {
				manager.load(Bearplane.game.getAssetsPath() + "/bg" + i + ".png", Texture.class);
			}
			loadFont();
			manager.load(Bearplane.game.getAssetsPath() + "/frame.png", Texture.class);
		
			File f = null;
			f = new File(new File(".").getCanonicalPath() + Bearplane.game.getEffectsPath());
			if (f != null) {
				effectnames = f.list();
				if (effectnames != null) {
					for (String s : effectnames) {
						if (s.substring(s.length() - 2).equals(".p")) {
							manager.load(Bearplane.game.getEffectsPath() + "/" + s, ParticleEffect.class);
						}
					}
				}
			}
		} catch (Exception e) {
			Log.error(e);
		}
	}

	protected void preloadAllPNGFromDir(String d) {
		try {
			File f = new File(new File(".").getCanonicalPath() + "/" + d);
			File[] fileList = f.listFiles();
			String s;
			for (File ff : fileList) {
				if (!ff.isDirectory()) {
					s = ff.getName();
					if (s.substring(s.length() - 4).equals(".png")) {
						s = s.substring(0, s.length() - 4);
						textureList.put(s, d + "/" + s + ".png");
						manager.load(d + "/" + s + ".png", Texture.class);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadNecessities() {
		try {
			int i = 0;
			for (i = 0; i < 5; i++) {
				bg[i] = manager.get(Bearplane.game.getAssetsPath() + "/bg" + i + ".png");
			}
			texture = manager.get(Bearplane.game.getAssetsPath() + "/frame.png");
			frameTex = texture;
			framePiece = new TextureRegion[8];
			for (i = 0; i < 8; i++) {
				framePiece[i] = newTR(texture, i * 32, 0, 32, 32);
			}

			// arrow btns
			for (int a = 0; a < 4; a++) {
				for (int b = 0; b < 2; b++) {
					arrowButton[b][a] = newTR(texture, a * 16, 56 + b * 16, 16, 16);
				}
			}

			frameTitle = newTR(texture,0,32,160,24);
			
			checkBox[0] = newTR(texture, 106, 56, 13, 13);
			checkBox[1] = newTR(texture, 106, 56 + 13, 13, 13);

			for (int a = 0; a < 3; a++) {
				sliderBar[a] = newTR(texture, 64 + a * 10, 56, 32, 32);
			}

			for (int a = 0; a < 2; a++) {
				sliderPiece[a] = newTR(texture, 94, 56, 32 + a * 16, 32);
			}

			button = new TextureRegion[2][10];
			for (int b = 0; b < 2; b++) {
				for (i = 0; i < 8; i++) {
					button[b][i] = new TextureRegion(texture, 119 + i * 8, 56 + b * 8, 8, 8);
					fix(button[b][i], false, true);
				}
				button[b][8] = new TextureRegion(texture, 119 + 64, 56 + b * 8, 8, 8);
				fix(button[b][8], false, true);
				button[b][9] = new TextureRegion(texture, 119 + 64 + 8, 56 + b * 8, 8, 8);
				fix(button[b][9], false, true);
			}

			field = new TextureRegion[2][3];
			for (int b = 0; b < 2; b++) {
				field[b][0] = new TextureRegion(texture, 160, 32 + b * 26, 6, 24);
				fix(field[b][0], false, true);
				field[b][1] = new TextureRegion(texture, 18 + 160, 32 + b * 26, 4, 24);
				fix(field[b][1], false, true);
				field[b][2] = new TextureRegion(texture, 58 + 160, 32 + b * 26, 6, 24);
				fix(field[b][2], false, true);

			}
			texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			String n = "";
			for (String s : textureList.keySet()) {
				textures.put(s, (Texture)manager.get(textureList.get(s)));
			}

			if (effectnames != null) {
				for (String s : effectnames) {
					if (s.substring(s.length() - 2).equals(".p")) {
						n = s.substring(0, s.length() - 2);
						ParticleEffect pe = manager.get(Bearplane.game.getEffectsPath() + "/" + s);
						pe.flipY();
						effectData.put(Integer.parseInt(n), pe);
						ParticleEffectPool pec = new ParticleEffectPool(pe, 1, 20);
						effectPool.put(Integer.parseInt(n), pec);
					}
				}
			}
		} catch (Exception e) {
			Log.error(e);
		}
	}

	protected Texture loadLocalTexture(String name) {
		Texture texture = null;
		try {
			FileHandle fh = Gdx.files.local(Bearplane.game.getAssetsPath() + "/" + name + ".png");
			if (fh.exists()) {
				texture = new Texture(fh);
			}
		} catch (Exception e) {
			Log.error(e);
		}
		return texture;
	}

	private TextureRegion newTR(Texture tex, int x, int y, int w, int h) {
		TextureRegion t = new TextureRegion(tex, x, y, w, h);
		fix(t, false, true);
		return t;
	}

	private void fix(TextureRegion tex, boolean flipX, boolean flipY) {
		fixBleeding(tex);
		tex.flip(flipX, flipY);
		tex.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	private void fixBleeding(TextureRegion region) {
		float fix = 0.01f;
		float x = region.getRegionX();
		float y = region.getRegionY();
		float width = region.getRegionWidth();
		float height = region.getRegionHeight();
		float invTexWidth = 1f / region.getTexture().getWidth();
		float invTexHeight = 1f / region.getTexture().getHeight();
		region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth,
				(y + height - fix) * invTexHeight); // Trims Region
	}

	public void dispose() {
		if (texture != null) {
			texture.dispose();
		}
		if (frameTex != null) {
			frameTex.dispose();
		}
	}

	public float getStringWidth(String s, float scale, float padding, float spacing) {
		float total = 0;
		for (char c : s.toCharArray()) {
			int ascii = (int) c;
			total += fontWidth[ascii] * scale + padding * 2 + spacing;
		}
		return total;
	}

	private void loadFont() {
		int i = 0;
		Texture texture = loadLocalTexture("font");
		font = new TextureRegion[2][256];
		fontWidth = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 7, 5, 6, 9, 9, 10, 10, 3, 5, 5, 9, 7, 3, 8, 3, 7, 7, 5, 7, 7, 8, 7, 7, 7, 7, 7, 3, 3, 8, 6, 8,
				7, 9, 7, 7, 7, 8, 7, 7, 7, 7, 5, 8, 7, 7, 9, 8, 7, 7, 8, 8, 7, 7, 7, 7, 9, 8, 7, 7, 5, 7, 5, 10, 7, 5,
				7, 7, 7, 7, 7, 6, 7, 7, 5, 5, 7, 4, 9, 7, 7, 7, 8, 7, 7, 7, 7, 7, 9, 7, 7, 7, 6, 3, 6, 8, 9, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		fontX = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 1, 10, 17, 25, 36, 47, 59, 71, 76, 83, 90, 101, 110, 115, 125, 130, 139, 148, 155, 164, 173, 183,
				192, 201, 210, 219, 228, 233, 238, 248, 256, 266, 275, 286, 295, 304, 313, 323, 332, 341, 350, 359, 366,
				376, 385, 394, 405, 415, 424, 433, 443, 453, 462, 471, 480, 489, 500, 510, 519, 528, 535, 544, 551, 563,
				572, 579, 588, 597, 606, 615, 624, 632, 641, 650, 657, 664, 673, 679, 690, 699, 708, 717, 727, 736, 745,
				754, 763, 772, 783, 792, 801, 810, 818, 823, 831, 841, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0 };

		for (i = 0; i < 256; i++) {
			if (fontWidth[i] > 0) {
				fontWidth[i] += 1;
				for (int t = 0; t < 2; t++) {
					font[t][i] = new TextureRegion(texture, fontX[i], t * 16, fontWidth[i], 16);
					fix(font[t][i], false, true);
					font[t][i].getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
				}
			}
		}
	}

	public abstract void preload();

	public abstract void load();

}
