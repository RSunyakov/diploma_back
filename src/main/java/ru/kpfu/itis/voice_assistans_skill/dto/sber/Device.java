package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Device {
    @NonNull
    String surface;
    Capabilities capabilities;


}
