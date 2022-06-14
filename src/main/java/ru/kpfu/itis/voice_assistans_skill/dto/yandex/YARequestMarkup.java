package ru.kpfu.itis.voice_assistans_skill.dto.yandex;

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
public class YARequestMarkup {
    @JsonProperty("dangerous_content")
    boolean dangerousContent;
}