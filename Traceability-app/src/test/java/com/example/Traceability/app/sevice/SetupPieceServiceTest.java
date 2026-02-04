package com.example.Traceability.app.sevice;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.*;

import com.example.Traceability.app.config.AddItemResponse;
import com.example.Traceability.app.db.dto.BadPieceDto;
import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.db.entity.SetupPiece;
import com.example.Traceability.app.db.entity.enums.ReasonForToolReplacement;
import com.example.Traceability.app.repository.SetupPieceRepository;
import com.example.Traceability.app.service.SessionService;
import com.example.Traceability.app.service.SetupPieceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SetupPieceServiceTest {

    @Mock
    SessionService sessionService;

    @Mock
    SetupPieceRepository setupPieceRepository;

    @InjectMocks
    SetupPieceService setupPieceService;

    @Mock
    Clock clock;


    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(
                LocalDateTime.of(2026,2,4,15,0)
                        .toInstant(ZoneOffset.UTC),
                ZoneOffset.UTC);
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());


    }

    @Test
    void test_saveSetupDTO(){


        BadPieceDto badPieceDto = new BadPieceDto();
        badPieceDto.setSessionId(1L);
        badPieceDto.setProblem(ReasonForToolReplacement.TOOL_BROKEN.name());
        badPieceDto.setQrCode("QR999");

        Session s = new Session();
        when(sessionService.getSession(1L)).thenReturn(s);

        AddItemResponse result = setupPieceService.saveSetupDTO(badPieceDto);

        assertTrue(result.isSaved());
        assertEquals("Setup saved successfully",result.getMessage());

        verify(setupPieceRepository, times(1)).save(any(SetupPiece.class));
        assertEquals(LocalDateTime.of(2026, 2, 4, 15, 0), s.getEndOfSession());

    }

    @Test
    void test_saveSetupDTO_invalidEnum() {

        BadPieceDto dto = new BadPieceDto();
        dto.setSessionId(1L);
        dto.setQrCode("QR999");
        dto.setComment("Tool broken");
        dto.setProblem("WRONG_VALUE");

        Session session = new Session();
        when(sessionService.getSession(1L)).thenReturn(session);

        assertThrows(IllegalArgumentException.class, () ->
                setupPieceService.saveSetupDTO(dto)
        );
    }
}
