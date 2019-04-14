package proj2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class projectTests {

	CommunicationsMonitor monitor;
	Connection one;

	@Before
	public void initEmpty() {
		monitor = new CommunicationsMonitor();
		one = new Connection(1, 2, 101);
	}

	@Before
	public void initFull() {
		initEmpty();
		monitor.addCommunication(1, 2, 156);
		monitor.addCommunication(1, 2, 104);
		monitor.addCommunication(1, 2, 203);
		monitor.addCommunication(1, 2, 336);
	}

	@Test
	public void checkOrder() {
		initFull();
		monitor.createGraph();
		one = monitor.getLedger().iterator().next();
		assertEquals(one.timestamp, 104);
	}

	@Test
	public void blockConstruct() {
		initEmpty();
		monitor.addCommunication(1, 2, 156);
		monitor.createGraph();
		monitor.addCommunication(1, 2, 104);
		one = monitor.getLedger().iterator().next();
		assertEquals(156, one.timestamp);
	}

	@Test
	public void blockNegative() {
		initEmpty();
		monitor.addCommunication(-1, 2, 101);
		boolean gotAdded = monitor.getLedger().iterator().hasNext();
		assertEquals(false, gotAdded);
		monitor.addCommunication(1, -2, 101);
		gotAdded = monitor.getLedger().iterator().hasNext();
		assertEquals(false, gotAdded);
		monitor.addCommunication(1, 2, -101);
		gotAdded = monitor.getLedger().iterator().hasNext();
		assertEquals(false, gotAdded);
	}

	@Test
	public void mapExists() {
		initFull();
		assertNotNull(monitor.getComputerMapping());
	}

	@Test
	public void listExists() {
		initFull();
		HashMap<Integer, List<ComputerNode>> map = monitor.getComputerMapping();
		assertNotNull(map.get(new Integer(1)));
	}

	@Test
	public void nodeOneExists() {
		initFull();
		List<ComputerNode> nodeOneList = monitor.getComputerMapping().get(new Integer(1));
		LinkedList<ComputerNode> a = (LinkedList<ComputerNode>) nodeOneList;
		assertNotNull(a.peek());
	}
	
	@Test
	public void assertConnEdge() {
		initFull();
		List<ComputerNode> nodeOne = monitor.getComputerMapping().get(new Integer(1));
		ComputerNode a = ((LinkedList<ComputerNode>) nodeOne).peek();
		assertEquals(104, a.getTimestamp());
		ComputerNode b = a.getOutNeighbors().get(0);
		assertEquals(2, b.getID());
	}
}
