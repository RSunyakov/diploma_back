package ru.kpfu.itis.voice_assistans_skill.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.kpfu.itis.voice_assistans_skill.api.models.Admin;

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
    @GeneratedValue
    Long id;

    String name;

    @OneToMany(mappedBy = "test")
    @LazyCollection(LazyCollectionOption.TRUE)
    //@JsonManagedReference
    List<Question> questions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    @JsonIgnore
    Admin admin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_test",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    //@JsonManagedReference
    List<User> users;
}
