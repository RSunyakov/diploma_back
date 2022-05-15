package ru.kpfu.itis.voice_assistans_skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.kpfu.itis.voice_assistans_skill.model.Question;
import ru.kpfu.itis.voice_assistans_skill.model.Session;
import ru.kpfu.itis.voice_assistans_skill.repository.QuestionRepository;
import ru.kpfu.itis.voice_assistans_skill.repository.SessionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class VoiceAssistansSkillApplication {



    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(VoiceAssistansSkillApplication.class, args);
        /*SessionRepository repository = context.getBean(SessionRepository.class);
       QuestionRepository questionRepository = context.getBean(QuestionRepository.class);
        Question question = new Question(1L, "Самый лучший язык", new Session(), "Java");
        Question question2 = new Question(2L, "Самый лучший язык2", new Session(), "Dart");
        questionRepository.save(question);
        questionRepository.save(question2);*/
    }

}
