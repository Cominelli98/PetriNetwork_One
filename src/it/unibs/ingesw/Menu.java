package it.unibs.ingesw;

import java.util.Scanner;

public class Menu {
	
	private int idNet;
	private Network network;
	
	public Menu(int idNet) {
		this.idNet = idNet;
	}
	
	public void startMenu() {
		int select;
		do {
			System.out.println("MENU:");
			System.out.println("___________________________");
			System.out.println("1:crea Network");
			System.out.println("0:esci");
			System.out.println("___________________________");
			System.out.print("Seleziona: ");
			Scanner scanner = new Scanner(System.in);
			select = scanner.nextInt();
			
			switch (select) {
				case 1:
					createNetwork();
					break;
				case 0:
					break;

				default:
					throw new IllegalArgumentException("Unexpected value: " + select);
			}
		}while(select != 0);
	}
	
	public Network createNetwork() {
		System.out.println("Inserisci il nome della nuova netWork: ");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		Network network = new Network(idNet, name);
		int select;
		do {
			System.out.println("Scegli cosa fare:");
			System.out.println("1:crea transition");
			System.out.println("2:crea location");
			System.out.println("3:crea link");
			System.out.println("0:esci");
			select = scanner.nextInt();
			
			switch (select) {
				
			case 0:
				break;
				case 1:
					network = createTransition(network);
					break;
				case 2:
					network = createLocation(network);
					break;
				case 3:
					//createlink();
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + select);
			}
		}while(select != 0);
		return network;
		}
	
	private Network createLocation(Network network) {
		System.out.println("Inserisci il nome della nuova location: ");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		network.addLocation(name);
		return network;
	}
	
	private Network createTransition(Network network) {
		System.out.println("Inserisci il nome della nuova transition: ");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		network.addTransition(name);
		return network;
	}
	

}
