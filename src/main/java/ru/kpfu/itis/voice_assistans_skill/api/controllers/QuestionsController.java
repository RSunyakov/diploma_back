package ru.kpfu.itis.voice_assistans_skill.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.voice_assistans_skill.api.models.ResponseModel;
import ru.kpfu.itis.voice_assistans_skill.model.Question;
import ru.kpfu.itis.voice_assistans_skill.repository.QuestionRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/questions", consumes = MediaType.APPLICATION_JSON_VALUE)
public class QuestionsController {

    private final QuestionRepository questionRepository;

    @GetMapping("/{id}")
    public ResponseModel<Question> getQuestion(@PathVariable Long id) {
        return new ResponseModel<>(questionRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<ResponseModel<Question>> createQuestion(@RequestBody Question question) throws URISyntaxException {
        Question savedQuestion = questionRepository.save(question);
        return ResponseEntity.created(new URI("/questions/" + savedQuestion.getId())).body(new ResponseModel<>(savedQuestion));
    }

    @GetMapping
    public ResponseModel<List<Question>> getAllQuestions() {
        System.out.println("heh");
        return new ResponseModel<>(questionRepository.findAll());
    }
}
