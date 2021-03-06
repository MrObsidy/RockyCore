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

import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.util.misc.helpers.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class MiscUtil {
	
	/**
	 * 
	 * Seriously, don't use it. Will be removed and is only here for convenience's sake.
	 * 
	 * @param dim
	 * @return
	 */
	public static WorldServer getServerWorld(int dim) {
		return RegistryRegistry.getServerRegistry().getServer().getWorld(dim);
	}
	
	public static Class getClassForName(String name) throws ClassNotFoundException{
		System.out.println(name);
		return Class.forName(name);
	}
}
