package ru.kpfu.itis.voice_assistans_skill.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Webhooks {
    @JsonProperty("alice_url")
    String aliceUrl;
    @JsonProperty("marusia_url")
    String marusiaUrl;
    @JsonProperty("sber_url")
    String sberUrl;
}
