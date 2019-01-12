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


package mrobsidy.rockycore.gridnetworks.api;

import java.io.Serializable;
import java.util.ArrayList;

import mrobsidy.rockycore.misc.Debug;

public abstract class Grid{
	
	private ArrayList<IGridNode> nodes = new ArrayList<IGridNode>();
	
	private ArrayList<IGridUser> users = new ArrayList<IGridUser>();
	
	/**
	 * 
	 * Please note that TileEntitys which both implement  IGridUser and IGridGenetor
	 * should be registered as generators.
	 * 
	 */
	private ArrayList<IGridGenerator> generators = new ArrayList<IGridGenerator>();
	
	//private GridManager man;
	
	/**
	 * 
	 * @param node
	 */
	public Grid(){
		//ID = man.register(this);
			
		Debug.debug("Created a Grid");
		//this.man = man;
	}
	
	public ArrayList<IGridNode> getNodes(){
		return this.nodes;
	}
	
	public ArrayList<IGridUser> getUsers(){
		return this.users;
	}
	
	public ArrayList<IGridGenerator> getGenerators(){
		return this.generators;
	}
	
	public void addUser(IGridUser user){
		Debug.debug("addUser method fired");
		this.users.add(user);
	}
	
	public void addNode(IGridNode node){
		this.nodes.add(node);
	}
	
	public void addGenerator(IGridGenerator generator){
		this.generators.add(generator);
	}
	
	public void removeUser(IGridUser user){
		this.users.remove(user);
	}
	
	public void removeNode(IGridNode node){
		this.nodes.remove(node);
	}
	
	public void removeGenerator(IGridGenerator generator){
		this.generators.remove(generator);
	}
}
