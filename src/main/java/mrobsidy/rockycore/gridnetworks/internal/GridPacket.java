/**
 * 
 *  RockyCore
 *  Copyright (C) 2018-2019 MrObsidy
 *  
 *  
 *  This file is part of RockyCore.
 *
 *  RockyCore is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  RockyCore is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with RockyCore.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package mrobsidy.rockycore.gridnetworks.internal;

import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.api.IGridGenerator;

public final class GridPacket {
	
	private final IGridGenerator generator;
	private final ArrayList<Integer> visitedNodes;
	private final ArrayList<GridConnectionSegment> segments;
	private final float voltage;
	
	
	public GridPacket(ArrayList<Integer> visitedNodes, ArrayList<GridConnectionSegment> segments, float voltage, IGridGenerator offerer){
		this.visitedNodes = visitedNodes;
		this.segments = segments;
		this.voltage = voltage;
		this.generator = offerer;
	}
	
	public ArrayList<GridConnectionSegment> getSegments(){
		return this.segments;
	}
	
	public ArrayList<Integer> getVisitedNodes(){
		return this.visitedNodes;
	}

	public IGridGenerator getGenerator() {
		return this.generator;
	}
}
