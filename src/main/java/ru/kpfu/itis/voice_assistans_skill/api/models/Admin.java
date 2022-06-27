package ru.kpfu.itis.voice_assistans_skill.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.kpfu.itis.voice_assistans_skill.model.Test;
import ru.kpfu.itis.voice_assistans_skill.model.User;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "admin")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Admin {
    @Id
    @Column(nullable = false)
    @GeneratedValue
    Long id;

    String login;

    String password;

    @OneToMany()
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    List<User> users;

    @OneToMany()
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    List<Test> tests;
}
