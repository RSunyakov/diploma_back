/*
package ru.kpfu.itis.voice_assistans_skill.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.kpfu.itis.voice_assistans_skill.dto.*;
import ru.kpfu.itis.voice_assistans_skill.entity.Language;
import ru.kpfu.itis.voice_assistans_skill.entity.SessionEntity;
import ru.kpfu.itis.voice_assistans_skill.entity.TermEntity;
import ru.kpfu.itis.voice_assistans_skill.repository.DictionaryRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.SessionRepository;

@SpringBootTest
@ActiveProfiles("test")
class DictionaryServiceTest {
    @Autowired
    DictionaryService sut; // sut = System Under Test
    private YandexAliceResponse response;
    private final String sessionId = "2eac4854-fce721f3-b845abba-20d60";
    @MockBean
    private SessionRepository sessionRepository;
    @MockBean
    private DictionaryRepository dictionaryRepository;

    @Test
    void should_greet_user_and_ask_to_translate_random_word_and_store_new_session_when_new_session_started() {
        // в хранилище нет сессии
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        // из хранилища получили случайное слово
        when(dictionaryRepository.findAll()).thenReturn(
                List.of(new TermEntity(1L, Language.ENGLISH, "objection"))
        );

        response = sut.talkYandexAlice(
                new YandexAliceRequest(
                        new YASkillRequest("Алиса, запусти навык переводчик"),
                        new YASession(sessionId)));

        // проверяем, что Алиса предложила перевести слово
        assertEquals("Привет! Я помогу вам выучить английские слова. "
                        + "Как переводится на русский язык слово objection?",
                response.getResponse().getText());
        // проверяем, что сервис запомнил слово
        verify(sessionRepository).save(
                new SessionEntity(sessionId, Language.ENGLISH, "objection"));
    }

    @Test
    void should_check_term_translation_and_ask_to_translate_another_word_when_session_exists() {
        Long termId = 1001L;
        Long termTranslationId = 1002L;
        // в хранилище есть сессии
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(new SessionEntity(sessionId, Language.ENGLISH, "objection")));
        when(dictionaryRepository.findTranslation(Language.ENGLISH.toString(), "objection")).thenReturn(
                Optional.of(new TermEntity(termId, Language.RUSSIAN, "возражение"))
        );
        when(dictionaryRepository.findAll()).thenReturn(
                List.of(new TermEntity(null, Language.RUSSIAN, "персик"))
        );

        response = sut.talkYandexAlice(
                new YandexAliceRequest(
                        new YASkillRequest("возражение"),
                        new YASession(sessionId)));

        // проверяем, что Алиса подтвердила правильность перевода и предложила перевести новое слово
        assertEquals("Верно! Как переводится на английский язык слово персик?",
                response.getResponse().getText());
        // проверяем, что сервис запомнил новое слово
        verify(sessionRepository).save(
                new SessionEntity(sessionId, Language.RUSSIAN, "персик"));
    }
}*/
