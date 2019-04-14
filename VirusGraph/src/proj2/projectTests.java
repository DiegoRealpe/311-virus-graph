package proj2;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class projectTests {
	
	CommunicationsMonitor monitor;
	Connection one;
	
	@Before
	public void init() {
		monitor = new CommunicationsMonitor();
		one = new Connection(1, 2, 101);
	}
	
	@Test
	public void checkOrder() {
		init();
		monitor.addCommunication(1, 2, 156);
		monitor.addCommunication(1, 2, 104);
		monitor.addCommunication(1, 2, 203);
		monitor.addCommunication(1, 2, 336);
		monitor.createGraph();
		one = monitor.getLedger().iterator().next();
		assertEquals(one.timestamp, 104);
	}
	
	@Test
	public void blockConstruct() {
		init();
		monitor.addCommunication(1, 2, 156);
		monitor.createGraph();
		monitor.addCommunication(1, 2, 104);
		one = monitor.getLedger().iterator().next();
		assertEquals(156, one.timestamp);
	}
	
	@Test
	public void blockNegative() {
		init();
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

}
