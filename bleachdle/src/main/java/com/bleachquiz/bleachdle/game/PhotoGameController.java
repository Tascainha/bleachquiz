package com.bleachquiz.bleachdle.game;

import java.time.LocalDate;
import java.util.Map;
import java.util.Random;

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
@RequestMapping("/api/game/photo")
public class PhotoGameController {

    private static final int CROP_MIN = 15;
    private static final int CROP_MAX = 85;

    private final DailyCharacterService dailyCharacterService;
    private final CharacterRepository characterRepository;

    public PhotoGameController(DailyCharacterService dailyCharacterService,
            CharacterRepository characterRepository) {
        this.dailyCharacterService = dailyCharacterService;
        this.characterRepository = characterRepository;
    }

    @GetMapping("/today")
    public ResponseEntity<Map<String, Object>> today() {
        Character answer = dailyCharacterService.characterOfTheDay(GameMode.PHOTO);
        LocalDate date = dailyCharacterService.today();
        long seed = (answer.getId() + date).hashCode();
        Random random = new Random(seed);
        int cropX = CROP_MIN + random.nextInt(CROP_MAX - CROP_MIN + 1);
        int cropY = CROP_MIN + random.nextInt(CROP_MAX - CROP_MIN + 1);

        return ResponseEntity.ok(Map.of(
                "date", date.toString(),
                "imageUrl", answer.getAvatar(),
                "cropX", cropX,
                "cropY", cropY));
    }

    @PostMapping("/guess")
    public ResponseEntity<GuessResultDto> guess(@RequestBody GuessRequest request) {
        Character answer = dailyCharacterService.characterOfTheDay(GameMode.PHOTO);
        Character guessed = characterRepository.findById(request.characterId())
                .orElse(null);

        if (guessed == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean correct = guessed.getId().equals(answer.getId());
        return ResponseEntity.ok(GuessResultDto.of(correct, CharacterSummary.from(guessed), answer.getName()));
    }
}
