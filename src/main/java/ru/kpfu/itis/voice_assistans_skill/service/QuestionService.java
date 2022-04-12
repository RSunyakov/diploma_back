package ru.kpfu.itis.voice_assistans_skill.service;

import ru.kpfu.itis.voice_assistans_skill.dto.YandexAliceRequest;
import ru.kpfu.itis.voice_assistans_skill.dto.YandexAliceResponse;

public interface QuestionService {

    YandexAliceResponse talkVoiceAssistant(YandexAliceRequest request);
}
