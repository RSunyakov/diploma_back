package ru.kpfu.itis.voice_assistans_skill.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YANaturalLanguageUnderstanding {
    List<String> tokens = new ArrayList<>();
    List<YAEntity> entities = new ArrayList<>();
}