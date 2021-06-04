package it.unibs.ingesw;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;


public final class WriteN {
	
	
	public static String SAVE_ERROR = "errore nel salvataggio";
	
	
	public static void save(Network net) {
		Gson gson = new Gson();
		File data = new File("prova.txt");
		boolean exist = data.exists();
		FileWriter f;
		String variabileNik;
		try {
			f = new FileWriter("prova.txt", exist);
			variabileNik = gson.toJson(net, net.getClass())+"\n";
			//f.write(variabileNik);
			System.out.println(variabileNik);
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(SAVE_ERROR);
		}
		
	}
	
}
	
