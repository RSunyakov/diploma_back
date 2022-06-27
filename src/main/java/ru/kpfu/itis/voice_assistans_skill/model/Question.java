package ru.kpfu.itis.voice_assistans_skill.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.kpfu.itis.voice_assistans_skill.api.models.Admin;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "question")
@ToString(exclude = {"rightUsers", "allUsers", "test"})
public class Question {
    @Id
    @Column(nullable = false)
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String question;

    @Column(nullable = false)
    String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    @JsonIgnore
    //@JsonBackReference
    Test test;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinTable(
            name = "right_answered_questions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    @JsonIgnore
    List<User> rightUsers;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
            @JoinTable(
                    name = "answered_questions",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "question_id")
            )
    @JsonIgnore
    List<User> allUsers;

    boolean isOpen;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Question)) {
            return false;
        }
        Question question1 = (Question) obj;
        return question1.getId().equals(getId());
    }
}
