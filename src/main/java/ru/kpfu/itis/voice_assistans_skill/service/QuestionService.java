package ru.kpfu.itis.voice_assistans_skill.service;

import ru.kpfu.itis.voice_assistans_skill.dto.Request;
import ru.kpfu.itis.voice_assistans_skill.dto.Response;
import ru.kpfu.itis.voice_assistans_skill.dto.yandex.YandexAliceRequest;
import ru.kpfu.itis.voice_assistans_skill.dto.yandex.YandexAliceResponse;

public interface QuestionService {

    Response talkVoiceAssistant(Request request);

    Response checkTestAvailable(Request request, String testName);
}
