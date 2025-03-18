package com.bleachquiz.bleachdle.character;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/character")
public class CharacterController {

    @Autowired
    private CharacterRepository characterRepository;

    @GetMapping("/{name}")
    public ResponseEntity<Character> getCharacterByName(@PathVariable String name) {
        Optional<Character> character = characterRepository.findByName(name);

        if (character.isPresent()) {
            Character charWithRaces = character.get();
            return ResponseEntity.ok(charWithRaces);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<List<Character>> addCharacters(@RequestBody List<Character> characters) {
        List<Character> savedCharacters = characterRepository.saveAll(characters);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacters);
    }

}