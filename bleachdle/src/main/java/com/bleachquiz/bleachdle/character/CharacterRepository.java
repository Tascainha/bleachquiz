package com.bleachquiz.bleachdle.character;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {
    Optional<Character> findByName(String name);
}
