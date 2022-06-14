package ru.kpfu.itis.voice_assistans_skill.dto.marusia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Meta {
    @JsonProperty("client_id")
    String clientId;
    String locale;
    String timezone;
    Interfaces interfaces;
}
