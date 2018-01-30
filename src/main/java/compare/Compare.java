package main.java.compare;

import java.util.ArrayList;

public class Compare {
	public ArrayList<String> findDiffStrings(ArrayList<String> noJarList, ArrayList<String> jarList) {
		ArrayList<String> matches = new ArrayList<String>();
		for(String jar: jarList) {
			if(!noJarList.contains(jar) && jar.split("\\.").length>2) {
				matches.add(jar);
			}
		}
		return matches;
	}
	
	public ArrayList<String> findSameStrings(ArrayList<String> noJarList, ArrayList<String> jarList) {
		ArrayList<String> matches = new ArrayList<String>();
		for(String jar: jarList) {
			if(noJarList.contains(jar)) {
				matches.add(jar);
			}
		}
		return matches;
	}
}
