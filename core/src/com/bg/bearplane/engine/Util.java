package com.bg.bearplane.engine;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.Json;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

	public class Coord {

		public int x, y;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	public static double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	public static double distance(float x1, float y1, float x2, float y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	public static void exportJSON(String filename, Object o) {
		try {
			Json json = new Json();
			json.toJson(o, Gdx.files.local(filename));
		} catch (Exception e) {
			Log.error(e);
		}
	}

	public static boolean exists(String filename) {
		File file = new File(filename);
		return file.exists();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object importJSON(String filename, Class c) {
		Json mapper = new Json();
		try {
			FileHandle f = Gdx.files.local(filename);
			if(f != null && f.exists()) {
				return mapper.fromJson(c, f);
			}
		} catch (Exception e) {
			Log.error(e);
		}
		return null;
	}

	public static void writeFile(String filename, String s) {
		try {
			Files.write(Paths.get(filename), s.getBytes());
		} catch (IOException e) {
			Log.error(e);
		}
	}

	public static String readFile(String filename) {
		String s = "";
		try {
			s = new String(Files.readAllBytes(Paths.get(filename)));
		} catch (Exception e) {
			Log.warn(filename + " not found for reading");
		}
		return s;
	}

	public static String encryptPassword(String password) {
		try {
			MessageDigest messageDigest;
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			return new String(messageDigest.digest());
		} catch (Exception e) {
			Log.error(e);

		}
		return "";
	}

	public static int rndInt(int min, int max) {
		return randInt(min, max);
	}

	static Random rand = new Random(System.currentTimeMillis() / 7);

	public static int randInt(int min, int max) {

		// NOTE: This will (intentionally) not run as written so that folks
		// copy-pasting have to think about how to initialize their
		// Random instance. Initialization of the Random instance is outside
		// the main scope of the question, but some decent options are to have
		// a field that is initialized once and then re-used as needed or to
		// use ThreadLocalRandom (if using at least Java 1.7).
		//
		// In particular, do NOT do 'Random rand = new Random()' here or you
		// will get not very good / not very random results.

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static String getDate(String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(new Date());
	}

	public static byte[][] divideArray(byte[] source, int chunksize) {
		byte[][] ret = new byte[(int) Math.ceil(source.length / (double) chunksize)][chunksize];
		try {
			int start = 0;
			for (int i = 0; i < ret.length; i++) {
				ret[i] = Arrays.copyOfRange(source, start, start + chunksize);
				start += chunksize;
			}
		} catch (Exception e) {
			Log.error(e);
		}
		return ret;
	}

	static public boolean inCenteredBox(int x, int y, int centerX, int centerY, int width, int height) {
		int topY = centerY - (height / 2);
		int bottomY = centerY + (height / 2);
		int leftX = centerX - (width / 2);
		int rightX = centerX + (width / 2);
		if (x > leftX && x < rightX && y > topY && y < bottomY) {
			return true;
		}
		return false;
	}

	static public boolean inBox(int x, int y, int lowerX, int upperX, int lowerY, int upperY) {
		return (x >= lowerX && x < upperX && y >= lowerY && y < upperY);
	}

	public static int getDir(int fx, int fy, int tx, int ty) {
		if (ty < fy) {
			return 0;
		} else if (ty > fy) {
			return 1;
		} else if (tx < fx) {
			return 2;
		} else if (tx > fx) {
			return 3;
		}
		return 0;
	}

	public static int reverseDir(int d) {
		int n = d;
		if (d == 0) {
			n = 1;
		} else if (d == 1) {
			n = 0;
		} else if (d == 2) {
			n = 3;
		} else if (d == 3) {
			n = 2;
		}
		return n;
	}

	public static void assureDir(String s) {
		File directory = new File(s);
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	public static int setBit(int value, int bit) {
		// Create mask
		int mask = 1 << bit;
		// Set bit
		return value | mask;
	}

	public static int clearBit(int x, int kth) {
		return (x & ~(1 << kth));
	}

	public static boolean checkBit(int value, int bit) {
		return ((value >> bit) & 1) != 0;
	}

	public static int setBit(int value, int bit, boolean to) {
		if (to) {
			return setBit(value, bit);
		} else {
			return clearBit(value, bit);
		}
	}

	public static List<String> wrapText(float scale, int width, String text) {
		List<String> lines = new ArrayList<String>();
		String line = "";
		String word = "";
		for (int c = 0; c < text.length(); c++) { // read string one byte at a time, and check width at every char
			String p = text.substring(c, c + 1); // single letter
			if (p.equals(" ")) { // finished a word, try to add it on
				if (line.length() > 0) {
					if (Bearplane.assets.getStringWidth(line + " " + word, scale, 0, 1) > width) { // wont fit, start
																									// new
																									// line
						lines.add(line);
						line = word;
						word = "";
					} else { // this word fits no problem
						line += " " + word;
						word = "";
					}
				} else { // we're on first word of line, is it too wide?
					if (Bearplane.assets.getStringWidth(word, scale, 0, 1) > width) {
						line = word + " ";
						word = "";
					} else {
						line = word;
						word = "";
					}
				}
			} else {
				if (line.length() == 0 && Bearplane.assets.getStringWidth(word + p, scale, 0, 1) > width) {
					// first word is too wide, split it. i.e. AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
					lines.add(word);
					word = "";
					word = p;
				} else if (line.length() > 0
						&& Bearplane.assets.getStringWidth(line + " " + word + p, scale, 0, 1) > width) {
					lines.add(line);
					line = "";
					word += p;
				} else { // keep adding to this worddddd
					word += p;
				}
			}
		}
		if (word.length() > 0) { // get last word, since loop only makes new lines when it reaches end
			line += " " + word;
		}
		if (line.length() > 0) { // get last line, same reason as above
			lines.add(line);
		}
		return lines;
	}

	public static void saveTexture(final String s, final int x, final int y, final int w, final int h,
			final Texture tex) {
		try {
			final FileHandle fh = new FileHandle(String.valueOf(s) + ".png");
			final Pixmap pixmap = getPixmapRegion(x, y, w, h, false, tex);
			PixmapIO.writePNG(fh, pixmap);
			pixmap.dispose();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static Pixmap getPixmapRegion(final int x, final int y, final int w, final int h, final boolean yDown,
			final Texture tex) {
		final TextureData td = tex.getTextureData();
		if (!td.isPrepared()) {
			td.prepare();
		}
		final Pixmap pm = td.consumePixmap();
		final Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
		pixmap.drawPixmap(pm, x, y, w, h, 0, 0, w, h);
		if (yDown) {
			final ByteBuffer pixels = pixmap.getPixels();
			final int numBytes = w * h * 4;
			final byte[] lines = new byte[numBytes];
			final int numBytesPerLine = w * 4;
			for (int i = 0; i < h; ++i) {
				pixels.position((h - i - 1) * numBytesPerLine);
				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
			}
			pixels.clear();
			pixels.put(lines);
		}
		return pixmap;
	}

}
