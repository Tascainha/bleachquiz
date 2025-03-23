package com.bleachquiz.bleachdle.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ParseJson {

	ObjectNode characterJson;
	ObjectNode zanpakutoJson;
	
	public ParseJson(String path) {
		readJson(path);
	}
	
	public ObjectNode getCharacterJson(){
		return this.characterJson;
	}
	
	public ObjectNode getZanpakutoJson(){
		return this.zanpakutoJson;
	}
	
	private void readJson(String path) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			
			JsonNode rootNode = objectMapper.readTree(new File(path));
			JsonNode name = rootNode.get("name");
			String englishName = name.get("english").asText();
			String description = rootNode.get("description").asText();
			JsonNode stats = rootNode.get("stats");
			String race = stats.get("race").asText();
			String gender = stats.get("gender").asText();
			String height = stats.get("height").asText();
			JsonNode firstApperance = stats.get("First Appearance");
			String firsApperanceAnime = firstApperance.get("anime").asText();
			
			characterJson = objectMapper.createObjectNode();
			zanpakutoJson = objectMapper.createObjectNode();
			characterJson.put("name", englishName);
			
			ArrayNode descriptionArray = objectMapper.createArrayNode();
			TextFormatter tf = new TextFormatter();
			
			for(String desc : tf.clearDescription(englishName, description)) {
				descriptionArray.add(desc);
			}
			
			characterJson.set("description", descriptionArray);
			characterJson.put("race", race);
			characterJson.put("gender", gender);
			
			characterJson.put("height", tf.removeParentheses(height));
			characterJson.put("first_appearance", firsApperanceAnime);
			
			JsonNode zanpakutoNode = stats.get("Zanpakutō");
			
			if(zanpakutoNode != null && !zanpakutoNode.isNull()) {
				zanpakutoJson.set("zanpakuto", zanpakutoNode);
			}
			
			JsonNode media = rootNode.get("media");
			
			ArrayNode arrayMedia = objectMapper.createArrayNode();
			
			for(JsonNode item : media) {
				String source = item.get("source").asText();
				arrayMedia.add(source);
			}
			
			characterJson.set("media", arrayMedia);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
