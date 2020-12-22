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
 * A random generated graph representing a flat tiled map.
 * 
 * @author davebaol
 */
public class FlatTiledGraph implements TiledGraph<FlatTiledNode> {

	Array<FlatTiledNode> nodes;

	Pathfindable map;
	int w,h;

	public FlatTiledGraph(Pathfindable map) {
		this.map = map;
		w = map.getGraphWidth();
		h = map.getGraphHeight();
		this.nodes = new Array<FlatTiledNode>(w * h);
	}

	public void init() {
		nodes.clear();
		int x = 0;
		int y = 0;
		for (x = 0; x < w; x++) {
			for (y = 0; y < h; y++) {
				nodes.add(map.getTiles()[x][y]);
			}
		}
		for (x = 0; x < w; x++) {
			// int idx = x * Shared.MAP_WIDTH;
			for (y = 0; y < h; y++) {
				// FlatTiledNode n = nodes.get(idx + y);
				map.getTiles()[x][y].connections.clear();
				// if (y > 0) {
				addConnection(map.getTiles()[x][y], 0, 0, -1);
				// } else {
				// addConnection(map.getTiles()[x][y], 0, 0, -1);
				// }
				// if (y < Shared.MAP_WIDTH - 1) {
				addConnection(map.getTiles()[x][y], 1, 0, 1);
				// } else {
				// addConnection(map.getTiles()[x][y], 1, 1, 0);
				// }
				// if (x > 0) {
				addConnection(map.getTiles()[x][y], 2, -1, 0);
				// } else {
				// addConnection(map.getTiles()[x][y], 2, -1, 0);
				// }
				// if (x < Shared.MAP_WIDTH - 1) {
				addConnection(map.getTiles()[x][y], 3, 1, 0);
				// } else {
				// addConnection(map.getTiles()[x][y], 3, 1, 0);
				// }

				// map.getTiles()[x][y].vconn = new Array<DefaultConnection<FlatTiledNode>>();
			}
		}

		// update();
	}

	public void update() {
		int x = 0;
		int y = 0;
		// int d = 0;
		for (x = 0; x < w; x++) {
			for (y = 0; y < h; y++) {
				if (y > 0) {
					updateConnection(map.getTiles()[x][y], 0, 0, -1);
				}
				if (y < w - 1) {
					updateConnection(map.getTiles()[x][y], 1, 0, 1);
				}
				if (x > 0) {
					updateConnection(map.getTiles()[x][y], 2, -1, 0);
				}
				if (x < h - 1) {
					updateConnection(map.getTiles()[x][y], 3, 1, 0);
				}
				// map.getTiles()[x][y].vconn.clear();
				// for(DefaultConnection<FlatTiledNode> dc : map.getTiles()[x][y].connections) {
				// if(dc.valid) {
				// map.getTiles()[x][y].vconn.add(dc);
				// }
				// }
			}
		}
	}

	@Override
	public FlatTiledNode getNode(int x, int y) {
		return nodes.get(x * w + y);
	}

	@Override
	public FlatTiledNode getNode(int index) {
		return nodes.get(index);
	}

	@Override
	public int getIndex(FlatTiledNode node) {
		return node.getIndex();
	}

	@Override
	public int getNodeCount() {
		return nodes.size;
	}

	static FlatTiledConnection ftc;
	static FlatTiledNode t;

	@Override
	public Array<DefaultConnection<FlatTiledNode>> getConnections(FlatTiledNode fromNode) {
		return fromNode.getConnections();
	}


	private void addConnection(FlatTiledNode n, int d, int xOffset, int yOffset) {
		int xo = 0;
		int yo = 0;
		xo = n.x + xOffset;
		yo = n.y + yOffset;
		if (map.inBounds(xo, yo)) {
			t = getNode(n.x + xOffset, n.y + yOffset);
			n.connections.add(new FlatTiledConnection(d, this, n, t, 1f));
		} else {
			ftc = new FlatTiledConnection(d, this, n, null, 1f);
			ftc.valid = false;
			n.connections.add(ftc);
		}
	}

	private void updateConnection(FlatTiledNode n, int d, int xOffset, int yOffset) {
		n.connections.get(d).valid = map.isValidConnection(n, d, xOffset, yOffset);
		n.connections.get(d).cost = map.getConnectionCost(n, d, xOffset, yOffset);
		/*
		if (map.isVacantWalls(map.getTiles()[n.x + xOffset][n.y + yOffset].x, map.getTiles()[n.x + xOffset][n.y + yOffset].y, d,
				n.x, n.y)
				&& map.isVacantTile(map.getTiles()[n.x + xOffset][n.y + yOffset].x,
						map.getTiles()[n.x + xOffset][n.y + yOffset].y)) {
			if (map.isVacantElse(map.getTiles()[n.x + xOffset][n.y + yOffset].x, map.getTiles()[n.x + xOffset][n.y + yOffset].y)) {
				
				n.connections.get(d).cost = 1f;
			} else {
				n.connections.get(d).valid = true;
				n.connections.get(d).cost = 200f;
			}
		} else {
			n.connections.get(d).cost = 9999f;
			n.connections.get(d).valid = false;
		}
		*/
	}

}
