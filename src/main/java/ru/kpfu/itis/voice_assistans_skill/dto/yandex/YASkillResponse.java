package ru.kpfu.itis.voice_assistans_skill.dto.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YASkillResponse {
    @NonNull
    String text;
    //аннотация для правильной десериализации поля из JSON
    //так как в Java принята нотация CamelCase, а в API - snake_case
    @JsonProperty("end_session")
    boolean endSession = false;
}
