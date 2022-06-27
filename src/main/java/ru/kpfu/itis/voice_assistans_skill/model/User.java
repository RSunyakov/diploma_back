package ru.kpfu.itis.voice_assistans_skill.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import ru.kpfu.itis.voice_assistans_skill.api.models.Admin;

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
    @JsonProperty("user_id")
    String userId;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    //@JsonBackReference
    List<Test> tests;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    Question currentQuestion;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    List<Question> rightAnsweredQuestions;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    List<Question> answeredQuestions;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    Admin admin;

    /*@OneToOne(fetch = FetchType.LAZY)
    private Scenario scenario;*/
}
