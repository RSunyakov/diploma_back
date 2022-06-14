package ru.kpfu.itis.voice_assistans_skill.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.voice_assistans_skill.dto.Request;
import ru.kpfu.itis.voice_assistans_skill.dto.Response;
import ru.kpfu.itis.voice_assistans_skill.model.Scenario;
import ru.kpfu.itis.voice_assistans_skill.model.User;
import ru.kpfu.itis.voice_assistans_skill.repository.ScenarioRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.UserRepository;
import ru.kpfu.itis.voice_assistans_skill.utility.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MappingServiceImpl implements MappingService {

    private final QuestionService questionService;

    private boolean waitingUserName = false;

    private final UserRepository userRepository;

    private final ScenarioRepository scenarioRepository;

    private final TestService testService;

    private final ResultService resultService;

    private final String menuResponse = "Данный навык предназначен для прохождения тестирований. Для просмотра доступных тестов, произнесите \"Покажи список тестов\", для просмотра результатов: \"Покажи результаты\", для начала тестирования: \"Запусти тестирование название_теста\" ";

    @Override
    public Response mappingRequest(Request request) {
        String responseText = "";
        if (request.getUserId() == null) {
            responseText = "Для пользования навыком необходимо авторизоваться в голосовом ассистенте";
        } else {
            if (waitingUserName) {
                List<String> userName = Arrays.asList(request.getCommand().split(" "));
               /* Scenario scenario = new Scenario(1L, "");
                System.out.println("scenario: " + scenario);
                scenarioRepository.save(scenario);*/
                User user = new User(request.getUserId(), userName.get(0), userName.get(1), new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>());
                userRepository.save(user);
                responseText = "Приятно познакомиться, " + request.getCommand() + " . ";
                waitingUserName = false;
                request.setNewSession(true);
            }
            Optional<User> optionalUser = userRepository.findById(request.getUserId());
            if (optionalUser.isPresent()) {
                if (request.isNewSession()) {
                    responseText = responseText.concat(menuResponse);
                    return new Response(responseText);
                }
                ///Место где пользователь авторизован и указал свое имя
                return mapping(request);
            } else {
                responseText = "Для продолжения работы необходимо представиться, какие у вас имя и фамилия?";
                waitingUserName = true;
            }
        }
        return new Response(responseText);
    }

    public Response mapping(Request request) {
        List<Scenario> scenarios = scenarioRepository.findAll();
        if (!scenarios.isEmpty()) {
           Scenario scenario = scenarios.get(0);
           switch (scenario.getName()) {
               case "Test":
                   return questionService.talkVoiceAssistant(request);
               case "List":
                   return testService.getAvailableTests(request);
               case "Result":
                   return resultService.getResult(request);
           }
        }
        Command matchedValue = Command.ifContains(request.getCommand());
        if (matchedValue != null) {
            Optional<User> optionalUser = userRepository.findById(request.getUserId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                switch (matchedValue) {
                    case Test:
                        /*System.out.println("tut: ");
                        user.setScenario(new Scenario(1L, Command.Test.name()));
                        userRepository.save(user);
                        System.out.println("just user: " + user);
                        System.out.println("tttt: " + userRepository.findById(request.getUserId()).get());*/
                        scenarioRepository.save(new Scenario(1L, Command.Test.name()));
                        int commandIndex = request.getCommand().lastIndexOf(Command.Test.getCommand());
                        String testName = request.getCommand().substring(commandIndex + Command.Test.getCommand().length() + 1);
                        return questionService.checkTestAvailable(request, testName);
                    case List:
                        /*user.setScenario(new Scenario(1L, Command.List.name()));
                        userRepository.save(user);*/
                        scenarioRepository.save(new Scenario(1L, Command.List.name()));
                        return testService.getAvailableTests(request);
                    case Results:
                        /*user.setScenario(new Scenario(1L, Command.Results.name()));
                        userRepository.save(user);*/
                        scenarioRepository.save(new Scenario(1L, Command.Results.name()));
                        return resultService.getResult(request);
                    case Menu:
                        return new Response(menuResponse);
                    default:
                        return new Response("Извините, я не понял вашу команду");
                }
            }
        }
        return new Response("Извините, я не понял вашу команду");
    }
}
