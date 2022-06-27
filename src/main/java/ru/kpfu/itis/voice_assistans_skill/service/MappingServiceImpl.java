package ru.kpfu.itis.voice_assistans_skill.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.voice_assistans_skill.api.models.Admin;
import ru.kpfu.itis.voice_assistans_skill.dto.Request;
import ru.kpfu.itis.voice_assistans_skill.dto.Response;
import ru.kpfu.itis.voice_assistans_skill.model.Scenario;
import ru.kpfu.itis.voice_assistans_skill.model.User;
import ru.kpfu.itis.voice_assistans_skill.repository.AdminRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.ScenarioRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.UserRepository;
import ru.kpfu.itis.voice_assistans_skill.utility.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ru.kpfu.itis.voice_assistans_skill.utility.Command.Test;

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

    private final AdminRepository adminRepository;

    private final String menuResponse = "Данный навык предназначен для прохождения тестирований. Для просмотра доступных тестов, произнесите \"Покажи список тестов\", для просмотра результатов: \"Покажи результаты\", для начала тестирования: \"Запусти тестирование название_теста\" ";

    @Override
    public Response mappingRequest(Request request) {
        String responseText = "";
        if (request.getUserId() == null) {
            responseText = "Для пользования навыком необходимо авторизоваться в голосовом ассистенте";
        } else {
            if (waitingUserName) {
                Optional<Admin> optionalAdmin = adminRepository.findById(request.getAdminId());
                if (optionalAdmin.isPresent()) {
                    Admin admin = optionalAdmin.get();
                    List<String> userName = Arrays.asList(request.getCommand().split(" "));
                    User user = new User(request.getUserId() + request.getAdminId(), userName.get(0), userName.get(1), new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(), admin);
                    userRepository.save(user);
                    responseText = "Приятно познакомиться, " + request.getCommand() + " . ";
                    waitingUserName = false;
                    request.setNewSession(true);

                    List<User> users = admin.getUsers();
                    users.add(user);
                    admin.setUsers(users);
                    adminRepository.save(admin);
                } else {
                    responseText = "Проблемы с URL (Неверный ID админа)";
                    return new Response(responseText);
                }
            }
            Optional<User> optionalUser = userRepository.findById(request.getUserId() + request.getAdminId());
            if (optionalUser.isPresent()) {
                if (request.isNewSession()) {
                    responseText = responseText.concat(menuResponse);
                    return new Response(responseText);
                }
                ///Место где пользователь авторизован и указал свое имя
                return mapping(request, request.getAdminId());
            } else {
                responseText = "Для продолжения работы необходимо представиться, какие у вас имя и фамилия?";
                waitingUserName = true;
            }
        }
        return new Response(responseText);
    }

    public Response mapping(Request request, Long id) {
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
            Optional<User> optionalUser = userRepository.findById(request.getUserId() + id);
            if (optionalUser.isPresent()) {
                switch (matchedValue) {
                    case Test:
                        scenarioRepository.save(new Scenario(1L, Test.name()));
                        int commandIndex = request.getCommand().lastIndexOf(Test.getCommand());
                        String testName = request.getCommand().substring(commandIndex + Test.getCommand().length() + 1);
                        return questionService.checkTestAvailable(request, testName);
                    case List:
                        scenarioRepository.save(new Scenario(1L, Command.List.name()));
                        return testService.getAvailableTests(request);
                    case Results:
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
