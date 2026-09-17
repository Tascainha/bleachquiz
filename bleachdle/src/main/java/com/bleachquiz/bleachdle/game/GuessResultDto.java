package com.bleachquiz.bleachdle.game;

import com.bleachquiz.bleachdle.character.CharacterSummary;

public record GuessResultDto(boolean correct, CharacterSummary guessed, String answerName) {

    public static GuessResultDto of(boolean correct, CharacterSummary guessed, String answerName) {
        return new GuessResultDto(correct, guessed, correct ? answerName : null);
    }
}
