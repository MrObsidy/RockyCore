package mrobsidy.rockycore.gridnetworks;

import java.io.Serializable;
import java.util.ArrayList;

import mrobsidy.rockycore.misc.Debug;

public abstract class Grid{

	private int capacity;
	
	private IGridNode mainNode;
	
	private ArrayList<IGridNode> nodes = new ArrayList<IGridNode>();
	
	private ArrayList<IGridUser> users = new ArrayList<IGridUser>();
	
	//private GridManager man;
	
	public int ID;
	
	/**
	 * 
	 * @param node
	 */
	public Grid(IGridNode node /*, GridManager man*/){
		//ID = man.register(this);
		capacity = 0;
		mainNode = node;
		node.setID(0);
		nodes.add(node);
		node.setGrid(this);
		
		Debug.debug("Created a Grid, main node: " + mainNode.toString() + " with an ID of: " + mainNode.getID());
		//this.man = man;
	}
	
	public ArrayList<IGridNode> getNodes(){
		return this.nodes;
	}
	
	public ArrayList<IGridUser> getUsers(){
		return this.users;
	}
	
	public IGridNode getMainNode(){
		return this.mainNode;
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
	
	void addUser(IGridUser user){
		Debug.debug("addUser method fired");
		this.users.add(user);
		user.setGrid(this);
		user.setID(this.users.size() - 1);
	}
	
	void addNode(IGridNode node){
		this.nodes.add(node);
		node.setGrid(this);
		node.setID(this.nodes.size() - 1);
	}
}
