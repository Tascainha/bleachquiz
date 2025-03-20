package com.bleachquiz.bleachdle.util;

public class TextFormatter {
	
	String textFormatted;
	
	public TextFormatter(String text) {
		
	}
	
	public String getTextFormatted() {
		return this.textFormatted;
	};
	
	
	
	//Método para 
	public String clearDescription(String rawTargetName, String description) {
		String[] names = rawTargetName.split("_");
		StringBuilder targetName = new StringBuilder();
		
		for(String name : names) {
			if(!name.isEmpty()) {
				targetName.append(java.lang.Character.toUpperCase(name.charAt(0))).append(name.substring(1)).append(" ");
				}
			}
		
		return description.replace(targetName, "");
	}
	
}
