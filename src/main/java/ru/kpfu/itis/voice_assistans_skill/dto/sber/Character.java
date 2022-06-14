package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Character {
    @NonNull
    String id;
    @NonNull
    String name;
    @NonNull
    String gender;
    @NonNull
    String appeal;

}
