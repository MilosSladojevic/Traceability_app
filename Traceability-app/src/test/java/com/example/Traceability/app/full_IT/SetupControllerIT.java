package com.example.Traceability.app.full_IT;

import com.example.Traceability.app.db.dto.BadPieceDto;
import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.db.entity.enums.ReasonForToolReplacement;
import com.example.Traceability.app.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class SetupControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    void shouldSaveSetupPiece() throws Exception {


        Session session = new Session();
        session.setNoOutbox("23456");
        session.setNoInbox("23457");
        sessionRepository.save(session);


        BadPieceDto dto = new BadPieceDto();
        dto.setQrCode("RK232345");
        dto.setSessionId(session.getId());
        dto.setProblem(ReasonForToolReplacement.TOOL_BROKEN.name());
        dto.setComment("This is a comment");

        String jsonRequest = objectMapper.writeValueAsString(dto);


        mockMvc.perform(post("/piece/setup/save-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                .with(user("admin").password("password").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.saved").value(true))
                .andExpect(jsonPath("$.message").exists());
    }
}
