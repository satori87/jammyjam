package com.bg.bearplane.engine;

public class Timer {

	Bearable game;
	
	public long stamp = 0;
	public int interval = 0;

	public boolean alt = false;

	public Timer() {

	}

	public Timer(Bearable game, int interval) {
		this.game = game;
		this.interval = interval;
	}

	public void update(long tick) {
		if (tick >= stamp) {
			alt = !alt;
			stamp = tick + interval;
			game.doTimer(interval);
		}
	}
}
