package com.bg.bearplane.gui;

import java.util.LinkedList;
import java.util.List;
import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.engine.Log;
import com.bg.bearplane.engine.Util;
import com.gdx420.jammyjam.core.Shared;

public class Dialog {

	Scene scene;

	public int width = 0;
	public int height = 0;
	
	public int x = Shared.GAME_WIDTH/2;
	public int y = Shared.GAME_HEIGHT/2;
	
	public boolean done = false;
	public boolean active = false;

	public List<Button> choices;
	public Label msg;
	public Frame frame;

	public String id;
	
	public int xO = 0;
	public int yO = 0;
	
	public String text;
	
	public String[] choicetext;
	public String[] choiceid;
	
	public Dialog(Scene scene, String id, int width, String text, String[] choicetext,
			String[] choiceid) {
		this.scene = scene;
		this.id = id;
		this.width = width;
		this.choiceid = choiceid;
		this.choicetext = choicetext;
		this.text = text;	
	}

	public Dialog(Scene scene, String id, int width, String text) {
		this(scene, id, width, text, new String[] { "Ok" }, new String[] { "" });
	}

	public void setchoices(String[] choicetext, String[] choiceid) {
		this.choicetext = choicetext;
		this.choiceid = choiceid;
	}

	public void choose(Button b) {
		active = false;
		done = true;	
	}
	
	public void start(long tick) {	
		//TODO nextline
		//scene.activeDialog = this;
		active = true;

		int textHeight = (Util.wrapText(2, width - 40 - xO, text).size() * 30) + 32 + yO;
		if (textHeight < 176) {
			textHeight = 176;
		}
		height = textHeight + choicetext.length * 60;
		frame = new Frame(scene,"f", x, y, width, height - 2, true, true);
		frame.useAbsDraw = true;
		msg = new Label(scene, "l", x - (width / 2) + 8 + xO, y - (height / 2) + 8 + 8+yO, 2, text, Color.WHITE, false);
		msg.wrapw = width - 32 - xO;
		msg.wrap = true;
		msg.useAbsDraw = true;
		choices = new LinkedList<Button>();
		Button b;
		for (int i = 0; i < choicetext.length; i++) {
			b = new Button(scene, "dialog" + i, x, y - height / 2 + textHeight + 24 + 60*i, width - 16, 48, choicetext[i]);
			choices.add(b);
			b.useAbsDraw = true;
			
		}
	}

	public void update(long tick) {	
		
		if(active) {
			for (Button b : choices) {
				b.update();
			}
		}
	}


	public void render() {
		if(active) {
			frame.render();
			msg.render();
			for (Button b : choices) {
				b.render();
			}
		}
	}

}
