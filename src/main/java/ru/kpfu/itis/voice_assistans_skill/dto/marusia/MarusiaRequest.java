package ru.kpfu.itis.voice_assistans_skill.dto.marusia;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarusiaRequest {
    Meta meta;
    Request request;
    Session session;
    String version;
}
