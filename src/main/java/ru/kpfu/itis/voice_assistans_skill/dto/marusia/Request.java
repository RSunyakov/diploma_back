package ru.kpfu.itis.voice_assistans_skill.dto.marusia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    String command;
    @JsonProperty("original_utterance")
    String originalUtterance;
    String type;
    /*@Nullable
    Object payload;*/
    Nlu nlu;
}
