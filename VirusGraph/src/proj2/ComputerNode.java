package proj2;

import java.util.ArrayList;
import java.util.List;

/**
 * The ComputerNode class represents the nodes of the graph G, which are pairs
 * (Ci, t).
 *
 * @author diegort
 */
public class ComputerNode {

	private int ID;
	private int Timestamp;
	private ArrayList<ComputerNode> Connections;

	public ComputerNode(int givenID, int givenTime) {
		ID = givenID;
		Timestamp = givenTime;
		Connections = new ArrayList<ComputerNode>();
	}

	/**
	 * Returns the ID of the associated computer.
	 *
	 * @return Associated Computer's ID
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Returns the timestamp associated with this node.
	 *
	 * @return Timestamp for the node
	 */
	public int getTimestamp() {
		return Timestamp;
	}

	/**
	 * Returns a list of ComputerNode objects to which there is outgoing edge from
	 * this ComputerNode object.
	 *
	 * @return a list of ComputerNode objects that have an edge from this to the
	 *         nodes in the list.
	 */
	public List<ComputerNode> getOutNeighbors() {
		return (List<ComputerNode>) Connections;
	}
	
	protected void addEdge(ComputerNode n) {
		Connections.add(n);
	}
	
	public String toString() {
		return "Node#" + ID + "@" + Timestamp;
	}

}
