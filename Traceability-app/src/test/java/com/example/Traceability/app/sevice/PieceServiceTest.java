package com.example.Traceability.app.sevice;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.*;

import com.example.Traceability.app.config.AddItemResponse;
import com.example.Traceability.app.db.dto.PieceDto;
import com.example.Traceability.app.db.entity.Piece;
import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.repository.PieceRepository;
import com.example.Traceability.app.service.PieceService;
import com.example.Traceability.app.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

public class PieceServiceTest {


    @Mock
    private PieceRepository pieceRepository;


    private PieceService pieceService;

    @Mock
    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Clock fixedClock = Clock.fixed(
                LocalDateTime.of(2026, 2, 4, 12, 0)
                        .atZone(ZoneId.systemDefault())
                        .toInstant(),
                ZoneId.systemDefault()
        );

        pieceService=new PieceService(sessionService,pieceRepository,fixedClock);
    }

    @Test
    void saveDto_success() {
        PieceDto dto = new PieceDto();
        dto.setSessionId(1L);
        dto.setQrCode("QR123");

        Session session = new Session();
        when(sessionService.getSession(1L)).thenReturn(session);

        AddItemResponse response = pieceService.saveDto(dto);

        assertTrue(response.isSaved());
        assertEquals("Piece saved successfully", response.getMessage());

        verify(pieceRepository, times(1)).save(any(Piece.class));
        assertEquals(LocalDateTime.of(2026, 2, 4, 12, 0), session.getEndOfSession());
    }

    @Test
    void saveDto_duplicateQrCode() {
        PieceDto dto = new PieceDto();
        dto.setSessionId(1L);
        dto.setQrCode("QR123");

        Session session = new Session();
        when(sessionService.getSession(1L)).thenReturn(session);

        when(pieceRepository.save(any(Piece.class)))
                .thenThrow(DataIntegrityViolationException.class);

        AddItemResponse response = pieceService.saveDto(dto);

        assertFalse(response.isSaved());
        assertTrue(response.getMessage().contains("already exist"));
    }

    @Test
    void saveDto_unexpectedError() {
        PieceDto dto = new PieceDto();
        dto.setSessionId(1L);
        dto.setQrCode("QR123");

        Session session = new Session();
        when(sessionService.getSession(1L)).thenReturn(session);

        when(pieceRepository.save(any(Piece.class)))
                .thenThrow(RuntimeException.class);

        AddItemResponse response = pieceService.saveDto(dto);

        assertFalse(response.isSaved());
        assertEquals("Unexpected error", response.getMessage());
    }


}
