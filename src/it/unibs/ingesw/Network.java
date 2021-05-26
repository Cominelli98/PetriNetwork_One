package it.unibs.ingesw;

import java.util.ArrayList;

public class Network {
	
	private ArrayList<Location> locations;
	private ArrayList<Transition> transitions;
	private ArrayList<Link> netLinks;
	private int netId; 
	private String name;
	
	public Network (int ID, String name) {
		locations = new ArrayList<Location>();
		transitions = new ArrayList<Transition>();
		netLinks = new ArrayList<Link>();
		this.name = name;
		this.netId = ID;
	}
	
	
	
	public void addLocation (String name) {
		locations.add(new Location(netId, locations.size(), name));
	}


	public void addTransition (String name) {
		transitions.add(new Transition(netId, transitions.size(), name));
	}
	
	public void addLink (Link l) {
		netLinks.add(l);
	}

	public void removeLocation (Location l) {
		locations.remove(l);
	}
	
	public void removeTransition (Transition t) {
		transitions.remove(t);
	}
	
	public void removeLink (Link l) {
		netLinks.remove(l);
	}
	
	
	
	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public ArrayList<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}

	public ArrayList<Link> getNetLinks() {
		return netLinks;
	}

	public void setNetLinks(ArrayList<Link> netLinks) {
		this.netLinks = netLinks;
	}

	public int getNetId() {
		return netId;
	}

	public void setNetId(int netId) {
		this.netId = netId;
	}
	
	
}
