package com.example.Traceability.app.service;

import com.example.Traceability.app.config.AddItemResponse;
import com.example.Traceability.app.db.dto.PieceDto;
import com.example.Traceability.app.db.entity.Piece;
import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.repository.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PieceService {

    private SessionService sessionService;
    private PieceRepository pieceRepository;

    @Autowired
    public PieceService(SessionService sessionService, PieceRepository pieceRepository) {
        this.sessionService = sessionService;
        this.pieceRepository = pieceRepository;
    }

    @Transactional
    public AddItemResponse saveDto(PieceDto pieceDto) {
        Session session = sessionService.getSession(pieceDto.getSessionId());
        session.setEndOfSession(LocalDateTime.now());
        try {
            Piece piece = new Piece();
            piece.setSession(session);
            piece.setQrCode(pieceDto.getQrCode());
            piece.setProductionTime(LocalDateTime.now());
            pieceRepository.save(piece);

            return new AddItemResponse(true,"Piece saved successfully");
        }catch (DataIntegrityViolationException e){
            return new AddItemResponse(false,"Piece with QR code: "+pieceDto.getQrCode()+" already exist!");
        }catch (Exception e){
            return new AddItemResponse(false,"Unexpected error");
        }


    }
}
