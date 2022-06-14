package ru.kpfu.itis.voice_assistans_skill.dto.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YASkillRequest {
    @NonNull
    String command;
    @JsonProperty("original_utterance")
    String originalUtterance;
    YARequestType type;
    YARequestMarkup markup;
    YANaturalLanguageUnderstanding nlu;
}
