package net.spikesync.pingerdaemonrabbitmqclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.spikesync.pingerdaemonrabbitmqclient.PingEntry.PINGHEAT;
import net.spikesync.pingerdaemonrabbitmqclient.PingEntry.PINGRESULT;

/* Class VmPinger 
 * 
 * VmPinger is instantiated with one SilverCloudeNode object as its origin from the pings it executes to every node in
 * SilverCloud. Every VmPinger object runs in one thread that is started by its owning class, in this case: PingDaemon.
 * 
 * */


public class VmPinger implements Runnable {

	private static int pingPort = 22;
	private static int pingTimeout = 2000;
	private static long threadSleep = 2000;
	private static final Logger logger = LoggerFactory.getLogger(VmPinger.class);
	
	private SilverCloudNode origNode;
	private SilverCloudNode destNode;
	private ArrayList<PingEntry> pingentries;

	public VmPinger(SilverCloudNode orNo, SilverCloudNode deNo) {
		this.origNode = orNo;
		this.destNode = deNo;
		this.pingentries = new ArrayList<PingEntry>();
	}
	
	public ArrayList<PingEntry> getPingEntries() {
		return this.pingentries;
	}
	
	/* Clearing the list of PingEntry's. Retrieving them and clearing them could be done in one method, but then 
	 * the current list of PingEntry's have to be copied first, which is a lot of overhead. However, in this 
	 * construction it's the user and caller's responsibility to keep the list consistent and tidy!! 
	 */
	
	public void clearPingEntries() {
		this.pingentries.clear();
	}
	
	public PingEntry pingVm() {
		PingEntry pingEntry = new PingEntry(new Date(), this.origNode, this.destNode, PINGRESULT.PINGUNKOWN, PINGHEAT.UNKNOWN);
		try {
			Socket soc = new Socket(); 
            soc.connect(new InetSocketAddress(this.destNode.getIpAddress(), VmPinger.pingPort), VmPinger.pingTimeout);
            pingEntry.setLastPingResult(PINGRESULT.PINGSUCCESS);
            logger.debug("SUCCESSFUL Ping!! Result: " + pingEntry.toString());
            soc.close();
		} catch (IOException e) {
            pingEntry.setLastPingResult(PINGRESULT.PINGFAILURE);
            logger.debug("--UN--SUCCESSFUL Ping!! Result: " + pingEntry.toString());
		}
		return pingEntry;
	}
	
	public PingEntry rawICMPPing() {
		PingEntry pingEntry = new PingEntry(new Date(), this.origNode, this.destNode, PINGRESULT.PINGUNKOWN, PINGHEAT.UNKNOWN);
		
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(2000); // 2-second timeout

            InetAddress host = InetAddress.getByName(this.destNode.getIpAddress());
            byte[] sendData = createICMPPacket();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, host, 0);
            socket.send(sendPacket);

            byte[] receiveData = new byte[48];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            pingEntry.setLastPingResult(PINGRESULT.PINGSUCCESS);
            logger.debug("SUCCESSFUL raw ICMP Ping!! Result: " + pingEntry.toString());
  
        } catch (SocketTimeoutException e) {
            pingEntry.setLastPingResult(PINGRESULT.PINGFAILURE);
            logger.debug("--UN--SUCCESSFUL raw ICMP Ping -- Socket Time out!! Result: " + pingEntry.toString());
            logger.debug(e.toString());
        } catch (IOException e) {
            pingEntry.setLastPingResult(PINGRESULT.PINGFAILURE);
            logger.debug("--UN--SUCCESSFUL raw ICMP Ping!! Result: " + pingEntry.toString());
            logger.debug(e.toString());
        }

		
		return pingEntry;
	}
	
	public PingEntry rawICMPJNI() {
		PingEntry pingEntry = new PingEntry(new Date(), this.origNode, this.destNode, PINGRESULT.PINGUNKOWN, PINGHEAT.UNKNOWN);
		
		return pingEntry;

	}
	
	
    private static byte[] createICMPPacket() {
        byte[] packet = new byte[48];
        packet[0] = 8;  // ICMP Echo Request
        packet[1] = 0;  // Code (0)
        return packet;
    }

	
	@Override
	public void run() {
		while (true) {
			/* 
			 * Run method pingVm() to ping the destination and insert the result in the list of PingEntry's: 
			 * this.pingentries. After adding them, clear the list of PingEntry's.
			 */
			this.pingentries.add(this.rawICMPPing());
//			this.pingentries.add(this.pingVm());
			try {
				Thread.sleep(VmPinger.threadSleep);
			} catch (InterruptedException e) {
				logger.error("Sleep of Thread VmPinger interrupted!!");
				e.printStackTrace();
			}
			
		}
	}
}
