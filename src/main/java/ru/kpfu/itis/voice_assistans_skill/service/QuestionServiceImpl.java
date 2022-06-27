package ru.kpfu.itis.voice_assistans_skill.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.voice_assistans_skill.dto.Request;
import ru.kpfu.itis.voice_assistans_skill.dto.Response;
import ru.kpfu.itis.voice_assistans_skill.model.*;
import ru.kpfu.itis.voice_assistans_skill.repository.*;
import ru.kpfu.itis.voice_assistans_skill.utility.StringDistance;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final ScenarioRepository scenarioRepository;

    private final UserRepository userRepository;

    private final ResultRepository resultRepository;

    private Test currentTest;

    @Override
    public Response talkVoiceAssistant(Request request) {
        String responseText = "Привет! Тебе нужно ответить на несколько вопросов. ";
        Optional<User> userOptional = userRepository.findById(request.getUserId() + request.getAdminId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getCurrentQuestion() != null) {
                Question currentQuestion = user.getCurrentQuestion();
                if (currentQuestion.getAnswer().equals(request.getCommand()) || (currentQuestion.isOpen() && StringDistance.findSimilarity(request.getCommand(), currentQuestion.getAnswer()) >= 0.65)) {
                    responseText = "Верно!";
                    //Помечаем вопрос как верный и как отвеченный
                    List<Question> rightAnsweredQuestions = user.getRightAnsweredQuestions();
                    rightAnsweredQuestions.add(currentQuestion);
                    user.setRightAnsweredQuestions(rightAnsweredQuestions);
                    List<Question> answeredQuestions = user.getAnsweredQuestions();
                    answeredQuestions.add(currentQuestion);
                    user.setAnsweredQuestions(answeredQuestions);
                    userRepository.save(user);
                    //Ищу новый вопрос (условия для проверки на то, был ли вопрос уже отвечен)
                    List<Question> questions = currentTest.getQuestions();
                    List<Question> actuallyQuestions = new ArrayList<>();
                    for (Question question : questions) {
                        if  (!answeredQuestions.contains(question)) {
                            actuallyQuestions.add(question);
                        }
                    }
                    if (actuallyQuestions.size() == 0) {
                        responseText += " Поздравляем! Вы ответили на все вопросы";
                        //Подсчитываю результаты
                        User actuallyUser = userRepository.findById(request.getUserId() + request.getAdminId()).get();
                        int rightAnswerCount = actuallyUser.getRightAnsweredQuestions().size() - 1;
                        int questionCount = actuallyUser.getAnsweredQuestions().size() - 1;
                        double result = ((double) rightAnswerCount / ((double) questionCount));
                        int mark = getMark(result);
                        Result resultObject = new Result(null, actuallyUser, currentTest, result, mark);
                        resultRepository.save(resultObject);
                        actuallyUser.setCurrentQuestion(null);
                        actuallyUser.setAnsweredQuestions(new ArrayList<Question>());
                        actuallyUser.setRightAnsweredQuestions(new ArrayList<>());
                        userRepository.save(actuallyUser);
                        scenarioRepository.deleteAll();
                    } else {
                        responseText += " Внимание вопрос: " + actuallyQuestions.get(0).getQuestion();
                        user.setCurrentQuestion(actuallyQuestions.get(0));
                        userRepository.save(user);
                    }
                } else {
                    responseText = "Неверно!";
                    //Помечаем вопрос как отвеченный (но сам ответ неверный)
                    List<Question> answeredQuestions = user.getAnsweredQuestions();
                    answeredQuestions.add(currentQuestion);
                    user.setAnsweredQuestions(answeredQuestions);
                    userRepository.save(user);
                    //Ищу новый вопрос (условия для проверки на то, был ли вопрос уже отвечен)
                    List<Question> questions = currentTest.getQuestions();
                    List<Question> actuallyQuestions = new ArrayList<>();
                    for (Question question : questions) {
                        if  (!answeredQuestions.contains(question)) {
                            actuallyQuestions.add(question);
                        }
                    }
                    if (actuallyQuestions.size() == 0) {
                        responseText += " Поздравляем! Вы ответили на все вопросы";
                        //Подсчитываю результаты
                        User actuallyUser = userRepository.findById(request.getUserId() + request.getAdminId()).get();
                        int rightAnswerCount = actuallyUser.getRightAnsweredQuestions().size() - 1;
                        int questionCount = actuallyUser.getAnsweredQuestions().size() - 1;
                        double result = ((double) rightAnswerCount / ((double) questionCount));
                        int mark = getMark(result);
                        Result resultObject = new Result(null, actuallyUser, currentTest, result, mark);
                        resultRepository.save(resultObject);
                        actuallyUser.setCurrentQuestion(null);
                        actuallyUser.setAnsweredQuestions(new ArrayList<>());
                        actuallyUser.setRightAnsweredQuestions(new ArrayList<>());
                        userRepository.save(actuallyUser);
                        scenarioRepository.deleteAll();
                    } else {
                        responseText += " Внимание вопрос: " + actuallyQuestions.get(0).getQuestion();
                        user.setCurrentQuestion(actuallyQuestions.get(0));
                        userRepository.save(user);
                    }

                }
            } else {
                user.setCurrentQuestion(currentTest.getQuestions().get(0));
                userRepository.save(user);
                responseText += currentTest.getQuestions().get(0).getQuestion();
            }
        }
        return new Response(responseText);
    }

    int getMark(double result) {
        int mark = 0;
        if (result == 1) {
            return 5;
        }
        if (result >= 3.0 / 4.0) {
            return 4;
        }
        if (result >= 0.5) {
            return 3;
        }
        if (result < 0.5) {
            return 2;
        }
        return mark;
    }

    @Override
    public Response checkTestAvailable(Request request, String testName) {
        Optional<User> userOptional = userRepository.findById(request.getUserId() + request.getAdminId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Test> tests = user.getTests();
            for (Test test : tests) {
                if (test.getName().equals(testName)) {
                    currentTest = test;
                    return talkVoiceAssistant(request);
                }
            }
        }
        scenarioRepository.deleteAll();
        return new Response("Данное тестирование не найдено, либо не доступно для вас");
    }
}
