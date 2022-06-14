package ru.kpfu.itis.voice_assistans_skill.dto.yandex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YASession {
    @JsonProperty("message_id")
    int messageId;
    @JsonProperty("session_id")
    @NonNull
    String sessionId;
    @JsonProperty("skill_id")
    String skillId;
    YAUser user;
    YAApplication application;
    @JsonProperty("new")
    boolean isNew;
}