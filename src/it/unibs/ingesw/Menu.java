package it.unibs.ingesw;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Menu {
	
	final String SELEZIONE = "Seleziona: ";
	final String ERRORE = "ERRORE INSERIMENTO: inserisci un numero sensato\n";
	
	final String MENUSTART[] = {
			"MENU:",
			"___________________________",
			"1:Crea Network",
			"0:Esci",
			"___________________________",
			
	};
	
	final String MENUNETWORK[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Crea transition",
			"2:Crea location",
			"3:Crea link",
			"4:Salva rete",
			"5:Prova lettura",
			"0:Esci",
			"___________________________"};
	
	final String ASKLINK = "A cosa vuoi collegarla? Inserisci il numero relativo";
	final String SALVATAGGIO = "Salvataggio eseguito";
	final String NOME_GIA_PRESENTE_RETE = "Esiste già una rete con questo nome";
	final String NOME_GIA_PRESENTE_LOCATION = "Esiste già una location con questo nome";
	final String NOME_GIA_PRESENTE_TRANSITION = "Esiste già una transition con questo nome";
	final String LINK_GIA_PRESENTE = "Link già presente";
	
	
	private int idNet;
	private Network currentNetwork;
	private ArrayList<Network> networks;
	
	public Menu() {
		
		networks = new ArrayList<>();
		Network.network_id = Utility.getMax(ReadN.getNetIDsFromFile());
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
			select = Utility.readLimitedInt(0, MENUNETWORK.length-3);
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
					int loc;
					int trans;
					System.out.print(currentNetwork.getTransitionsList());
					trans = Utility.readLimitedInt(0, currentNetwork.getTransitions().size()-1);
					System.out.println(ASKLINK);
					System.out.print(currentNetwork.getLocationsList());
					loc = Utility.readLimitedInt(0, currentNetwork.getLocations().size()-1);
					if(!checkLinkExistence(currentNetwork.getTransition(trans), currentNetwork.getLocation(loc)))
						createLink(currentNetwork.getTransition(trans), currentNetwork.getLocation(loc));
					else
						System.out.println(LINK_GIA_PRESENTE);
					break;
				case 4:
					saveNetOnFile();
					break;
				case 5:
				ReadN.getNetIDsFromFile();
				default:
					break;
			}
		}while(select != 0);
		return network;
		}
	
	/**
	 * Medoto che crea un nuovo posto nella rete con l'inserimento di un "nome" che necessariamente dovrà essere diverso da quelli già presenti
	 */
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
	/**
	 * Medoto che crea una nuova transizione nella rete con l'inserimento di un "nome" che necessariamente dovrà essere diverso da quelli già presenti
	 */
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
	
	/**
	 * Metodo utilizzato per controllare che il link che si stà per creare non esisti già
	 * @param t transition
	 * @param l location
	 * @return boolean
	 */
	private boolean checkLinkExistence(Transition t, Location l) {
		
		for (int i = 0; i < currentNetwork.getNetLinks().size(); i++) {
			if(currentNetwork.getNetLinks().get(i).getTransition().equals(t) &&
					currentNetwork.getNetLinks().get(i).getLocation().equals(l))
				return true;
		}
		
		return false;
	}
	
	public void createLink(Transition t, Location l) {
		
		currentNetwork.addLink(new Link(t, l, currentNetwork.getNetId()));
	}
	
	/**
	 * Metodo utilizzato per riempire al momento della creazione di una nuova rete un posto ed una transizione, infine li collega da un link
	 */
	private void createBase() {
		createLocation();
		createTransition();
		currentNetwork.addLink(new Link(currentNetwork.getTransition(0), currentNetwork.getLocation(0), currentNetwork.getNetId()));
		
	}
	/**
	 * Metodo che richiama dalla classe statica WriteN il salvataggio su file delle reti create
	 */
	private void saveNetOnFile(){	
		WriteN.save(currentNetwork);
		System.out.println(SALVATAGGIO);
	}
}
