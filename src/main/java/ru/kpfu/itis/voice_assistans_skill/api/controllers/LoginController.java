package ru.kpfu.itis.voice_assistans_skill.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.voice_assistans_skill.api.models.Admin;
import ru.kpfu.itis.voice_assistans_skill.api.models.ResponseModel;
import ru.kpfu.itis.voice_assistans_skill.api.models.Webhooks;
import ru.kpfu.itis.voice_assistans_skill.dto.Response;
import ru.kpfu.itis.voice_assistans_skill.repository.AdminRepository;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/admins", consumes = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private final AdminRepository adminRepository;


    @PostMapping("/register")
    public ResponseEntity<ResponseModel<Admin>> createAdmin(@RequestBody Admin admin) throws URISyntaxException {
        Admin savedAdmin = adminRepository.save(admin);
        return ResponseEntity.created(new URI("/admins" + savedAdmin.getId())).body(new ResponseModel<>(savedAdmin));
    }

    @PostMapping("/login")
    public ResponseModel<Admin> login(@RequestBody Admin admin) {
        Admin adminInRepo = adminRepository.findAdminByLogin(admin.getLogin());
        return new ResponseModel<>(adminInRepo);
    }

    @GetMapping("/{id}/url")
    public ResponseModel<Webhooks> getWebhooks(@PathVariable Long id) {
        return new ResponseModel<>(new Webhooks("https://itis-vkr-srr.ru/api/v1/" + id + "/alice", "https://itis-vkr-srr.ru/api/v1/" + id + "/marusia", "https://itis-vkr-srr.ru/api/v1/" + id + "/sber"));
    }
}
