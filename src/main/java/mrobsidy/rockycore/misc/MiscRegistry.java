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


package mrobsidy.rockycore.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import mrobsidy.rockycore.registries.api.CustomData;
import net.minecraft.entity.player.EntityPlayer;

/**
 * 
 * A place where everything you will or won't need is registered.
 * 
 * @author mrobsidy
 *
 */
public class MiscRegistry {
	
	private ArrayList<CustomData> data = new ArrayList<CustomData>();
	
	public MiscRegistry(){
		//TODO: Make this useful and not a dummy constructor
	}
	
	public CustomData getData(String name){
		CustomData returnData = null;
		
		for (CustomData dat : data){
			if (dat.getName() == name) returnData = dat;
		}
		
		return returnData;
	}
	
	public void addData(Object obj, String name){
		for (Iterator<CustomData> it = data.iterator(); it.hasNext();){
			CustomData dat = it.next();
			if (dat.getName() == name){
				it.remove();
			}
		}
		data.add(new CustomData(obj, name));
		
	}
	
	public void addData(CustomData data){
		addData(data.getData(), data.getName());
	}
}
