package com.bleachquiz.bleachdle.character;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Character> getCharacter(String name) {
        Optional<Character> character = characterRepository.findByName(name);

        if (character.isPresent()) {
            return ResponseEntity.ok(character.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> chars = characterRepository.findAll();
        return ResponseEntity.ok(chars);
    }

    @PostMapping
    public ResponseEntity<Void> addCharacter(@RequestBody Character character) {
        characterRepository.save(character);
        return ResponseEntity.ok().build();
    }

}
