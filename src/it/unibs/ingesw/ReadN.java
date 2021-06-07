package it.unibs.ingesw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public final class ReadN {
	
	
	public static ArrayList<String> readNets() throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
		String line;
		ArrayList<String> lines = new ArrayList<>();
		try {
			line = reader.readLine();
			while(line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	//restituisce gli id delle network salvate permanentemente
	public static ArrayList<Integer> getNetIDsFromFile() {
		ArrayList<Integer> IDs = new ArrayList<>();
		try {
			ArrayList<String> nets = readNets();
			Gson gson = new Gson();
			for(String s : nets) {
				Network net = gson.fromJson(s, Network.class);
				IDs.add(net.getNetId());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return IDs;
	}
	
	//restituisce la lista delle network salvate
	public static StringBuffer getNetNamesList() {
		StringBuffer names = new StringBuffer();
		try {
			ArrayList<String> nets = readNets();
			Gson gson = new Gson();
			for(String s : nets) {
				Network net = gson.fromJson(s, Network.class);
				names.append(net.getNetId()+")"+net.getName());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}
	
	//restituisce la network con l'id specificato
	public static Network getNetworkFromFile(int netID) {
		Network net = null;
		try {
			ArrayList<String> nets = readNets();
			Gson gson = new Gson();
			for(String s : nets) {
				net = gson.fromJson(s, Network.class);
				if(net.getNetId() == netID) {
					return net;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return net;
	}
}
