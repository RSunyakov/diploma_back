package ru.kpfu.itis.voice_assistans_skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.voice_assistans_skill.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
