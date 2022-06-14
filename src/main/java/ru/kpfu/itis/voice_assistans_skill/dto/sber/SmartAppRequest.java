package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kpfu.itis.voice_assistans_skill.dto.Request;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmartAppRequest {
    @NonNull
    Long messageId;
    @NonNull
    String sessionId;
    @NonNull
    String messageName;
    @NonNull
    SAUserIdentifier uuid;
    @NonNull
    MessageToSkillRequest payload;
}
