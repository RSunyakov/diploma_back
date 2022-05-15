package ru.kpfu.itis.voice_assistans_skill.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.voice_assistans_skill.entity.SessionEntity;
import ru.kpfu.itis.voice_assistans_skill.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
}
