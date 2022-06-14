package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageToSkillRequest {
    @JsonProperty("app_info")
    AppInfo appInfo;
    String intent;
    @JsonProperty("original_intent")
    String originalIntent;
    @JsonProperty("intent_meta")
    Object intentMeta;
    Time time;
    int index;
    String title;
    @JsonProperty("is_query_by_number")
    boolean isQueryByNumber;
    Device device;
    @JsonProperty("new_session")
    boolean newSession;
    Character character;
    Message message;
}
