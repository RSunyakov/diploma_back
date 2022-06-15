package ru.kpfu.itis.voice_assistans_skill.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.voice_assistans_skill.dto.Request;
import ru.kpfu.itis.voice_assistans_skill.dto.Response;
import ru.kpfu.itis.voice_assistans_skill.model.Scenario;
import ru.kpfu.itis.voice_assistans_skill.model.Test;
import ru.kpfu.itis.voice_assistans_skill.model.User;
import ru.kpfu.itis.voice_assistans_skill.repository.ScenarioRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.TestRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    private final UserRepository userRepository;

    private final ScenarioRepository scenarioRepository;

    @Override
    public Response getAvailableTests(Request request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        String responseText = "";
        List<Test> availableTest = new ArrayList<>();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Test> tests = testRepository.findAll();
            for (Test test : tests) {
                for (User userTest : test.getUsers()) {
                    if (user.getUserId().equals(userTest.getUserId())) {
                        availableTest.add(test);
                    }
                }
            }
        }
        if (availableTest.isEmpty()) {
            return new Response("На данный момент нет тестов, доступные вам");
        }
        for (Test test : availableTest) {
            responseText = responseText.concat(" " + test.getName() + ", ");
        }
       /* User user = userRepository.findById(request.getUserId()).get();
        Scenario scenario = user.getScenario();
        scenario.setName("");
        user.setScenario(scenario);
        userRepository.save(user);*/
        scenarioRepository.deleteAll();
        return new Response("Для вас доступны следующие тесты: " + responseText + "чтобы запустить тестирование, произнесите Запусти тестирование название_теста ");
    }

    @Override
    public Response startTest(Request request) {
        return null;
    }
}
