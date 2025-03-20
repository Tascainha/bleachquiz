package com.bleachquiz.bleachdle.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//Criar Json do personagem
//Criar Json da zanpakuto
public class ParseJson {

	public static void main(String[] args) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			
			JsonNode rootNode = objectMapper.readTree(new File("data.json"));
			
			String id = rootNode.get("id").asText();
			String description = rootNode.get("description").asText();
			JsonNode stats = rootNode.get("stats");
			String race = stats.get("race").asText();
			String gender = stats.get("gender").asText();
			String height = stats.get("height").asText();
			JsonNode firstApperance = stats.get("First Appearance");
			String firsApperanceAnime = firstApperance.get("anime").asText();
			
			JsonNode hasZanpakuto = stats.path("Zanpakutō");
			
			if(!hasZanpakuto.isMissingNode() && !hasZanpakuto.isEmpty()) {
				JsonNode zanpakuto = stats.get("Zanpakutō");
				String shikai = zanpakuto.get("shikai").asText();
				String bankai = zanpakuto.get("bankai").asText();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
