package com.bleachquiz.bleachdle.character;

public record CharacterSummary(String id, String name, String avatar) {

    public static CharacterSummary from(Character character) {
        return new CharacterSummary(character.getId(), character.getName(), character.getAvatar());
    }
}
