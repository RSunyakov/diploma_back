package ru.kpfu.itis.voice_assistans_skill.dto.yandex;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data //автогенерация сеттеров, геттеров, hashCode, equals и toString
@NoArgsConstructor //генерация конструктора без аргументов
// нужна для десериализации из JSON
@RequiredArgsConstructor //генерация конструктора для обязательных полей
@FieldDefaults(level = AccessLevel.PRIVATE) //по умолчанию доступ к членам - private
public class YandexAliceResponse {
    @NonNull //автопроверка аргументов на null
    YASkillResponse response;
    @NonNull
    String version = "1.0";
}