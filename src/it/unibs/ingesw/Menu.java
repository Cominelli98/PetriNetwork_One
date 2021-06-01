package it.unibs.ingesw;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {
	
	String SELEZIONE = "Seleziona: ";
	String ERRORE = "ERRORE INSERIMENTO: inserisci un numero sensato\n";
	
	String MENUSTART[] = {
			"MENU:",
			"___________________________",
			"1:crea Network",
			"0:esci",
			"___________________________",
			
	};
	
	String MENUNETWORK[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:crea transition",
			"2:crea location",
			"3:crea link",
			"0:esci",
			"___________________________"};
	
	String ASKLINK = "A cosa vuoi collegarla? Inserisci il numero relativo";
	
	private int idNet;
	private Network currentNetwork;
	private ArrayList<Network> networks;
	Scanner scanner;
	
	public Menu() {
		
		networks = new ArrayList<>();
		Network.network_id = networks.size(); //TODO get biggest id	
		 scanner = new Scanner(System.in);
	}
	
	public void startMenu() {
		var select = -1;
		do {
			for (String s : MENUSTART) {
				System.out.println(s);
			}
			
			select = Utility.readLimitedInt(0, 1, scanner);
			switch (select) {
				case 1:
					createNetwork();
					break;
				case 0:
					scanner.close();
					break;

				default:
					break;
					//throw new IllegalArgumentException("Unexpected value: " + select);
			}
		}while(select != 0);
		
	}
	
	public Network createNetwork() {
		System.out.println("Inserisci il nome della nuova rete: ");
		String name = Utility.readString(scanner);
		Network network = new Network(name);
		networks.add(network);
		currentNetwork = network;
		var select = -1;
		
		createBase();
		
		do {
			for (String s : MENUNETWORK) {
				System.out.println(s);
			}
			select = scanner.nextInt(); //TODO: CONTROL
			var num = -1;
			switch (select) {
				
			case 0:
				break;
				case 1:
					createTransition();
					System.out.println(ASKLINK);
					System.out.print(currentNetwork.getLocationsList());
					num = Utility.readLimitedInt(0, currentNetwork.getLocations().size()-1, scanner);
					createLink(currentNetwork.getLastTransition(), currentNetwork.getLocation(num));
					num = -1;
					break;
				case 2:
					createLocation();
					System.out.println(ASKLINK);
					System.out.print(currentNetwork.getTransitionsList());
					num = Utility.readLimitedInt(0, currentNetwork.getTransitions().size()-1, scanner);
					createLink(currentNetwork.getTransition(num), currentNetwork.getLastLocation());
					num = -1;
					break;
				case 3:
					//createlink();
					break;
				default:
					//throw new IllegalArgumentException("Unexpected value: " + select);
					break;
			}
		}while(select != 0);
		return network;
		}
	
	private void createLocation() {
		System.out.println("Inserisci il nome della nuova location: ");
		String name = Utility.readString(scanner);
		currentNetwork.addLocation(name);
	}
	
	private void createTransition() {
		System.out.println("Inserisci il nome della nuova transition: ");
		String name = Utility.readString(scanner);
		currentNetwork.addTransition(name);
	}
	
	public void createLink(Transition t, Location l) {
		currentNetwork.addLink(new Link(t, l, currentNetwork.getNetId()));
	}
	private void createBase() {
		createLocation();
		createTransition();
		currentNetwork.addLink(new Link(currentNetwork.getTransition(0), currentNetwork.getLocation(0), currentNetwork.getNetId()));
		
	}

}
