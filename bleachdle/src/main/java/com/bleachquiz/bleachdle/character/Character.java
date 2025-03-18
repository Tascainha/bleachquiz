package com.bleachquiz.bleachdle.character;

import java.util.ArrayList;
import java.util.List;
import com.bleachquiz.bleachdle.status.Zanpakuto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @Column(nullable = false)
    private List<String> description = new ArrayList<>();

    @Column(nullable = false)
    private String race;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String height;

    @OneToOne
    private Zanpakuto zanpakuto;

    @Column(nullable = false)
    private String firstAppearance;

    @Column(nullable = false)
    private List<String> media = new ArrayList<>();

}
