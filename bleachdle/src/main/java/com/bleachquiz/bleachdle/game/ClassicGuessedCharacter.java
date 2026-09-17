package com.bleachquiz.bleachdle.game;

import java.util.List;

import com.bleachquiz.bleachdle.ability.Ability;
import com.bleachquiz.bleachdle.character.Character;

public record ClassicGuessedCharacter(
        String id,
        String name,
        String avatar,
        List<String> race,
        String gender,
        List<String> abilityTypes,
        String firstAppearanceMedia,
        Integer firstAppearanceValue,
        String status,
        Integer height) {

    public static ClassicGuessedCharacter from(Character character) {
        return new ClassicGuessedCharacter(
                character.getId(),
                character.getName(),
                character.getAvatar(),
                character.getRace(),
                character.getGender(),
                character.getAbilities().stream().map(Ability::getType).toList(),
                character.getFirstAppearance().getMedia(),
                character.getFirstAppearance().getValue(),
                character.getStatus(),
                character.getHeight());
    }
}
