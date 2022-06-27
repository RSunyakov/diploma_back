package ru.kpfu.itis.voice_assistans_skill.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.voice_assistans_skill.dto.Request;
import ru.kpfu.itis.voice_assistans_skill.dto.Response;
import ru.kpfu.itis.voice_assistans_skill.model.Result;
import ru.kpfu.itis.voice_assistans_skill.model.Scenario;
import ru.kpfu.itis.voice_assistans_skill.model.User;
import ru.kpfu.itis.voice_assistans_skill.repository.ResultRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.ScenarioRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.UserRepository;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final UserRepository userRepository;

    private final ResultRepository resultRepository;

    private final ScenarioRepository scenarioRepository;

    @Override
    public Response getResult(Request request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId() + request.getAdminId());
        List<Result> resultsWithoutDuplicate = new ArrayList<>();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Result> results = resultRepository.findAllByUser(user);
            Collections.reverse(results);
            Set<Long> resultIds = new HashSet<>();
            for (Result result : results) {
                if (!resultIds.contains(result.getTest().getId())) {
                    resultsWithoutDuplicate.add(result);
                    resultIds.add(result.getTest().getId());
                }
            }
        }
        StringBuilder responseText = new StringBuilder("На данный момент вы прошли следующие тестирования: ");
        if (resultsWithoutDuplicate.isEmpty()) {
            return new Response("На данный момент у вас нет пройденных тестирований");
        }
        for (Result result : resultsWithoutDuplicate) {
            responseText.append(result.getTest().getName());
            responseText.append(" (оценка: ").append(result.getMark());
            responseText.append(", процент правильных ответов: ").append(result.getResult()*100).append("%) ");
        }
        scenarioRepository.deleteAll();
        return new Response(responseText.toString());
    }
}
