package ru.kpfu.itis.voice_assistans_skill.service;

import ru.kpfu.itis.voice_assistans_skill.dto.Request;
import ru.kpfu.itis.voice_assistans_skill.dto.Response;

public interface ResultService {
    Response getResult(Request request);
}
