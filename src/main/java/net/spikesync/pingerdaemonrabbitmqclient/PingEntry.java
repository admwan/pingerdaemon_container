package net.spikesync.pingerdaemonrabbitmqclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PingEntry implements Cloneable {

	private static final Logger logger = LoggerFactory.getLogger(PingEntry.class);
	
	private SilverCloudNode pingOrig;
	private SilverCloudNode pingDest;
	private Date lastPingDate;
	private PINGRESULT lastPingResult; // -1 means unkown, 0 means failed, 1 means succeeded
	private PINGHEAT pingHeat; // -1 means unknown. scale pingheated: 0 means long time ago, 10 means recently
								// heated (ping successful)

	public PingEntry(Date lastDate, SilverCloudNode orig, SilverCloudNode dest, PINGRESULT lastResult, PINGHEAT heat) {
		this.lastPingDate = lastDate;
		this.pingOrig = orig;
		this.pingDest = dest;
		this.lastPingResult = lastResult;
		this.pingHeat = heat;
	}

	public static enum PINGRESULT { // Use this enum instead of integer codes.
		PINGSUCCESS, PINGFAILURE, PINGUNKOWN // Ping status/result unknown. Used to be -1 (Integer).
	}

	public static enum PINGHEAT {

		UNKNOWN, // Pingheat is unknown. This is the LOWEST ordinal!! Important to determine the
					// warmer or colder temperature!
		GLACIAL, FRIGID, CRISPY, TEPID, SNUG, TROPIC, SCORCHING,
	}

	static public PINGHEAT getWarmerHeat(PINGHEAT temperature) {
		int index = temperature.ordinal();
		int nextIndex = index + 1;
		if (nextIndex > 7)
			return PINGHEAT.SCORCHING; // Hotter than SCORCHING is not possible.
		PINGHEAT[] pingheat = PINGHEAT.values();
		nextIndex %= pingheat.length;
		return pingheat[nextIndex];
	}

	static public PINGHEAT getColderHeat(PINGHEAT temperature) {
		int index = temperature.ordinal();
		int nextIndex = index - 1;
		if (nextIndex == 0)
			return PINGHEAT.GLACIAL; // Colder than GLACIAL is not possible.
		else if (nextIndex == -1)
			return PINGHEAT.UNKNOWN; // PINGHEAT was unknown, and on an unsuccessful ping it remains unknown.
		PINGHEAT[] pingheat = PINGHEAT.values();
		nextIndex %= pingheat.length;
		return pingheat[nextIndex];
	}

	public int getPingHeatOrdinals() {
		switch (pingHeat) {
		case UNKNOWN:
			return 0;
		case GLACIAL:
			return 1;
		case FRIGID:
			return 2;
		case CRISPY:
			return 3;
		case TEPID:
			return 4;
		case SNUG:
			return 5;
		case TROPIC:
			return 6;
		case SCORCHING:
			return 7;
		default:
			break;
		}
		return 0; // This value (the default) is the same as UNKNOWN.
	}

	public PINGHEAT getPingHeat() {
		return pingHeat;
	}

	public void setPingHeat(PINGHEAT pingHeat) {
		this.pingHeat = pingHeat;
	}

	public SilverCloudNode getPingOrig() {
		return pingOrig;
	}

	public void setPingOrig(SilverCloudNode pingOrig) {
		this.pingOrig = pingOrig;
	}

	public SilverCloudNode getPingDest() {
		return pingDest;
	}

	public void setPingDest(SilverCloudNode pingDest) {
		this.pingDest = pingDest;
	}

	public Date getLastPingDate() {
		return lastPingDate;
	}

	public void setLastPingDate(Date lastPing) {
		this.lastPingDate = lastPing;
	}

	public PINGRESULT getLastPingResult() {
		return lastPingResult;
	}

	public void setLastPingResult(PINGRESULT lastPingResult) {
		this.lastPingResult = lastPingResult;
	}

	@Override
	public String toString() {

		DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		return format.format(this.lastPingDate) + ";" + this.pingOrig + ";" + this.pingDest + ";" + this.lastPingResult
				+ ";" + this.pingHeat + "\n";
	}

	/*
	 * Equality for PingEntry's is important because these objects are converted into Strings and back when sent 
	 * and retrieved from the RabbitMQ. In the conversion process, a time difference is introduces somewhere. If it is 
	 * small (less than the number in variable timeUncertainty) then the pings are considered to be the same.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (this.getClass() != other.getClass())
			return false;
		long timeUncertainty = 1000; // Time tolerance in milliseconds. 
		PingEntry otherPingEntry = (PingEntry) other;
		Date otherLaPiDa = otherPingEntry.getLastPingDate();
		SilverCloudNode otherSiClNoOr = otherPingEntry.getPingOrig();
		SilverCloudNode otherSiClNoDe = otherPingEntry.getPingDest();
		PINGRESULT otherLaRe = otherPingEntry.getLastPingResult();
		PINGHEAT otherPiHe = otherPingEntry.getPingHeat();
		
		logger.debug("Time of ohter last ping date: " + otherLaPiDa.toString() + ", this last ping date: " + this.lastPingDate.toString());

		if ((this.pingOrig.equals(otherSiClNoOr)) 
				&& (this.pingDest.equals(otherSiClNoDe))
				&& (this.pingOrig.equals(otherSiClNoOr))
				&& ( Math.abs(this.lastPingDate.getTime() - otherLaPiDa.getTime()) < timeUncertainty) 
				&& (this.lastPingResult.equals(otherLaRe))
				&& (this.pingHeat.equals(otherPiHe)))
			return true;

		else
			return false;
	}

}

// For DateFormat see:  https://stackoverflow.com/questions/5937017/how-to-convert-a-date-in-this-format-tue-jul-13-000000-cest-2010-to-a-java-d
