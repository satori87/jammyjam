/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.bg.bearplane.ai.pathfinding.tiled;

import com.badlogic.gdx.utils.Array;

/**
 * A node for a {@link TiledGraph}.
 * 
 * @param <N> Type of node, either flat or hierarchical, extending the
 *            {@link TiledNode} class
 * 
 * @author davebaol
 */
public abstract class TiledNode<N extends TiledNode<N>> {

	public int x;
	public int y;

	protected Array<DefaultConnection<N>> connections;
	//protected Array<DefaultConnection<N>> vconn;
	

	// protected Array<DefaultConnection<N>> vcon;
	public TiledNode(int x, int y, Array<DefaultConnection<N>> connections) {
		this.x = x;
		this.y = y;
		this.connections = connections;
	}

	public abstract int getIndex();

	public Array<DefaultConnection<N>> getConnections() {
		//Array<DefaultConnection<N>> con = new Array<DefaultConnection<N>>();
		//for (DefaultConnection<N> c : this.connections) {
		//	if (c.valid) {
		//		con.add(c);
		//	}
		//}
		return this.connections;
	}

}
