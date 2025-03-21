package net.spikesync.pingerdeamonrabbitmqclient;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//import ch.qos.logback.core.joran.spi.JoranException;
//import net.spikesync.pingerdaemonrabbitmqclient.LogbackConfigurator;
import net.spikesync.pingerdaemonrabbitmqclient.SilverCloud;
import net.spikesync.pingerdaemonrabbitmqclient.SilverCloudNode;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
public class SimpleJunit5Test {

	private static final Logger logger = LoggerFactory.getLogger(SimpleJunit5Test.class);

	@Autowired
	private SilverCloud sc;

	@BeforeAll
	static void initAll() {
		System.out.println("---Inside initAll---");
	}

	@BeforeEach
	void init(TestInfo testInfo) {
		System.out.println("Start..." + testInfo.getDisplayName());
	}

	@Test
	public void messageTest() {

//		try {
//			LogbackConfigurator.configure("logback.xml");
//		} catch (JoranException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		logger.info(
				"Now in SimpleJunit5Test.messageTest!!!! LOGGER WORKS @INFO ------------------------------------------");
		System.out.println("Logger name: " + logger.getName());
		System.out.println("Logger enabled for debugging? " + logger.isDebugEnabled());
		System.out.println("Logger enabled for error? " + logger.isErrorEnabled());
		System.out.println("Now in SimpleJunit5Test.messageTest ---  System.out, not the logger!!!!");

		// Test fedsky
		ArrayList<SilverCloudNode> nodes = sc.getScNodes();
		SilverCloudNode targetNode = null;
		for (SilverCloudNode node : nodes) {
//			if (node.getNodeName().equals("THORFW"))
			 if (node.getNodeName().equals("WITOOG"))
				targetNode = node;
		}
//		assertEquals("192.168.50.107", targetNode.getIpAddress());
		assertEquals("192.168.50.233", targetNode.getIpAddress());

		/*
		 * ArrayList<SilverCloudNode> nodes = sc.getScNodes(); SilverCloudNode
		 * targetNode = null; for (SilverCloudNode node : nodes) { if
		 * (node.getNodeName().equals("THORFW")) targetNode = node; }
		 */
		
	}

	@AfterEach
	void tearDown(TestInfo testInfo) {
		System.out.println("Finished..." + testInfo.getDisplayName());
	}

	@AfterAll
	static void tearDownAll() {
		System.out.println("---Inside tearDownAll---");
	}
}