package ru.kpfu.itis.voice_assistans_skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.voice_assistans_skill.model.Result;
import ru.kpfu.itis.voice_assistans_skill.model.User;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllByUser(User user);
}
