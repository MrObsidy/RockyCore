/**
 * 
 *  RockyCore
 *  Copyright (C) 2018-2020 MrObsidy
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

public final class GridConnectionSegment {
	private final float voltage;
	private final float resistance;
	
	public GridConnectionSegment(float volt, float res){
		this.voltage = volt;
		this.resistance = res;
	}
	
	public float getVoltage(){
		return this.voltage;
	}
	
	public float getResistance(){
		return this.resistance;
	}
}
