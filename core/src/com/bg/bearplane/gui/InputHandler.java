package com.bg.bearplane.gui;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Queue;

public class InputHandler implements InputProcessor {

	// Scene scene;
	public List<Integer> keyPress; // store in parallel so we can process
									// first-time clicks
	public boolean[] keyDown; // as well as whats still being held down
	public long[] keyDownAt;
	public boolean[] mouseDown = new boolean[255];
	public boolean[] wasMouseJustClicked = new boolean[255];
	public boolean[] wasMouseJustReleased = new boolean[255];
	public int[] mouseDownX = new int[255];
	public int[] mouseDownY = new int[255];
	public int[] mouseUpX = new int[255];
	public int[] mouseUpY = new int[255];
	public int mouseX = 0;
	public int mouseY = 0;
	public Queue<Integer> scrolls = new Queue<Integer>();

	
	public InputHandler() {
		keyPress = new ArrayList<Integer>();
		keyDown = new boolean[300];
		keyDownAt = new long[300];
		for (int i = 0; i < 300; i++) {
			keyDown[i] = false;
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		keyDown[keycode] = true;
		keyDownAt[keycode] = System.currentTimeMillis();
		keyPress.add(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		keyDown[keycode] = false;
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int X = Scene.getRelativeX(screenX);
		int Y = Scene.getRelativeY(screenY);
		mouseDown[button] = true;
		wasMouseJustClicked[button] = true;
		mouseDownX[button] = X;
		mouseDownY[button] = Y;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		int X = Scene.getRelativeX(screenX);
		int Y = Scene.getRelativeY(screenY);
		wasMouseJustReleased[button] = true;
		mouseUpX[button] = X;
		mouseUpY[button] = Y;
		mouseDown[button] = false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int X = Scene.getRelativeX(screenX);
		int Y = Scene.getRelativeY(screenY);
		mouseX = X;
		mouseY = Y;
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		int X = Scene.getRelativeX(screenX);
		int Y = Scene.getRelativeY(screenY);
		mouseX = X;
		mouseY = Y;
		return true;
	}

	@Override
	public boolean scrolled(float x, float y) {
		scrolls.addLast((int)y);
		//1.9.12 might have broken this! come back and check. it used to be an int now its a float
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

}
