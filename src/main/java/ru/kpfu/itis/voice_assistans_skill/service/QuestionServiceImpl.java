package ru.kpfu.itis.voice_assistans_skill.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.voice_assistans_skill.dto.YASkillResponse;
import ru.kpfu.itis.voice_assistans_skill.dto.YandexAliceRequest;
import ru.kpfu.itis.voice_assistans_skill.dto.YandexAliceResponse;
import ru.kpfu.itis.voice_assistans_skill.model.Question;
import ru.kpfu.itis.voice_assistans_skill.model.Session;
import ru.kpfu.itis.voice_assistans_skill.repository.QuestionRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.SessionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final SessionRepository sessionRepository;
    private final QuestionRepository questionRepository;

    @Override
    public YandexAliceResponse talkVoiceAssistant(YandexAliceRequest request) {
        String responseText = "Привет! Я помогу подготовиться тебе к тесту. Тебе нужно ответить на несколько вопросов. ";
        Optional<Session> session = sessionRepository.findById(request.getSession().getSessionId());
        if (session.isPresent()) {
            Question currentQuestion = session.get().getCurrentQuestion();
            if (currentQuestion.getAnswer().equals(request.getRequest().getCommand())) {
                responseText = "Верно!";
                Session currentSession = session.get();
                //Пометили текущий вопрос как отвеченный
                List<Question> sessionsAnsweredQuestions = currentSession.getAnsweredQuestions();
                sessionsAnsweredQuestions.add(currentQuestion);
                currentSession.setAnsweredQuestions(sessionsAnsweredQuestions);
                //Ищу новый вопрос (условия для проверки на то, был ли вопрос уже отвечен)
                List<Question> questions = questionRepository.findAll();
                List<Question> actuallyQuestions = new ArrayList<>();
                for (Question question : questions) {
                    if (!sessionsAnsweredQuestions.contains(question)) {
                        actuallyQuestions.add(question);
                    }
                }
                //Если не нашлось не отвеченных вопросов
                if (actuallyQuestions.size() == 0) {
                    responseText = "Поздрвляем! Вы ответили на все вопросы";
                } else {
                    //Отдал вопрос, и пометил его как current
                    responseText += " Внимание вопрос: " + actuallyQuestions.get(0).getQuestion();
                    currentSession.setCurrentQuestion(actuallyQuestions.get(0));
                    sessionRepository.save(currentSession);
                }
            } else {
                responseText = "Неверно! Попытайся еще раз";
            }
        } else {
            //начальное условие, если пользователь первый раз болтает с Алисой
            List<Question> allQuestions = questionRepository.findAll();
            sessionRepository.save(new Session(request.getSession().getSessionId(), allQuestions.get(0), new ArrayList<>()));
            responseText += allQuestions.get(0).getQuestion();
        }
        return new YandexAliceResponse(new YASkillResponse(responseText));
    }
}
