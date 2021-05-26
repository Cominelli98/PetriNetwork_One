package it.unibs.ingesw;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		ArrayList<Network> networks = new ArrayList<>();
		Menu menu = new Menu(networks.size());
		menu.startMenu();
	}

}
