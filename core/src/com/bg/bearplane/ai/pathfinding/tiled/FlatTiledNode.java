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

/** A node for a {@link FlatTiledGraph}.
 * 
 * @author davebaol */
public class FlatTiledNode extends TiledNode<FlatTiledNode> {

	int w, h;
	
	public FlatTiledNode (int x, int y, int w, int h) {
		super(x, y, new Array<DefaultConnection<FlatTiledNode>>(4));
		this.w = w;
		this.h = h;
	}

	@Override
	public int getIndex () {
		return x * w + y;
	}

}
