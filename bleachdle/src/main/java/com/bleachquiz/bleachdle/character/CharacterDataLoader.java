package com.bleachquiz.bleachdle.character;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CharacterDataLoader implements ApplicationRunner {

    private static final List<String> DATA_FILES = Arrays.asList(
            "data/characters/arrancar.json",
            "data/characters/humans.json",
            "data/characters/quincy.json",
            "data/characters/shinigami.json");

    private final CharacterRepository characterRepository;
    private final ObjectMapper objectMapper;

    public CharacterDataLoader(CharacterRepository characterRepository, ObjectMapper objectMapper) {
        this.characterRepository = characterRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Character> characters = new ArrayList<>();
        for (String file : DATA_FILES) {
            try (InputStream inputStream = new ClassPathResource(file).getInputStream()) {
                Character[] parsed = objectMapper.readValue(inputStream, Character[].class);
                characters.addAll(Arrays.asList(parsed));
            }
        }

        // Os JSONs em data/characters/ são a fonte da verdade; o banco é recarregado do
        // zero a cada start para refletir qualquer edição feita neles.
        characterRepository.deleteAllInBatch();
        characterRepository.saveAll(characters);
    }
}
