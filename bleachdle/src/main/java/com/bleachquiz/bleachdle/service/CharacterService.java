package com.bleachquiz.bleachdle.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bleachquiz.bleachdle.character.Character;
import com.bleachquiz.bleachdle.repository.CharacterRepository;

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
//	
//	public List<Character> addCharacters(List<Character> characters) {
//		
//	}
	
	public Character addCharacter(Character rawCharacter) {
		//TODO
		
		rawCharacter.setDescription(clearDescription(rawCharacter.getName(), rawCharacter.getDescription()));
		return characterRepository.save(rawCharacter);
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
		
		return description.replace(targetName, "");
	}
}
