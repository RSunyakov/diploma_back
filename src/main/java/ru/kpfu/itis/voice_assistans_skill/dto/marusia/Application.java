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
public class Application {
    @JsonProperty("application_id")
    String applicationId;
    @JsonProperty("application_type")
    String applicationType;
}
