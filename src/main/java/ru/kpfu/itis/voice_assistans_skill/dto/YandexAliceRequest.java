package ru.kpfu.itis.voice_assistans_skill.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YandexAliceRequest {
    YAMetadata meta;
    @NonNull
    YASkillRequest request;
    @NonNull
    YASession session;
    String version;
}