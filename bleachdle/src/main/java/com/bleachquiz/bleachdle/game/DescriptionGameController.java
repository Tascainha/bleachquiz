package com.bleachquiz.bleachdle.game;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bleachquiz.bleachdle.character.Character;
import com.bleachquiz.bleachdle.character.CharacterRepository;
import com.bleachquiz.bleachdle.character.CharacterSummary;

@RestController
@RequestMapping("/api/game/description")
public class DescriptionGameController {

    private final DailyCharacterService dailyCharacterService;
    private final CharacterRepository characterRepository;

    public DescriptionGameController(DailyCharacterService dailyCharacterService,
            CharacterRepository characterRepository) {
        this.dailyCharacterService = dailyCharacterService;
        this.characterRepository = characterRepository;
    }

    @GetMapping("/today")
    public ResponseEntity<Map<String, Object>> today() {
        Character answer = dailyCharacterService.characterOfTheDay(GameMode.DESCRIPTION);
        LocalDate date = dailyCharacterService.today();
        String description = DescriptionMasker.mask(answer.getDescription(), answer.getName());
        return ResponseEntity.ok(Map.of("date", date.toString(), "description", description));
    }

    @PostMapping("/guess")
    public ResponseEntity<GuessResultDto> guess(@RequestBody GuessRequest request) {
        Character answer = dailyCharacterService.characterOfTheDay(GameMode.DESCRIPTION);
        Character guessed = characterRepository.findById(request.characterId())
                .orElse(null);

        if (guessed == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean correct = guessed.getId().equals(answer.getId());
        return ResponseEntity.ok(GuessResultDto.of(correct, CharacterSummary.from(guessed), answer.getName()));
    }
}
