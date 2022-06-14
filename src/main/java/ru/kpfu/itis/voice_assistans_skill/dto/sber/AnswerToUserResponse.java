package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerToUserResponse {
    @NonNull
    Device device;
    @JsonProperty("auto_listening")
    boolean autoListening = true;
    String pronounceText;
    List<Item> items;
}
