package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @JsonProperty("original_text")
    String originalText;
    @JsonProperty("asr_normalized_message")
    String asrNormalizedMessage;
    @JsonProperty("normalized_text")
    String normalizedText;
}
