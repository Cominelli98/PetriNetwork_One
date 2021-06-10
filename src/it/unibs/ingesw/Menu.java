package it.unibs.ingesw;

import java.io.File;
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
			"2:Salva reti",
			"3:Visualizza reti",
			"0:Esci",
			"___________________________",
			
	};
	
	final String MENUNETWORK[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Crea location",
			"2:Crea transition",
			"3:Crea link",
			"0:Indietro",
			"___________________________"};
	
	final String MENUSALVA[] = {
			"Scegli cosa fare:",
			"___________________________",
			"1:Salva una rete",
			"2:Salva tutte le reti",
			"0:Indietro",
			"___________________________"};
	final String MENUVISUALIZZA[] = {
			"Cosa vuoi visualizzare?:",
			"___________________________",
			"1:Visualizza elenco locations",
			"2:Visualizza elenco transitions",
			"3:Visualizza elenco link ",
			"4:Visualizza rete complessiva",
			"0:Indietro",
			"___________________________"};
	
	final String ASKLINK = "A cosa vuoi collegarla? Inserisci il numero relativo";
	final String SALVATAGGIO = "Salvataggio eseguito";
	final String NOME_GIA_PRESENTE_RETE = "Esiste già una rete con questo nome";
	final String NOME_GIA_PRESENTE_LOCATION = "Esiste già una location con questo nome";
	final String NOME_GIA_PRESENTE_TRANSITION = "Esiste già una transition con questo nome";
	final String LINK_GIA_PRESENTE = "Link già presente";
	final String RICHIESTA_SALVATAGGIO = "Quale rete vuoi salvare?";
	
	private Network currentNetwork;
	private ArrayList<Network> networks;
	
	public Menu() {
		
		networks = new ArrayList<>();
		WriteN.fileCreation();
		Network.network_id = Utility.getMax(ReadN.getNetIDsFromFile());
		
	}
	
	public void startMenu() {
		var select = -1;
		do {
			for (String s : MENUSTART) {
				System.out.println(s);
			}
			
			select = Utility.readLimitedInt(0, 3);
			switch (select) {
				case 1:
					createNetwork();
					break;
				case 2:
					if(networks.size() != 0)
						saveOption();
					else
						System.out.println("Non ci sono reti da salvare");
					break;
				case 3:
					if(networks.size() != 0)
					netViewer();
					else {
						System.out.println("Non ci sono reti da visualizzare");
					}
					break;
				case 0:
					Utility.close();
					break;
			}
		}while(select != 0);
		
	}
	
	private void netViewer() {
		System.out.println("Quale rete vuoi visualizzare?");
		System.out.println(getNetworksList());
		int i = Utility.readLimitedInt(0, networks.size());
		int select = -1;
		do {
			for (String s : MENUVISUALIZZA)
				System.out.println(s);
			select = Utility.readLimitedInt(0, 4);
			
			switch(select) {
			case 1:
				System.out.println("ELENCO LOCATIONS:");
				System.out.println(networks.get(i).getLocationsList());
				break;
			case 2:
				System.out.println("ELENCO TRANSITIONS:");
				System.out.println(networks.get(i).getTransitionsList());
				break;
			case 3:
				System.out.println("ELENCO LINKS:");
				System.out.println(networks.get(i).getLinksList());
				break;
			case 4:
				System.out.println("ELENCO LOCATIONS:");
				System.out.println(networks.get(i).getLocationsList());
				System.out.println("ELENCO TRANSITIONS:");
				System.out.println(networks.get(i).getTransitionsList());
				System.out.println("ELENCO LINKS:");
				System.out.println(networks.get(i).getLinksList());
				break;
			case 0:
				break;
			}
		}while (select != 0);
		
	}

	public Network createNetwork() {
		String name;
		boolean exists;
		do {
			System.out.println("Inserisci il nome della nuova rete:");
			name = Utility.readString();
			exists = checkNetExistence(name);
			if (exists)
				System.out.println(NOME_GIA_PRESENTE_RETE);
		}while(exists);
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
					createLocation();
					System.out.println(ASKLINK);
					System.out.print(currentNetwork.getTransitionsList());
					num = Utility.readLimitedInt(0, currentNetwork.getTransitions().size()-1);
					createLink(currentNetwork.getTransition(num), currentNetwork.getLastLocation());
					num = -1;
					break;
				case 2:
					createTransition();
					System.out.println(ASKLINK);
					System.out.print(currentNetwork.getLocationsList());
					num = Utility.readLimitedInt(0, currentNetwork.getLocations().size()-1);
					createLink(currentNetwork.getLastTransition(), currentNetwork.getLocation(num));
					num = -1;
					break;
				case 3:
					int loc;
					int trans;
					System.out.println("ELENCO LOCATIONS");
					System.out.print(currentNetwork.getLocationsList());
					loc = Utility.readLimitedInt(0, currentNetwork.getLocations().size()-1);
					System.out.println(ASKLINK);
					System.out.println("ELENCO TRANSITIONS");
					System.out.print(currentNetwork.getTransitionsList());
					trans = Utility.readLimitedInt(0, currentNetwork.getTransitions().size()-1);
					if(!checkLinkExistence(currentNetwork.getTransition(trans), currentNetwork.getLocation(loc)))
						createLink(currentNetwork.getTransition(trans), currentNetwork.getLocation(loc));
					else
						System.out.println(LINK_GIA_PRESENTE);
					break;
			}
		}while(select != 0);
		return network;
		}
	
	private void saveOption() {
		int select = -1;
		for (String s : MENUSALVA)
			System.out.println(s);
		select = Utility.readLimitedInt(0, 2);
		switch (select) {
		case 0:
			break;
		case 1:
			System.out.println("Quale rete vuoi salvare?");
			System.out.println(getNetworksList());
			int i = Utility.readLimitedInt(0, networks.size());
			saveNetOnFile(networks.get(i));
			break;
		case 2:
			for (Network n : networks)
				saveNetOnFile(n);
			break;
		}
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
	
	private boolean checkNetExistence(String name) {
		if(networks.size()>0) {
			for (Network n : networks) {
				if(n.getName().equals(name)){
					return true;
				}
			}
		}
		
		return (ReadN.checkNetNameExistence(name));
		
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
	
	private StringBuffer getNetworksList(){
		StringBuffer s = new StringBuffer("");
		int i = 0;
		for (Network n : networks) {
			s.append(i++ + ")" + n.getName() + "\n");
		}
		return s;
	}
	/**
	 * Metodo che richiama dalla classe statica WriteN il salvataggio su file delle reti create
	 */
	private void saveNetOnFile(Network n){
		if (!ReadN.checkIdExistence(n.getNetId()))
			WriteN.save(n);
		System.out.println(SALVATAGGIO + " rete " + n.getName());
	}
}
