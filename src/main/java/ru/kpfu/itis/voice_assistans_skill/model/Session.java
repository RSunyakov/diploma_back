package ru.kpfu.itis.voice_assistans_skill.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.kpfu.itis.voice_assistans_skill.entity.Language;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Language language;

    @Column(nullable = false)
    String term;

    @Column(nullable = true)

    @OneToMany(mappedBy = "session")
    List<Question> answeredQuestions;
}
