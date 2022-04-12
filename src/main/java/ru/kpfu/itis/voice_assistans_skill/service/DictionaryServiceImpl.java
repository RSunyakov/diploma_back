package ru.kpfu.itis.voice_assistans_skill.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.voice_assistans_skill.dto.YASkillResponse;
import ru.kpfu.itis.voice_assistans_skill.dto.YandexAliceRequest;
import ru.kpfu.itis.voice_assistans_skill.dto.YandexAliceResponse;
import ru.kpfu.itis.voice_assistans_skill.entity.Language;
import ru.kpfu.itis.voice_assistans_skill.entity.SessionEntity;
import ru.kpfu.itis.voice_assistans_skill.entity.TermEntity;
import ru.kpfu.itis.voice_assistans_skill.repository.DictionaryRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.SessionRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final SessionRepository sessionRepository;
    private final DictionaryRepository dictionaryRepository;
    private final Random random = new Random();

    @Override
    public YandexAliceResponse talkYandexAlice(YandexAliceRequest request) {
        String responseText = "Привет! Я помогу вам выучить английские слова.";

        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getRequest());
        Objects.requireNonNull(request.getRequest().getCommand());
        Objects.requireNonNull(request.getSession());
        String sessionId = request.getSession().getSessionId();
        Objects.requireNonNull(sessionId);

        Optional<SessionEntity> session = sessionRepository.findById(sessionId);
        if (session.isPresent()) {
            Optional<TermEntity> translation = dictionaryRepository.findTranslation(session.get().getLanguage().toString(), session.get().getTerm());
            if (translation.isPresent()) {
                if (translation.get().getTerm().equalsIgnoreCase(request.getRequest().getCommand())) {
                    responseText = "Верно!";
                } else {
                    responseText = "Не верно!";
                }
            }
        }
        List<TermEntity> terms = dictionaryRepository.findAll();
        if (!terms.isEmpty()) {
            TermEntity term = terms.get(random.nextInt(terms.size()));
            responseText += String.format(" Как переводится на %s язык слово %s?",
                    Language.ENGLISH.equals(term.getLanguage()) ? "русский" : "английский",
                    term.getTerm()
            );

            sessionRepository.save(new SessionEntity(sessionId, term.getLanguage(), term.getTerm()));
        }

        return new YandexAliceResponse(new YASkillResponse(responseText));
    }
}
