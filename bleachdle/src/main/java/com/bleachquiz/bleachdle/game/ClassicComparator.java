package com.bleachquiz.bleachdle.game;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.bleachquiz.bleachdle.ability.Ability;
import com.bleachquiz.bleachdle.character.Character;

final class ClassicComparator {

    private ClassicComparator() {
    }

    static ClassicComparisonDto compare(Character guessed, Character answer) {
        MatchStatus race = compareSets(guessed.getRace(), answer.getRace());
        MatchStatus gender = guessed.getGender().equalsIgnoreCase(answer.getGender())
                ? MatchStatus.EQUAL
                : MatchStatus.NONE;
        MatchStatus abilities = compareSets(abilityTypes(guessed), abilityTypes(answer));

        NumericComparison firstAppearanceValue = compareNumeric(
                guessed.getFirstAppearance().getValue(), answer.getFirstAppearance().getValue());

        MatchStatus status = guessed.getStatus() != null && guessed.getStatus().equalsIgnoreCase(answer.getStatus())
                ? MatchStatus.EQUAL
                : MatchStatus.NONE;

        NumericComparison height = compareNumeric(guessed.getHeight(), answer.getHeight());

        return new ClassicComparisonDto(
                race,
                gender,
                abilities,
                firstAppearanceValue.trend(),
                status,
                height.status(),
                height.trend());
    }

    private static List<String> abilityTypes(Character character) {
        return character.getAbilities().stream()
                .map(Ability::getType)
                .toList();
    }

    private static MatchStatus compareSets(List<String> guessedValues, List<String> answerValues) {
        Set<String> guessedSet = normalize(guessedValues);
        Set<String> answerSet = normalize(answerValues);

        if (guessedSet.equals(answerSet)) {
            return MatchStatus.EQUAL;
        }

        Set<String> intersection = new HashSet<>(guessedSet);
        intersection.retainAll(answerSet);
        return intersection.isEmpty() ? MatchStatus.NONE : MatchStatus.PARTIAL;
    }

    private static Set<String> normalize(List<String> values) {
        return values.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private static NumericComparison compareNumeric(Integer guessedValue, Integer answerValue) {
        if (guessedValue == null || answerValue == null) {
            return NumericComparison.EMPTY;
        }
        if (Objects.equals(guessedValue, answerValue)) {
            return new NumericComparison(MatchStatus.EQUAL, ValueTrend.EQUAL);
        }
        ValueTrend trend = guessedValue < answerValue ? ValueTrend.HIGHER : ValueTrend.LOWER;
        return new NumericComparison(MatchStatus.NONE, trend);
    }

    private record NumericComparison(MatchStatus status, ValueTrend trend) {
        static final NumericComparison EMPTY = new NumericComparison(null, null);
    }
}
