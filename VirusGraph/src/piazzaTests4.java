

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class piazzaTests4 {
	CommunicationsMonitor cm;

	@Before
	public void setCm() {
		cm = new CommunicationsMonitor();
	}

	/**
	 * This is example 1 form the project description
	 */
	@Test
	public void example1() {
		CommunicationsMonitor cm = new CommunicationsMonitor();
		cm.addCommunication(1, 2, 4);
		cm.addCommunication(2, 4, 8);
		cm.addCommunication(3, 4, 8);
		cm.addCommunication(1, 4, 12);
		cm.createGraph();

		assertEquals(3, getLast(cm.queryInfection(1, 3, 2, 8)).getID());

	}

	/**
	 * This is example 1 form the project description
	 */
	@Test
	public void example1CheekGraph() {
		CommunicationsMonitor cm = new CommunicationsMonitor();
		cm.addCommunication(1, 2, 4);
		cm.addCommunication(2, 4, 8);
		cm.addCommunication(3, 4, 8);
		cm.addCommunication(1, 4, 12);
		cm.createGraph();

		HashMap<Integer, List<ComputerNode>> graph = cm.getComputerMapping();

//@formatter:off
		ComputerNode one = new ComputerNode(1, 4);
		ComputerNode two = new ComputerNode(2, 4);
		ComputerNode three = new ComputerNode(2, 8);
		ComputerNode four = new ComputerNode(4, 8);
		ComputerNode five = new ComputerNode(3, 8);
		ComputerNode six = new ComputerNode(1, 12);
		ComputerNode seven = new ComputerNode(4, 12);
//@formatter:on
		one.addEdge(two);
		one.addEdge(six);

		two.addEdge(one);
		two.addEdge(three);

		three.addEdge(four);

		four.addEdge(three);
		four.addEdge(five);
		four.addEdge(seven);

		five.addEdge(four);

		six.addEdge(seven);
		seven.addEdge(six);

		List<ComputerNode> c1 = graph.get(1);
		List<ComputerNode> c2 = graph.get(2);
		List<ComputerNode> c3 = graph.get(3);
		List<ComputerNode> c4 = graph.get(4);

		assertEquals(2, c1.size());
		assertEquals(2, c2.size());
		assertEquals(1, c3.size());
		assertEquals(2, c4.size());

		assertTrue(c1.get(0).equals(one));
		assertTrue(c1.get(1).equals(six));
		assertTrue(c2.get(0).equals(two));
		assertTrue(c2.get(1).equals(three));
		assertTrue(c3.get(0).equals(five));
		assertTrue(c4.get(0).equals(four));
		assertTrue(c4.get(1).equals(seven));

	}

	@Test
	public void example2() {
		cm.addCommunication(2, 3, 8);
		cm.addCommunication(1, 4, 12);
		cm.addCommunication(1, 2, 14);
		assertEquals(null, cm.queryInfection(1, 3, 2, 14));

	}

	@Test
	public void temp() {
		cm.addCommunication(2, 3, 8);
		cm.addCommunication(1, 4, 12);
		cm.addCommunication(1, 2, 14);
		cm.createGraph();

		List<ComputerNode> temp = cm.queryInfection(1, 3, 2, 8);
	}

	@Test
	public void InfectAfterLastCommunication() {
		cm.addCommunication(1, 2, 4);
		cm.createGraph();
		assertEquals(null, cm.queryInfection(1, 2, 5, 5));
	}

	private ComputerNode getLast(List<ComputerNode> computerNodes) {
		return computerNodes.get(computerNodes.size() - 1);
	}
}