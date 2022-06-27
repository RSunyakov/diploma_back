package ru.kpfu.itis.voice_assistans_skill.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.voice_assistans_skill.api.models.ResponseModel;
import ru.kpfu.itis.voice_assistans_skill.model.Test;
import ru.kpfu.itis.voice_assistans_skill.model.User;
import ru.kpfu.itis.voice_assistans_skill.repository.TestRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.UserRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UserRepository userRepository;

    private final TestRepository testRepository;

    @GetMapping("/all/{adminId}")
    public ResponseModel<List<User>> getAllUsers(@PathVariable Long adminId) {
        return new ResponseModel<>(userRepository.findAllByAdminId(adminId));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseModel<User>> addTests(@PathVariable String id, @RequestBody List<Test> tests) throws URISyntaxException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Test> currentTests = user.getTests();
            currentTests.addAll(tests);
            user.setTests(currentTests);
            userRepository.save(user);
            for (Test test : user.getTests()) {
                List<User> users = test.getUsers();
                if (users == null) {
                    List<User> rootUsers = new ArrayList<>();
                    rootUsers.add(user);
                    test.setUsers(rootUsers);
                } else {
                    users.add(user);
                    test.setUsers(users);
                }
                testRepository.save(test);
            }
            return ResponseEntity.created(new URI("/users/" + user.getUserId())).body(new ResponseModel<>(user));
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
