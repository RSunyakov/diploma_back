package ru.kpfu.itis.voice_assistans_skill.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE)
@Entity(name = "session_old")
public class SessionEntity {
    @Id
    @Column(nullable = false)
    String sessionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Language language;

    @Column(nullable = false)
    String term;
}