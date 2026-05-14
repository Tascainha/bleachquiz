package com.bleachquiz.bleachdle.character;

import java.util.List;

import com.bleachquiz.bleachdle.ability.Ability;
import com.bleachquiz.bleachdle.firstappearance.FirstAppearance;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "characters")
public class Character {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String avatar;

    @ElementCollection
    @CollectionTable(name = "character_races")
    private List<String> race;

    @Column(nullable = false)
    private String gender;

    @ElementCollection
    @CollectionTable(name = "character_abilities")
    private List<Ability> abilities;
    
    @Embedded
    private FirstAppearance firstAppearance;



}
