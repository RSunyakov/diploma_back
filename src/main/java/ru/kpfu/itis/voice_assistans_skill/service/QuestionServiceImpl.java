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
        String responseText = "Привет! Я помогу подготовиться тебе к тесту. Тебе нужно ответить на несколько вопросов. ";
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getCurrentQuestion() != null) {
                Question currentQuestion = user.getCurrentQuestion();
                if (currentQuestion.getAnswer().equals(request.getCommand()) || (currentQuestion.isOpen() && StringDistance.findSimilarity(request.getCommand(), currentQuestion.getAnswer()) >= 0.65)) {
                    responseText = "Верно!";
                    //Помечаем вопрос как верный и как отвеченный
                    List<Question> rightAnsweredQuestions = user.getRightAnsweredQuestions();
                    System.out.println("right answers: " + rightAnsweredQuestions);
                    rightAnsweredQuestions.add(currentQuestion);
                    user.setRightAnsweredQuestions(rightAnsweredQuestions);
                    List<Question> answeredQuestions = user.getAnsweredQuestions();
                    answeredQuestions.add(currentQuestion);
                    user.setAnsweredQuestions(answeredQuestions);
                    System.out.println("user: " + user);
                    userRepository.save(user);
                    System.out.println("After save user: " + userRepository.findById(request.getUserId()).get());
                    //Ищу новый вопрос (условия для проверки на то, был ли вопрос уже отвечен)
                    List<Question> questions = currentTest.getQuestions();
                    List<Question> actuallyQuestions = new ArrayList<>();
                    for (Question question : questions) {
                        if  (!answeredQuestions.contains(question)) {
                            actuallyQuestions.add(question);
                        }
                        System.out.println("current Question: " + question);
                        System.out.println("all questions: " + questions);
                        System.out.println("answered questions: " + answeredQuestions);
                        System.out.println("actually Questions: " + actuallyQuestions);
                    }
                    if (actuallyQuestions.size() == 0) {
                        responseText += " Поздравляем! Вы ответили на все вопросы";
                        //Подсчитываю результаты
                        User actuallyUser = userRepository.findById(request.getUserId()).get();
                        int rightAnswerCount = actuallyUser.getRightAnsweredQuestions().size() - 1;
                        int questionCount = actuallyUser.getAnsweredQuestions().size() - 1;
                        double result = ((double) rightAnswerCount / ((double) questionCount));
                        int mark = getMark(result);
                        Result resultObject = new Result(null, actuallyUser, currentTest, result, mark);
                        resultRepository.save(resultObject);
                        actuallyUser.setCurrentQuestion(null);
                        actuallyUser.setAnsweredQuestions(new ArrayList<Question>());
                        actuallyUser.setRightAnsweredQuestions(new ArrayList<>());
                        System.out.println("user1: " + actuallyUser);
                        userRepository.save(actuallyUser);
                        System.out.println("After save user1: " + userRepository.findById(request.getUserId()).get());
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
                    System.out.println("wrong user: " + user);
                    userRepository.save(user);
                    System.out.println("wrong user after save: " + userRepository.findById(request.getUserId()).get());
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
                        User actuallyUser = userRepository.findById(request.getUserId()).get();
                        System.out.println("list right: " + actuallyUser.getRightAnsweredQuestions());
                        int rightAnswerCount = actuallyUser.getRightAnsweredQuestions().size() - 1;
                        int questionCount = actuallyUser.getAnsweredQuestions().size() - 1;
                        System.out.println("all answered: " + actuallyUser.getAnsweredQuestions().size());
                        double result = ((double) rightAnswerCount / ((double) questionCount));
                        System.out.println("result: " + result);
                        int mark = getMark(result);
                        Result resultObject = new Result(null, actuallyUser, currentTest, result, mark);
                        resultRepository.save(resultObject);
                        actuallyUser.setCurrentQuestion(null);
                        actuallyUser.setAnsweredQuestions(new ArrayList<>());
                        actuallyUser.setRightAnsweredQuestions(new ArrayList<>());
                        System.out.println("user1: " + actuallyUser);
                        userRepository.save(actuallyUser);
                        System.out.println("After save user1: " + userRepository.findById(request.getUserId()).get());
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
        /*Optional<Session> session = sessionRepository.findById(request.getSessionId());*/
       /* if (session.isPresent()) {
            Question currentQuestion = session.get().getCurrentQuestion();
            System.out.println("current question: " + currentQuestion);
            if (currentQuestion.getAnswer().equals(request.getCommand())) {
                responseText = "Верно!";
                Session currentSession = session.get();
                //Пометили текущий вопрос как отвеченный
                List<Question> sessionsAnsweredQuestions = currentSession.getAnsweredQuestions();
                sessionsAnsweredQuestions.add(currentQuestion);
                currentSession.setAnsweredQuestions(sessionsAnsweredQuestions);
                sessionRepository.save(currentSession);
                //Ищу новый вопрос (условия для проверки на то, был ли вопрос уже отвечен)
                List<Question> questions = questionRepository.findAll();
                List<Question> actuallyQuestions = new ArrayList<>();
                for (Question question : questions) {
                    if (!sessionsAnsweredQuestions.contains(question)) {
                        actuallyQuestions.add(question);
                    }
                }
                System.out.println("test" + actuallyQuestions);
                //Если не нашлось не отвеченных вопросов
                if (actuallyQuestions.size() == 0) {
                    responseText = "Поздрвляем! Вы ответили на все вопросы";
                    scenarioRepository.deleteAll();
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
            sessionRepository.save(new Session(request.getSessionId(), allQuestions.get(0), new ArrayList<>()));
            responseText += allQuestions.get(0).getQuestion();
        }
        return new Response(responseText);*/
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
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        System.out.println("userOptional: " + userOptional);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Test> tests = user.getTests();
            for (Test test : tests) {
                System.out.println("testCurrent: " + test);
                System.out.println("testName: " + testName);
                if (test.getName().equals(testName)) {
                    currentTest = test;
                    return talkVoiceAssistant(request);
                }
            }
        }
       /* User user = userRepository.findById(request.getUserId()).get();
        Scenario scenario = user.getScenario();
        scenario.setName("");
        user.setScenario(scenario);
        userRepository.save(user);*/
        scenarioRepository.deleteAll();
        return new Response("Данное тестирование не найдено, либо не доступно для вас");
    }
}
