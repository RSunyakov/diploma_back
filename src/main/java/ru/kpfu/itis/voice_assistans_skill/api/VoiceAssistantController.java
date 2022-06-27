package ru.kpfu.itis.voice_assistans_skill.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.voice_assistans_skill.dto.Request;
import ru.kpfu.itis.voice_assistans_skill.dto.marusia.MarusiaRequest;
import ru.kpfu.itis.voice_assistans_skill.dto.marusia.MarusiaResponse;
import ru.kpfu.itis.voice_assistans_skill.dto.marusia.Response;
import ru.kpfu.itis.voice_assistans_skill.dto.sber.*;
import ru.kpfu.itis.voice_assistans_skill.dto.yandex.YASkillResponse;
import ru.kpfu.itis.voice_assistans_skill.dto.yandex.YandexAliceRequest;
import ru.kpfu.itis.voice_assistans_skill.dto.yandex.YandexAliceResponse;
import ru.kpfu.itis.voice_assistans_skill.service.MappingService;
import ru.kpfu.itis.voice_assistans_skill.service.QuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VoiceAssistantController {
    private final QuestionService questionService;

    private final MappingService mappingService;

    @PostMapping("/{id}/alice")
    public @ResponseBody YandexAliceResponse talkYandexAlice(
            @PathVariable Long id,
            @RequestBody YandexAliceRequest request) {
        return new YandexAliceResponse(new YASkillResponse(mappingService.mappingRequest(new Request(request.getSession().getSessionId(), request.getRequest().getCommand(), request.getSession().getUser().getUserId(), request.getSession().isNew(), id)).getResponseText()));
    }

    @PostMapping("/{id}/sber")
    public @ResponseBody SmartAppResponse talkSmartApp(
            @PathVariable Long id,
            @RequestBody SmartAppRequest request) {
        String responseText = mappingService.mappingRequest(new Request(request.getSessionId(), request.getPayload().getMessage().getOriginalText().toLowerCase(Locale.ROOT), request.getUuid().getUserId(), request.getPayload().isNewSession(), id)).getResponseText();
        List<Item> items = new ArrayList<>();
        items.add(new Item(new Bubble(responseText)));

        return new SmartAppResponse(request.getMessageId(), request.getSessionId(),
                "ANSWER_TO_USER", new SAUserIdentifier(request.getUuid().getUserId(), request.getUuid().getSub(), request.getUuid().getUserChannel()),
                new AnswerToUserResponse(request.getPayload().getDevice(), true, responseText, items));
    }

    @PostMapping("/{id}/marusia")
    public @ResponseBody MarusiaResponse talkMarusia(
            @PathVariable Long id,
            @RequestBody MarusiaRequest request) {
        Request localRequest = new Request(request.getSession().getSessionId(), request.getRequest().getOriginalUtterance(), request.getSession().getUserId(), request.getSession().isNew(), id);
        ru.kpfu.itis.voice_assistans_skill.dto.Response localResponse = mappingService.mappingRequest(localRequest);
        String responseText = localResponse.getResponseText();
        return new MarusiaResponse(new Response(responseText, responseText, false), request.getSession(), request.getVersion());
    }
}