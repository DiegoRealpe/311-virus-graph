package proj2;

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
	public void oneConnection() {
		init();
	}

}
