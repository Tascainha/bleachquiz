package com.bleachquiz.bleachdle.util;

import java.util.ArrayList;
import java.util.List;

public class TextFormatter {
	
	public TextFormatter() {
		
	}
	
	public String removeParentheses(String rawString) {
		String string = rawString.replaceAll("\\([^)]*\\)", "");
		return string;
	}
	
	public String[] splitName(String rawName){
		String[] names = rawName.split(" ");
		return names;
	}
	
	public String[] splitDescription(String rawDescription) {
		String[] descriptions = rawDescription.split("\\.");
		return descriptions;
	}
	
	//Método para 
	public List<String> clearDescription(String rawName, String rawDescription) {
		
		List<String> descriptionNew = new ArrayList<String>();
		
		String[] descriptions = splitDescription(rawDescription);
		String[] names = splitDescription(rawName);
		
		for(String description : descriptions) {
			for(String name : names) {
				description = description.replace(name, "");
			}
			description = description.replaceAll("\\([^)]*\\)", "");
			descriptionNew.add(description.trim());
		}
		
		return descriptionNew;
	}
	
}