package main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONObject;

import main.java.config.ConfigReader;
import main.java.file.FileIO;
import main.java.json.APIJsonIO;

public class APIUsageCalculator {
	public static void main(String[] args) {
		//Important folder paths
		final String configPath = "D:\\Users\\user\\workspace\\APIUsageAnalyzer\\src\\config.properties";
		String apiUsageFolder = "\\APIUsage-AllJAR";
		String outputFolder = "\\APIAccount";
		
		ConfigReader configReader = new ConfigReader();
		ArrayList<String> sourceList;
		
		HashMap<String, Integer> classCountMap;
		HashMap<String, Integer> classCalledCountMap;
		ArrayList<String> classList;
		APIJsonIO classJsonIO;
		APIJsonIO clientsIO = new APIJsonIO();;
		FileIO projectDirIO;
		
		configReader.setConfig(configPath);
		sourceList = configReader.getValAsStringArrayList("sources");
		//System.out.println(configReader.getValAsString("clients"));
		clientsIO.jsonObj = new JSONObject(configReader.getValAsString("clients"));
		
		for(String source:sourceList) {
			classCountMap = new HashMap<String, Integer>();
			classCalledCountMap = new HashMap<String, Integer>();
			classList = new ArrayList<String>();
			classJsonIO = new APIJsonIO();
			projectDirIO = new FileIO();
			
			
			
			projectDirIO.setBaseDir(source);
			classJsonIO.jsonObj = new JSONObject(projectDirIO.readString(apiUsageFolder + "\\Class"));
			
			//Group APIs based on packages
			/*Iterator<String> classKeys = classJsonIO.jsonObj.keys();
			while(classKeys.hasNext()){
				String key = (String)classKeys.next();
				String packageString = classJsonIO.jsonObj.getJSONArray(key).getJSONObject(2).getString("package");
				int classCalledCount = classJsonIO.jsonObj.getJSONArray(key).getJSONObject(0).getInt("count");
				if(!classCountMap.containsKey(packageString)) {
					classCountMap.put(packageString, 1);
					classCalledCountMap.put(packageString, classCalledCount);
				} else {
					int classCount = classCountMap.get(packageString);
					classCountMap.replace(packageString, ++classCount);
					int totalClassCalledCount = classCalledCountMap.get(packageString);
					classCalledCountMap.replace(packageString, totalClassCalledCount+classCalledCount);
				}
			}
			System.out.println("Finished grouping APIs based on packages");*/
			
			//Get Group APIs based on clients
			Iterator<String> classKeys = classJsonIO.jsonObj.keys();
			while(classKeys.hasNext()){
				String classKey = (String)classKeys.next();
				String packageString = classJsonIO.jsonObj.getJSONArray(classKey).getJSONObject(2).getString("package");
				int classCalledCount = classJsonIO.jsonObj.getJSONArray(classKey).getJSONObject(0).getInt("count");
				Iterator<String> clientKeys = clientsIO.jsonObj.keys();
				while(clientKeys.hasNext()){
					String clientKey = (String)clientKeys.next();
					String[] clientPrefixs = ((String) clientsIO.jsonObj.get(clientKey)).split("-");
					//String temp
					for(String prefix : clientPrefixs) {
						if(packageString.startsWith(prefix)) {
							System.out.println(clientKey);
							System.out.println(prefix);
							System.out.println(classKey+"\n");
						}
						/*
						 * IF 
						 */
					}
				}
			}
			System.out.println("Finished grouping APIs based on clients");
		}
	}
}
