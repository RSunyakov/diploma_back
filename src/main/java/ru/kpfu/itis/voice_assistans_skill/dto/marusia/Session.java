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
public class Session {
    @JsonProperty("session_id")
    String sessionId;
    @JsonProperty("user_id")
    String userId;
    @JsonProperty("skill_id")
    String skillId;
    @JsonProperty("new")
    boolean isNew;
    @JsonProperty("message_id")
    int messageId;
    User user;
    Application application;
}
