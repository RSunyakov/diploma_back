package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Capabilities {
    @NonNull
    AvailableCapabilities screen;
    @NonNull
    AvailableCapabilities mic;
    @NonNull
    AvailableCapabilities speak;

}
