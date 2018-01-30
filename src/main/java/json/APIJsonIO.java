package main.java.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONObject;

public class APIJsonIO {
	public JSONObject jsonObj = new JSONObject();
	
	public void deleteKeys(ArrayList<String> keys) {
		for(String key:keys) {
			if(jsonObj.has(key)) {
				jsonObj.remove(key);
			}
		}
	}
	
	public void addString(HashMap<String, String> hashmap, String key) {
		for(Entry<String, String> entry : hashmap.entrySet()) {
			jsonObj.append(entry.getKey(), new JSONObject().put(key, entry.getValue()));
		}
	}
	
	public void addInt(HashMap<String, Integer> hashmap, String key) {
		for(Entry<String, Integer> entry : hashmap.entrySet()) {
			jsonObj.append(entry.getKey(), new JSONObject().put(key, entry.getValue()));
		}
	}
}
