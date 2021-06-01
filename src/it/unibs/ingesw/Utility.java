package it.unibs.ingesw;

import java.util.Scanner;

public final class Utility {
	
	
	
	public static int readLimitedInt(int min, int max, Scanner in) { //legge interi con estremi compresi
		//Scanner scanner = new Scanner(System.in);
		int i;
		do {
			try {
		
			i = in.nextInt();
		} catch (Exception e) {
			System.out.println("errore inserimento, per favore inserisci un intero");
			var throwable = in.nextLine();
			i= min-1;
		}
		if(i<min || i>max) {
			System.out.println("Errore inserimento, valore non compreso");
		}
		}while(i<min || i>max);
		
		return i;
		
	}
	
	public static String readString(Scanner in) {
		//Scanner scanner = new Scanner(System.in);
		String name = in.next();
		return name;
	
	}
}
