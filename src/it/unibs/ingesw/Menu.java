package it.unibs.ingesw;

import java.util.ArrayList;
import java.util.Scanner;


public class Menu {
	
	String SELEZIONE = "Seleziona: ";
	String ERRORE = "ERRORE INSERIMENTO: inserisci un numero sensato\n";
	
	String MENUSTART[] = {
			"MENU:",
			"___________________________",
			"1:Crea Network",
			"0:Esci",
			"___________________________",
			
	};
	
	String MENUNETWORK[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Crea transition",
			"2:Crea location",
			"3:Crea link",
			"4:Salva rete",
			"0:Esci",
			"___________________________"};
	
	String ASKLINK = "A cosa vuoi collegarla? Inserisci il numero relativo";
	String SALVATAGGIO = "Salvataggio eseguito";
	String NOME_GIA_PRESENTE_RETE = "Esiste già una rete con questo nome";
	String NOME_GIA_PRESENTE_LOCATION = "Esiste già una location con questo nome";
	String NOME_GIA_PRESENTE_TRANSITION = "Esiste già una transition con questo nome";
	
	
	private int idNet;
	private Network currentNetwork;
	private ArrayList<Network> networks;
	
	public Menu() {
		
		networks = new ArrayList<>();
		Network.network_id = networks.size(); //TODO get biggest id	
	}
	
	public void startMenu() {
		var select = -1;
		do {
			for (String s : MENUSTART) {
				System.out.println(s);
			}
			
			select = Utility.readLimitedInt(0, 1);
			switch (select) {
				case 1:
					createNetwork();
					break;
				case 0:
					Utility.close();
					break;

				default:
					break;
			}
		}while(select != 0);
		
	}
	
	public Network createNetwork() {
		System.out.println("Inserisci il nome della nuova rete: ");
		String name;
		boolean isEqual;
		if(networks.size()>0) {
			do {
				isEqual = false;
				name = Utility.readString();
				for (Network n : networks) {
					if(n.getName().equals(name)) {
						isEqual = true;
						System.out.println(NOME_GIA_PRESENTE_RETE);
						break;
					}
				}
			}while(isEqual);
		}else {
			name = Utility.readString();
		}
		Network network = new Network(name);
		networks.add(network);
		currentNetwork = network;
		var select = -1;
		
		createBase();
		
		do {
			for (String s : MENUNETWORK) {
				System.out.println(s);
			}
			select = Utility.readLimitedInt(0, MENUNETWORK.length-3); //TODO: CONTROL
			var num = -1;
			switch (select) {
				
			case 0:
				break;
				case 1:
					createTransition();
					System.out.println(ASKLINK);
					System.out.print(currentNetwork.getLocationsList());
					num = Utility.readLimitedInt(0, currentNetwork.getLocations().size()-1);
					createLink(currentNetwork.getLastTransition(), currentNetwork.getLocation(num));
					num = -1;
					break;
				case 2:
					createLocation();
					System.out.println(ASKLINK);
					System.out.print(currentNetwork.getTransitionsList());
					num = Utility.readLimitedInt(0, currentNetwork.getTransitions().size()-1);
					createLink(currentNetwork.getTransition(num), currentNetwork.getLastLocation());
					num = -1;
					break;
				case 3:
					System.out.print(currentNetwork.getTransitionsList());
					int trans = Utility.readLimitedInt(0, currentNetwork.getTransitions().size()-1);
					System.out.println(ASKLINK);
					System.out.print(currentNetwork.getLocationsList());
					int loc = Utility.readLimitedInt(0, currentNetwork.getLocations().size()-1);
					if(checkIfLinkAlreadyExist(currentNetwork.getTransition(trans), currentNetwork.getLocation(loc))) {
						System.out.println("Link già esistente");
					}else {
						createLink(currentNetwork.getTransition(trans), currentNetwork.getLocation(loc));
					}
						
					
					
					break;
				case 4:
					saveNetOnFile();
					break;
				default:
					break;
			}
		}while(select != 0);
		return network;
		}
	
	private void createLocation() {
		
		System.out.println("Inserisci il nome della nuova location: ");
		boolean isEqual;
		String name;
		do {
			isEqual = false;
			name = Utility.readString();
			for (Location l : currentNetwork.getLocations()) {
				if(l.getNodeName().equals(name)) {
					isEqual = true;
					System.out.println(NOME_GIA_PRESENTE_LOCATION);
					break;
				}
			}
		}while(isEqual);
		currentNetwork.addLocation(name);
	}
	
	private void createTransition() {
		
		System.out.println("Inserisci il nome della nuova transition: ");
		boolean isEqual;
		String name;
		do {
			isEqual = false;
			name = Utility.readString();
			for (Transition l : currentNetwork.getTransitions()) {
				if(l.getNodeName().equals(name)) {
					isEqual = true;
					System.out.println(NOME_GIA_PRESENTE_TRANSITION);
				}
			}
		}while(isEqual);
		currentNetwork.addTransition(name);
	}
	
	public void createLink(Transition t, Location l) {
		
		currentNetwork.addLink(new Link(t, l, currentNetwork.getNetId()));
	}
	
	private boolean checkIfLinkAlreadyExist(Transition t, Location l) {
		
		for (int i = 0; i < currentNetwork.getNetLinks().size(); i++) {
			if (currentNetwork.getNetLinks().get(i).getTransition().equals(t) &&
					currentNetwork.getNetLinks().get(i).getLocation().equals(l)) {
				return true;
			}
		}
		return false;
	}
	
	private void createBase() {
		createLocation();
		createTransition();
		currentNetwork.addLink(new Link(currentNetwork.getTransition(0), currentNetwork.getLocation(0), currentNetwork.getNetId()));
		
	}
	
	private void saveNetOnFile(){	
		WriteN.save(currentNetwork);
		System.out.println(SALVATAGGIO);
	}
	//pane pane pane pane
}
