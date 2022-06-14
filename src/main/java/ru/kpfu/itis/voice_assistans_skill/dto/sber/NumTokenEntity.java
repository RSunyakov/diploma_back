package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NumTokenEntity {
    @JsonProperty("adjectival_number")
    boolean adjectivalNumber;

    double value;
}
