package ru.kpfu.itis.voice_assistans_skill.dto.sber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Entities {
    @JsonProperty("CCY_TOKEN")
    CCYToken ccyToken;
    @JsonProperty("MONEY_TOKEN")
    MoneyToken moneyToken;
    @JsonProperty("NUM_TOKEN")
    NumToken numToken;
}
