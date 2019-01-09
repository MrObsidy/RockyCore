package mrobsidy.rockycore.gridnetworks.api;

import java.io.Serializable;
import java.util.ArrayList;

import mrobsidy.rockycore.misc.Debug;

public abstract class Grid{

	private int capacity;
	
	private ArrayList<IGridNode> nodes = new ArrayList<IGridNode>();
	
	private ArrayList<IGridUser> users = new ArrayList<IGridUser>();
	
	//private GridManager man;
	
	public int ID;
	
	/**
	 * 
	 * @param node
	 */
	public Grid(){
		//ID = man.register(this);
		capacity = 0;
			
		Debug.debug("Created a Grid");
		//this.man = man;
	}
	
	public ArrayList<IGridNode> getNodes(){
		return this.nodes;
	}
	
	public ArrayList<IGridUser> getUsers(){
		return this.users;
	}
	
	/**
	 * 
	 * Returns the amount of nodes.
	 * 
	 * @return
	 */
	public int getNodesSize(){
		return nodes.size();
	}
	
	public int getUsersSize(){
		return users.size();
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public void addSubCapacity(int amount){
		capacity += amount;
	}
	
	public void addUser(IGridUser user){
		Debug.debug("addUser method fired");
		this.users.add(user);
		user.setID(this.users.size() - 1);
	}
	
	public void addNode(IGridNode node){
		this.nodes.add(node);
		node.setID(this.nodes.size() - 1);
	}
}
