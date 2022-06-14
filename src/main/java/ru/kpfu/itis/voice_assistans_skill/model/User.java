package ru.kpfu.itis.voice_assistans_skill.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE)
@Entity(name = "local_user")
public class User {
    @Id
    @Column(nullable = false)
    String userId;

    String firstName;

    String lastName;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    List<Test> tests;

    @OneToOne(fetch = FetchType.LAZY)
    Question currentQuestion;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    List<Question> rightAnsweredQuestions;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    List<Question> answeredQuestions;

    /*@OneToOne(fetch = FetchType.LAZY)
    private Scenario scenario;*/
}
