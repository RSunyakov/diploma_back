package ru.kpfu.itis.voice_assistans_skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.voice_assistans_skill.api.models.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findAdminByLogin(String login);
}
