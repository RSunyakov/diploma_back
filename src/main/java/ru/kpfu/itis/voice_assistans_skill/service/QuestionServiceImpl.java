package ru.kpfu.itis.voice_assistans_skill.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.voice_assistans_skill.dto.YandexAliceRequest;
import ru.kpfu.itis.voice_assistans_skill.dto.YandexAliceResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    @Override
    public YandexAliceResponse talkVoiceAssistant(YandexAliceRequest request) {
        String responseText = "Привет! Я помогу подготовиться тебе к тесту. Тебе нужно ответить на несколько вопросов";
    }
}
