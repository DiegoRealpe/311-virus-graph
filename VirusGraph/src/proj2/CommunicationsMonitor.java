package proj2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The CommunicationsMonitor class represents the graph G built to answer
 * infection queries.
 *
 * @author diegort
 */
public class CommunicationsMonitor {

	public ArrayList<Connection> Ledger = new ArrayList<Connection>();
	public HashMap<Integer, LinkedList<ComputerNode>> nodeMap = new HashMap<>();
	private boolean constructed = false;

	/**
	 * Constructor with no parameters
	 */
	public CommunicationsMonitor() {
	}

	/**
	 * Takes as input two integers c1, c2, and a timestamp. This triple represents
	 * the fact that the computers with IDs c1 and c2 have communicated at the given
	 * timestamp. This method should run in O(1) time. Any invocation of this method
	 * after createGraph() is called will be ignored.
	 *
	 * @param c1        First ComputerNode in the communication pair.
	 * @param c2        Second ComputerNode in the communication pair.
	 * @param timestamp Time the communication took place.
	 */
	public void addCommunication(int c1, int c2, int timestamp) {
		if (constructed || c1 < 0 || c2 < 0 || timestamp < 0)
			return;
		Ledger.add(new Connection(c1, c2, timestamp));
	}

	/**
	 * Constructs the data structure as specified in the Section 2. This method
	 * should run in O(n + m log m) time.
	 */
	public void createGraph() {
		if (constructed)
			return;
		constructed = true;
		Ledger.sort(null); // nlog(n)

		// for each connection fill the hashmap with references each new node connection
		for (Connection entry : Ledger) {
			// A and B Reference each other
			ComputerNode lastA = entry.A;
			ComputerNode lastB = entry.B;
			// Check if either exist
			if (nodeMap.containsKey(entry.getIDA())) {
				ComputerNode peekA = nodeMap.get(entry.getIDA()).peekLast();
				if (lastA.equals(peekA)) {
					lastA = peekA;
				}
			}
			if (nodeMap.containsKey(entry.getIDB())) {
				ComputerNode peekB = nodeMap.get(entry.getIDB()).peekLast();
				if (lastB.equals(peekB)) {
					lastB = peekB;
				}
			}
			if (!lastA.getOutNeighbors().contains(lastB)) {
				lastA.addEdge(lastB);
			}
			if (!lastB.getOutNeighbors().contains(lastA)) {
				lastB.addEdge(lastA);
			}

			// Node A References
			if (!nodeMap.containsKey(entry.getIDA())) { // If it is the first entry
				nodeMap.put(entry.getIDA(), new LinkedList<ComputerNode>()); // create LL
				nodeMap.get(entry.getIDA()).add(entry.A);
			} else {
				ComputerNode tail = nodeMap.get(entry.getIDA()).peekLast();
				if (!tail.equals(entry.A)) { // Only add new node to list and to last if this time doesnt exist already
					tail.addEdge(entry.A); // else get last entry and reference new node
					nodeMap.get(entry.getIDA()).addLast(entry.A);
				}
			}

			// Node B References (same)
			if (!nodeMap.containsKey(entry.getIDB())) {
				nodeMap.put(entry.getIDB(), new LinkedList<ComputerNode>());
				nodeMap.get(entry.getIDB()).add(entry.B);
			} else {
				ComputerNode tail = nodeMap.get(entry.getIDB()).peekLast();
				if (!tail.equals(entry.B)) {
					tail.addEdge(entry.B);
					nodeMap.get(entry.getIDB()).addLast(entry.B);
				}
			}
		}

	}

