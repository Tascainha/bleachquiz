package com.bleachquiz.bleachdle.character;

import org.springframework.stereotype.Service;

@Service
public class CharacterService {

	private final CharacterRepository characterRepository;
	
	public CharacterService(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}
	
	public Character getCharacterByName(String name) {
		//TODO -> principalmente esse throw
		return characterRepository.findByName(name).orElseThrow();
	}
	
	public Character addCharacter(Character character) {
		//TODO
		return characterRepository.save(character);
	}
	
	
	//Se for necessário reutilizar esse método ou regex, criar uma classe RegexUtil no package Util.
	public String clearDescription(String rawTargetName, String description) {
		String[] names = rawTargetName.split("_");
		StringBuilder targetName = new StringBuilder();
		
		for(String name : names) {
			if(!name.isEmpty()) {
				targetName.append(java.lang.Character.toUpperCase(name.charAt(0))).append(name.substring(1)).append(" ");
				}
			}
		
		return targetName.toString().trim();
	}
}
