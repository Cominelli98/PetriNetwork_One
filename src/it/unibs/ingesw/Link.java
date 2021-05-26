package it.unibs.ingesw;

public class Link {
	
	private Transition transition;
	private Location location;
	private int netId;
	
	public Link (Transition transition, Location location, int netId) {
		this.transition = transition;
		this.location = location;
		this.netId = netId;
	}
	
	
	
	
	

	public Transition getTransition() {
		return transition;
	}

	public void setTransition(Transition transition) {
		this.transition = transition;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getNetId() {
		return netId;
	}

	public void setNetId(int netId) {
		this.netId = netId;
	}

}
