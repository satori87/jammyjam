package com.bg.bearplane.engine;

import java.io.PrintStream;

public class Log {

	static String logFile = "log.txt";
	static int hour = 0;

	public static boolean useFile = false;

	public static void hacker(String s) {
		PrintStream cur = System.out;
		if (useFile) {
			setOut("logs/hacker.txt");
		}
		MinLog.error(s);
		if (useFile) {
			System.setOut(cur);
		}
	}

	public static void info(String s) {
		update();
		MinLog.info(s);
	}

	public static void debug(String s) {
		update();
		MinLog.debug(s);
	}

	public static void debug(int i) {
		update();
		MinLog.debug(i + "");
	}
	
	public static void debug(Exception e) {
		update();
		MinLog.error("Exception: " + e);
		for (StackTraceElement ste : e.getStackTrace()) {
			MinLog.debug(ste.toString());
		}

	}

	public static void warn(String s) {
		update();
		MinLog.warn(s);
	}

	public static void error(String s) {
		update();
		MinLog.error(s);
		System.exit(0);
	}

	public static void error(Exception e) {
		update();
		MinLog.error("Exception: " + e);
		for (StackTraceElement ste : e.getStackTrace()) {
			MinLog.info(ste.toString());
		}
		System.exit(0);
	}

	public static void init(String[] args) {
		Util.assureDir("logs");		
		MinLog.DEBUG(); //keep kryo from running its fucking mouth		
		if( Bearplane.game.isRelease()) {
			useFile = true;
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-v":
					MinLog.ERROR();
					break;
				case "-vv":
					MinLog.WARN();
					break;
				case "-vvv":
					MinLog.INFO();
					break;
				case "-vvvv":
					MinLog.DEBUG();
					break;
				default:
					com.esotericsoftware.minlog.Log.NONE();
					break;
				}
			}
		} else {
			useFile = false;
			MinLog.DEBUG();
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-f":
					useFile = true;
					break;
				}
			}
		}

		if (useFile) {
			setLogFile();
		}
	}

	public static void update() {
		if (useFile) {
			int h = getHour();
			if (h != hour) {
				setLogFile();
			}
		}
	}

	static int getHour() {
		String d = Util.getDate("MM-dd-yy-HH");
		return Integer.parseInt(d.substring(d.length() - 2));
	}

	public static void setOut(String s) {
		try {
			PrintStream ps = new PrintStream(s);
			System.setOut(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setLogFile() {
		try {
			hour = getHour();
			String dir = Util.getDate("MM-dd-yy");
			Util.assureDir("logs/" + dir);
			String f = "logs/" + dir + "/" + dir + " Hour " + hour + ".txt";
			setOut(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
