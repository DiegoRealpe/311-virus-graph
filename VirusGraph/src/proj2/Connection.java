package proj2;

/**
 * This class contains a connection between two nodes and can be sorted
 * by its time stamp since it implements comparable. Instantiates both nodes
 * and acts as a wrapper for them until the monitor assembles the graph
 * @author diegort
 */
public class Connection implements Comparable<Connection> {

	ComputerNode A, B;
	int timestamp;

	public Connection(int nodeA, int nodeB, int time) {
		A = new ComputerNode(nodeA, time);
		B = new ComputerNode(nodeB, time);
		timestamp = time;
	}

	public ComputerNode getNodeA() {
		return A;
	}

	public ComputerNode getNodeB() {
		return B;
	}

	public int getTimestamp() {
		return timestamp;
	}

	@Override
	public int compareTo(Connection o) {
		return timestamp - o.timestamp;
	}
}
