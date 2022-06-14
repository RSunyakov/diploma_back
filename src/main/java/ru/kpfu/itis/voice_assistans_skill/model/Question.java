package ru.kpfu.itis.voice_assistans_skill.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "question")
public class Question {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String question;

    @Column(nullable = false)
    String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    Test test;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinTable(
            name = "right_answered_questions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    List<User> rightUsers;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
            @JoinTable(
                    name = "answered_questions",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "question_id")
            )
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
