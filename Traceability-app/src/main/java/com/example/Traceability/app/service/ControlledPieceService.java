package com.example.Traceability.app.service;

import com.example.Traceability.app.db.dto.AddItemResponse;
import com.example.Traceability.app.db.dto.PieceDto;
import com.example.Traceability.app.db.entity.ControlledPiece;
import com.example.Traceability.app.db.entity.Piece;
import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.repository.ControlledPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ControlledPieceService {

    private ControlledPieceRepository controlledPieceRepository;
    private SessionService sessionService;

    @Autowired
    public ControlledPieceService(ControlledPieceRepository controlledPieceRepository, SessionService sessionService) {
        this.controlledPieceRepository = controlledPieceRepository;
        this.sessionService = sessionService;
    }

    @Transactional
    public AddItemResponse saveDto(PieceDto pieceDto) {
        Session session = sessionService.getSession(pieceDto.getSessionId());
        session.setEndOfSession(LocalDateTime.now());
        try {
            ControlledPiece controlledPiece = new ControlledPiece();
            controlledPiece.setSession(session);
            controlledPiece.setQrCode(pieceDto.getQrCode());
            controlledPiece.setProductionTime(LocalDateTime.now());
            controlledPieceRepository.save(controlledPiece);

            return new AddItemResponse(true,"Controlled piece saved successfully");
        }catch (DataIntegrityViolationException e){
            return new AddItemResponse(false,"Controlled piece with QR code: "+pieceDto.getQrCode()+" already exist!");
        }catch (Exception e){
            return new AddItemResponse(false,"Unexpected error");
        }
    }
}