	/**
	 * Determines whether computer c2 could be infected by time y if computer c1 was
	 * infected at time x. If so, the method returns an ordered list of ComputerNode
	 * objects that represents the transmission sequence. This sequence is a path in
	 * graph G. The first ComputerNode object on the path will correspond to c1.
	 * Similarly, the last ComputerNode object on the path will correspond to c2. If
	 * c2 cannot be infected, return null.
	 * <p>
	 * Example 3. In Example 1, an infection path would be (C1, 4), (C2, 4), (C2,
	 * 8), (C4, 8), (C3, 8)
	 * <p>
	 * This method can assume that it will be called only after createGraph() and
	 * that x <= y. This method must run in O(m) time. This method can also be
	 * called multiple times with different inputs once the graph is constructed
	 * (i.e., once createGraph() has been invoked).
	 *
	 * @param c1 ComputerNode object to represent the Computer that is
	 *           hypothetically infected at time x.
	 * @param c2 ComputerNode object to represent the Computer to be tested for
	 *           possible infection if c1 was infected.
	 * @param x  Time c1 was hypothetically infected.
	 * @param y  Time c2 is being tested for being infected.
	 * @return List of the path in the graph (infection path) if one exists, null
	 *         otherwise.
	 */
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y) {
		// Checks for constructed G with at least one c1 node and one c2
		if (!constructed || !nodeMap.containsKey(c1) || !nodeMap.containsKey(c2) || y < x)
			return null;

		// Initialize all nodes as not visited
		for (LinkedList<ComputerNode> computerAccesses : nodeMap.values()) {
			for (ComputerNode n : computerAccesses) {
				n.visited = false;
			}
		}

		// Iterates to get starting node
		Iterator<ComputerNode> scanner = nodeMap.get(c1).iterator();
		ComputerNode pZero = scanner.next();
		// smallest (first conn) needs to be smaller than end time
		if (pZero.getTimestamp() > y)
			return null;
		while (scanner.hasNext()) {
			if (pZero.getTimestamp() >= x) {
				break; // stop at the first bigger or equal time
			}
			pZero = scanner.next();
		}
		// If the end of the list is smallest than the start then no infection can occur
		if (pZero.getTimestamp() < x)
			return null;

		// start
		return recPathFind(pZero, c2, y);
	}

	/**
	 * Mathod that recursively fills up a linked list of a path when it has found
	 * one Using linkedlist so I can push from beginning to end the nodes of the
	 * path since it will get filled up as the recursion returns
	 * 
	 * @param node
	 * @param targetID
	 * @param limit
	 * @return LL with node(s) if successful, null if not
	 */
	public LinkedList<ComputerNode> recPathFind(ComputerNode node, int targetID, int limit) {
		// always set the node as visited first
		node.visited = true;

		// Look if this node satisfies the query
		if (node.getID() == targetID && node.getTimestamp() <= limit) {
			// if it does, create a new list with target as first node
			LinkedList<ComputerNode> callbackPath = new LinkedList<>();
			callbackPath.addFirst(node);
			return callbackPath;
		}

		// Don't even test a node past the time we query, its children are past too
		if (node.getTimestamp() > limit) {
			return null;
		}

		// else for each neighbor node call recursion on it
		for (ComputerNode neigh : node.getOutNeighbors()) {
			if (neigh.visited == false) {
				LinkedList<ComputerNode> callbackPath = recPathFind(neigh, targetID, limit);
				// if the recursion results in a valid list it means it found the target before
				// or at timelimit
				if (callbackPath != null) {
					callbackPath.addFirst(node);
					return callbackPath;
				}
			}
		}
		// if none found the path or it doesn't have children return null
		return null;

	}

	/**
	 * Returns a HashMap that represents the mapping between an Integer and a list
	 * of ComputerNode objects. The Integer represents the ID of some computer Ci,
	 * while the list consists of pairs (Ci, t1),(Ci, t2),..., (Ci, tk), represented
	 * by ComputerNode objects, that specify that Ci has communicated with other
	 * computers at times t1, t2,...,tk. The list for each computer must be ordered
	 * by time; i.e., t1\<t2\<...\<tk.
	 *
	 * @return HashMap representing the mapping of an Integer and ComputerNode
	 *         objects.
	 */
	public HashMap<Integer, List<ComputerNode>> getComputerMapping() {
		if (!constructed)
			return null;
		return new HashMap<Integer, List<ComputerNode>>(nodeMap);
	}

	/**
	 * Returns the list of ComputerNode objects associated with computer c by
	 * performing a lookup in the mapping.
	 *
	 * @param c ID of computer
	 * @return ComputerNode objects associated with c.
	 */
	public List<ComputerNode> getComputerMapping(int c) {
		if (!constructed)
			return null;
		return (List<ComputerNode>) nodeMap.get(c);
	}

	/**
	 * Getter for the communication ledger
	 * 
	 * @return
	 */
	public ArrayList<Connection> getLedger() {
		return Ledger;
	}
}
