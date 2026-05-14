package com.bleachquiz.bleachdle.ability;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


@Embeddable
@Getter
@Setter
public class Ability {
    
    
    private String type;

    private String name;

}
