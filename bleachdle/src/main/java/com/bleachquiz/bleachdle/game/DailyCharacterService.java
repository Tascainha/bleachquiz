package com.bleachquiz.bleachdle.game;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bleachquiz.bleachdle.character.Character;
import com.bleachquiz.bleachdle.character.CharacterRepository;

@Service
public class DailyCharacterService {

    private static final ZoneId ZONE = ZoneId.of("America/Sao_Paulo");

    private final CharacterRepository characterRepository;

    public DailyCharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public LocalDate today() {
        return LocalDate.now(ZONE);
    }

    public Character characterOfTheDay(GameMode mode) {
        List<Character> characters = characterRepository.findAll().stream()
                .sorted(Comparator.comparing(Character::getId))
                .toList();

        if (characters.isEmpty()) {
            throw new IllegalStateException("No characters available");
        }

        long dayIndex = today().toEpochDay() + mode.getOffset();
        int index = (int) Math.floorMod(dayIndex, characters.size());
        return characters.get(index);
    }
}
