package com.bleachquiz.bleachdle.firstappearance;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class FirstAppearance {
 
    private String media;

    private Integer value;

}
