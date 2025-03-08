package net.spikesync.pingerdeamonrabbitmqclient;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.junit.jupiter.api.Test;

class VmPingerTest {

	@Test
	public void socketTest() {
		try {
			Socket socket = new Socket();
			//socket.bind(new InetSocketAddress("192.168.50.225", 0)); // Bind to the macvlan interface IP
			socket.connect(new InetSocketAddress("192.168.50.107", 0)); // Connect to the container
			assertNotNull(socket);
			System.out.println("Connected to " + socket.getRemoteSocketAddress());
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
