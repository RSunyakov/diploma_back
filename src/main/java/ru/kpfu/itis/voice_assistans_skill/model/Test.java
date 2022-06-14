package ru.kpfu.itis.voice_assistans_skill.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "test")
@ToString(exclude = {"questions", "users"})
public class Test {
    @Id
    @Column
    Long id;

    String name;

    @OneToMany(mappedBy = "test")
    @LazyCollection(LazyCollectionOption.TRUE)
    List<Question> questions;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_test",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<User> users;
}
