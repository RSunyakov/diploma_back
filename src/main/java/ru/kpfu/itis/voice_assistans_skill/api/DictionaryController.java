package ru.kpfu.itis.voice_assistans_skill.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.voice_assistans_skill.dto.YASkillResponse;
import ru.kpfu.itis.voice_assistans_skill.dto.YandexAliceRequest;
import ru.kpfu.itis.voice_assistans_skill.dto.YandexAliceResponse;
import ru.kpfu.itis.voice_assistans_skill.service.DictionaryService;

@RestController
@Slf4j
@RequestMapping("/api/v1/dictionary/yandex-alice-skill")
@RequiredArgsConstructor
public class DictionaryController {


    private final DictionaryService dictionaryService;

    @PostMapping
    public @ResponseBody YandexAliceResponse talkYandexAlice(
            @RequestBody  YandexAliceRequest request) {
        return dictionaryService.talkYandexAlice(request);
    }
}