package ru.kpfu.itis.voice_assistans_skill.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.voice_assistans_skill.api.models.Admin;
import ru.kpfu.itis.voice_assistans_skill.api.models.ResponseModel;
import ru.kpfu.itis.voice_assistans_skill.model.Question;
import ru.kpfu.itis.voice_assistans_skill.model.Test;
import ru.kpfu.itis.voice_assistans_skill.repository.AdminRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.QuestionRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.TestRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@Slf4j
public class TestsController {

    private final TestRepository testRepository;

    private final QuestionRepository questionRepository;

    private final AdminRepository adminRepository;

   @PostMapping()
    public ResponseEntity<ResponseModel<Test>> createTest(@RequestBody Test test, @RequestParam() Long adminId) throws URISyntaxException {
       Test savedTest = testRepository.save(test);
       Admin admin = adminRepository.findById(adminId).orElseThrow(RuntimeException::new);
       test.setAdmin(admin);
       List<Test> tests = admin.getTests();
       tests.add(savedTest);
       admin.setTests(tests);
       adminRepository.save(admin);
       testRepository.save(test);
       return ResponseEntity.created(new URI("/tests/" + savedTest.getId())).body(new ResponseModel<>(savedTest));
   }

    @GetMapping("/{id}")
    public Test getTest(@PathVariable Long id) {
        return testRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @GetMapping("/all/{id}")
    public ResponseModel<List<Test>> getAllTests(@PathVariable Long id) {
       return new ResponseModel<>(testRepository.findAllByAdminId(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseModel<Test>> addQuestions(@PathVariable Long id, @RequestBody List<Question> questions) throws URISyntaxException {
       Optional<Test> optionalTest = testRepository.findById(id);
       if (optionalTest.isPresent()) {
           Test test = optionalTest.get();
           List<Question> currentQuestions = test.getQuestions();
           currentQuestions.addAll(questions);
           test.setQuestions(currentQuestions);
           testRepository.save(test);
           for (Question question : test.getQuestions()) {
               question.setTest(test);
               questionRepository.save(question);
           }
           return ResponseEntity.created(new URI("/tests/" + test.getId())).body(new ResponseModel<>(test));
       }
       return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
