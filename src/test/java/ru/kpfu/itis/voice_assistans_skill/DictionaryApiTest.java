package ru.kpfu.itis.voice_assistans_skill;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest //маркер теста
@ActiveProfiles("test") //профиль Spring для тестирования
@AutoConfigureMockMvc //автоконфигурация MockMvc
class DictionaryApiTest {
    @Autowired
    MockMvc mockMvc; //класс

    @Test
    @SneakyThrows
    void should_accept_request_from_yandex_alice_and_return_valid_response() {
        mockMvc.perform(
                        post("/api/v1/dictionary/yandex-alice-skill/") //POST-запрос по указанному пути
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("{" //содержимое запроса от Яндекс.Алисы
                                        + "  \"meta\": {"
                                        + "  },"
                                        + "  \"request\": {"
                                        + "    \"command\": \"Алиса запусти навык переводчик\","
                                        + "    \"original_utterance\": \"алиса запусти навык переводчик\","
                                        + "    \"type\": \"SimpleUtterance\","
                                        + "    \"payload\": {},"
                                        + "    \"nlu\": {"
                                        + "      \"tokens\": ["
                                        + "        \"алиса\","
                                        + "        \"запусти\","
                                        + "        \"навык\","
                                        + "        \"переводчик\""
                                        + "      ]"
                                        + "    }"
                                        + "  },"
                                        + "  \"session\": {"
                                        + "    \"message_id\": 0,"
                                        + "    \"session_id\": \"2eac4854-fce721f3-b845abba-20d60\","
                                        + "    \"user\": {"
                                        + "      \"user_id\": \"47C73714B580EE\","
                                        + "      \"access_token\": \"AgAAAAAB4vpbAAApoR1oaCd5yR6eiXSHqOGT8dT\""
                                        + "    },"
                                        + "    \"application\": {"
                                        + "      \"application_id\": \"47C73714B580ED\""
                                        + "    },"
                                        + "    \"new\": true"
                                        + "  },"
                                        + "  \"version\": \"1.0\""
                                        + "}")
                )
                .andExpect(status().isOk()) //проверить, что статус ответа - HTTP 200 OK
                .andExpect(content().json("{" //проверка содержимого ответа
                        + "  \"response\": {"
                        + "    \"text\": \"Привет! "
                        + "Я помогу вам выучить английские слова.\","
                        + "    \"end_session\": false"
                        + "  },"
                        + "  \"version\": \"1.0\""
                        + "}"));
    }
}