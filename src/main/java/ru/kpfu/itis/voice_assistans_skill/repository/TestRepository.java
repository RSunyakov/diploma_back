package ru.kpfu.itis.voice_assistans_skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.voice_assistans_skill.model.Test;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByName(String testName);

    List<Test> findAllByAdminId(Long id);
}
