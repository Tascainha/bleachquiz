package com.bleachquiz.bleachdle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bleachquiz.bleachdle.character.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<Character> findByName(String name);
}
