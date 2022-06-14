package ru.kpfu.itis.voice_assistans_skill.dto.marusia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Response {
    @NonNull
    String text;
    String tts;
    //List<Button> buttons;
    @JsonProperty("end_session")
    boolean endSession;

}
