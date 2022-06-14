package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmartAppResponse {
    @NonNull
    Long messageId;
    @NonNull
    String sessionId;
    @NonNull
    String messageName;
    @NonNull
    SAUserIdentifier uuid;
    @NonNull
    AnswerToUserResponse payload;
}
