package proj2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
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
		monitor.createGraph();
	}

	/*@Test
	public void checkOrder() {
		initFull();
		one = monitor.getLedger().iterator().next();
		assertEquals(one.getTimestamp(), 104);
	}

	@Test
	public void blockConstruct() {
		initEmpty();
		monitor.addCommunication(1, 2, 156);
		monitor.createGraph();
		monitor.addCommunication(1, 2, 104);
		one = monitor.getLedger().iterator().next();
		assertEquals(156, one.getTimestamp());
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
		//System.out.println(a.peek());
		//System.out.println(monitor.Ledger.get(0));
	}
	
	@Test
	public void assertConnEdge() {
		initFull();
		List<ComputerNode> nodeOne = monitor.getComputerMapping().get(new Integer(1));
		ComputerNode a = ((LinkedList<ComputerNode>) nodeOne).peek();
		assertEquals(104, a.getTimestamp());
		ComputerNode b = a.getOutNeighbors().get(0);
		assertEquals(2, b.getID());
	}*/
	
	/**
	 * Infection tests
	 */
	@Before
	public void makeCustom1() {
		initEmpty();
		monitor.addCommunication(1, 3, 12);
		monitor.addCommunication(2, 1, 10);
		monitor.addCommunication(1, 2, 1);
		monitor.addCommunication(1, 2, 5);
		monitor.addCommunication(2, 1, 3);
		monitor.createGraph();
	}
	
	@Test
	public void testFirstE(){
		makeCustom1();
		List<ComputerNode> VirusPath;
		VirusPath = monitor.queryInfection(1, 3, 0, 12); //time before
		assertEquals(1, VirusPath.get(0).getTimestamp());
		VirusPath = monitor.queryInfection(1, 3, 1, 13); //At
		assertEquals(1, VirusPath.get(0).getTimestamp());
		VirusPath = monitor.queryInfection(1, 3, 2, 13); //During
		assertEquals(3, VirusPath.get(0).getTimestamp());
		
		
		VirusPath = monitor.queryInfection(4, 3, 1, 12); //Inexistent node 1
		assertNull(VirusPath);
		VirusPath = monitor.queryInfection(3, 4, 1, 12); //Inexistent node 2
		assertNull(VirusPath);
		
		VirusPath = monitor.queryInfection(3, 1, 2, 100); //Long
		assertEquals(12, VirusPath.get(0).getTimestamp());
		
		VirusPath = monitor.queryInfection(1, 3, 9, 100); //Various Iters
		assertEquals(10, VirusPath.get(0).getTimestamp());
	}
	
}
