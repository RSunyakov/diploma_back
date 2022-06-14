package ru.kpfu.itis.voice_assistans_skill.dto.yandex;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kpfu.itis.voice_assistans_skill.dto.Request;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YandexAliceRequest extends Request {
    YAMetadata meta;
    @NonNull
    YASkillRequest request;
    @NonNull
    YASession session;
    String version;
}