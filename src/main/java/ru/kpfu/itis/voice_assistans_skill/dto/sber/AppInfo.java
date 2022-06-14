package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppInfo {
    @NonNull
    UUID projectId;
    @NonNull
    String frontendType;
}
