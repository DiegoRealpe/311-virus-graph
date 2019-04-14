package proj2;

/**
 * This class contains a connection between two nodes and can be sorted
 * by its time stamp since it implements comparable. Instantiates both nodes
 * and acts as a wrapper for them until the monitor assembles the graph
 * @author diegort
 */
public class Connection implements Comparable<Connection> {

	protected ComputerNode A, B;
	protected int timestamp;

	public Connection(int nodeA, int nodeB, int time) {
		A = new ComputerNode(nodeA, time);
		B = new ComputerNode(nodeB, time);
		timestamp = time;
	}
	
	public int getIDA() {
		return A.getID();
	}
	
	public int getIDB() {
		return B.getID();
	}

	public int getTimestamp() {
		return timestamp;
	}

	@Override
	public int compareTo(Connection o) {
		return timestamp - o.timestamp;
	}
	
	public String toString() {
		return A.toString() + " - " + B.toString();
	}
}
