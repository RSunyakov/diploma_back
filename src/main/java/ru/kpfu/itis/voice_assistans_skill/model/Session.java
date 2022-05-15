package ru.kpfu.itis.voice_assistans_skill.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE)
@Entity(name = "session")
public class Session {
    @Id
    @Column(nullable = false)
    String sessionId;

    @OneToOne
    Question currentQuestion;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Question> answeredQuestions;
}
