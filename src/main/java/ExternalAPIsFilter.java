package main.java;

import java.util.ArrayList;

import org.json.JSONObject;

import main.java.compare.Compare;
import main.java.config.ConfigReader;
import main.java.file.FileIO;
import main.java.json.APIJsonIO;

/*
 * 
 */
public class ExternalAPIsFilter {
	public static void main(String[] args) {
		final String configPath = "D:\\Users\\user\\workspace\\APIUsageAnalyzer\\src\\config.properties";
		
		ConfigReader configReader = new ConfigReader();
		ArrayList<String> sourceList;
		
		//Important folder paths
		String noJarFolder = "\\APIUsage-NoSpring";
		String jarFolder = "\\APIUsage-Spring";
		String outputFolder = "\\APIUsage-externalAPIs";
		
		configReader.setConfig(configPath);
		sourceList = configReader.getValAsStringArrayList("sources");
		
		for(String source:sourceList) {
			Compare comparer = new Compare();
			FileIO file = new FileIO();
			
			ArrayList<String> classNoJar;
			ArrayList<String> methodNoJar;
			ArrayList<String> fieldNoJar;
			
			ArrayList<String> classJar;
			ArrayList<String> methodJar;
			ArrayList<String> fieldJar;
			
			ArrayList<String> extClasses;
			ArrayList<String> extMethods;
			ArrayList<String> extFields;
			
			ArrayList<String> intClasses;
			ArrayList<String> intMethods;
			ArrayList<String> intFields;
			
			APIJsonIO classJson = new APIJsonIO();
			APIJsonIO methodJson = new APIJsonIO();
			APIJsonIO fieldJson = new APIJsonIO();
			
			file.setBaseDir(source);
			
			classNoJar = file.readList(noJarFolder + "\\ClassList");
			methodNoJar = file.readList(noJarFolder + "\\MethodList");
			fieldNoJar = file.readList(noJarFolder + "\\FieldList");
			
			classJar = file.readList(jarFolder + "\\ClassList");
			methodJar = file.readList(jarFolder + "\\MethodList");
			fieldJar = file.readList(jarFolder + "\\FieldList");
			
			extClasses = comparer.findDiffStrings(classNoJar, classJar);
			extMethods = comparer.findDiffStrings(methodNoJar, methodJar);
			extFields = comparer.findDiffStrings(fieldNoJar, fieldJar);
			
			intClasses = comparer.findSameStrings(classNoJar, classJar);
			intMethods = comparer.findSameStrings(methodNoJar, methodJar);
			intFields = comparer.findSameStrings(fieldNoJar, fieldJar);
			
			classJson.jsonObj = new JSONObject(file.readString(jarFolder + "\\Class"));
			methodJson.jsonObj = new JSONObject(file.readString(jarFolder + "\\Method"));
			fieldJson.jsonObj = new JSONObject(file.readString(jarFolder + "\\Field"));
			
			classJson.deleteKeys(intClasses);
			methodJson.deleteKeys(intMethods);
			fieldJson.deleteKeys(intFields);
			
			System.out.println("Finished comparing");
			
			file.clearFolder(outputFolder);
			
			file.writeString(outputFolder + "\\Class", classJson.jsonObj.toString());
			file.writeString(outputFolder + "\\Method", methodJson.jsonObj.toString());
			file.writeString(outputFolder + "\\Field", fieldJson.jsonObj.toString());
			
			for(String classResult : extClasses){
				file.writeString(outputFolder + "\\ClassList", classResult);
			}
			for(String methodResult : extMethods){
				file.writeString(outputFolder + "\\MethodList", methodResult);
			}
			for(String fieldResult : extFields){
				file.writeString(outputFolder + "\\FieldList", fieldResult);
			}
			
			System.out.println("Finished writing");
		}
	}
}
