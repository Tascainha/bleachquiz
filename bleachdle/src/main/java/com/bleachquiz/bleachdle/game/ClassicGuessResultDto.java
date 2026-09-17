package com.bleachquiz.bleachdle.game;

public record ClassicGuessResultDto(
        boolean correct,
        ClassicGuessedCharacter guessed,
        String answerName,
        ClassicComparisonDto comparison) {

    public static ClassicGuessResultDto of(boolean correct, ClassicGuessedCharacter guessed, String answerName,
            ClassicComparisonDto comparison) {
        return new ClassicGuessResultDto(correct, guessed, correct ? answerName : null, comparison);
    }
}
