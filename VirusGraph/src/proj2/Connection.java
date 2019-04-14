package proj2;

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
