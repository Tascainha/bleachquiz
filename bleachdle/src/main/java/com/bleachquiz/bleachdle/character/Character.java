package com.bleachquiz.bleachdle.character;

import java.util.ArrayList;
import java.util.List;
import com.bleachquiz.bleachdle.race.Race;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String bankai;
    // #zanpakuto

    @Column(nullable = false) // description para o mini game de description
    private String description;
    
    @ManyToMany
    private List<Race> races = new ArrayList<>();

    @Column(nullable = false)
    private String height;

    @Column(nullable = false)
    private String first_appearance;
    
//    teste

}
