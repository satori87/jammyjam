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

/**
 * A connection for a {@link FlatTiledGraph}.
 * 
 * @author davebaol
 */
public class FlatTiledConnection extends DefaultConnection<FlatTiledNode> {

	static final float NON_DIAGONAL_COST = (float) Math.sqrt(2);

	float cost = 1f;

	
	FlatTiledGraph worldMap;

	public FlatTiledConnection(int d, FlatTiledGraph worldMap, FlatTiledNode fromNode, FlatTiledNode toNode, float cost) {
		super(fromNode, toNode);
		this.worldMap = worldMap;
		this.cost = cost;
		this.d = d;
	}

	@Override
	public float getCost() {
		return cost;
	}
}
