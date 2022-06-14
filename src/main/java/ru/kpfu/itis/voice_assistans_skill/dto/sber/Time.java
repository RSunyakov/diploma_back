package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Time {
    @NonNull
    Long timestamp;

    @NonNull
    @JsonProperty("timezone_id")
    String timezoneId;

    @NonNull
    @JsonProperty("timezone_offset_sec")
    Long timezoneOffsetSec;


}
