package mrobsidy.rockycore.gridnetworks;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Grid{

	private int capacity;
	
	private IGridNode mainNode;
	
	private ArrayList<IGridNode> nodes = new ArrayList<IGridNode>();
	
	private ArrayList<IGridUser> users = new ArrayList<IGridUser>();
	
	private GridManager man;
	
	public final int ID;
	
	/**
	 * 
	 * @param node
	 */
	public Grid(IGridNode node, GridManager man){
		ID = man.register(this);
		capacity = 0;
		mainNode = node;
		nodes.add(node);
		this.man = man;
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
	
	public int getSize(){
		return nodes.size();
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public void addSubCapacity(int amount){
		capacity += amount;
	}
	
	void addUser(IGridUser user){
		users.add(user);
		user.setGrid(this);
	}
	
	void addNode(IGridNode node){
		nodes.add(node);
		node.setGrid(this);
	}
}
