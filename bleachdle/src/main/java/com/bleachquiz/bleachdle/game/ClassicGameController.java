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

@RestController
@RequestMapping("/api/game/classic")
public class ClassicGameController {

    private final DailyCharacterService dailyCharacterService;
    private final CharacterRepository characterRepository;

    public ClassicGameController(DailyCharacterService dailyCharacterService,
            CharacterRepository characterRepository) {
        this.dailyCharacterService = dailyCharacterService;
        this.characterRepository = characterRepository;
    }

    @GetMapping("/today")
    public ResponseEntity<Map<String, Object>> today() {
        LocalDate date = dailyCharacterService.today();
        return ResponseEntity.ok(Map.of("date", date.toString()));
    }

    @PostMapping("/guess")
    public ResponseEntity<ClassicGuessResultDto> guess(@RequestBody GuessRequest request) {
        Character answer = dailyCharacterService.characterOfTheDay(GameMode.CLASSIC);
        Character guessed = characterRepository.findById(request.characterId())
                .orElse(null);

        if (guessed == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean correct = guessed.getId().equals(answer.getId());
        ClassicComparisonDto comparison = ClassicComparator.compare(guessed, answer);
        return ResponseEntity.ok(ClassicGuessResultDto.of(correct, ClassicGuessedCharacter.from(guessed),
                answer.getName(), comparison));
    }
}
