package com.bg.bearplane.engine;

import java.util.LinkedList;

public class RawStatement {

	public String statement = "";
	public LinkedList<Object> objects = new LinkedList<Object>();

	public RawStatement(String s, LinkedList<Object> o) {
		statement = s;
		objects = o;
	}

}
