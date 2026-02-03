package com.example.Traceability.app.service;

import com.example.Traceability.app.db.dto.AddItemResponse;
import com.example.Traceability.app.db.dto.BadPieceDto;
import com.example.Traceability.app.db.dto.CheckQrCodeResponse;
import com.example.Traceability.app.db.dto.QrCodeCheckDto;
import com.example.Traceability.app.db.entity.RfPiece;
import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.db.entity.enums.RfProblems;
import com.example.Traceability.app.repository.RfPieceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RfPieceService {

    private SessionService sessionService;
    private RfPieceRepository rfPieceRepository;

    public RfPieceService(SessionService sessionService, RfPieceRepository rfPieceRepository) {
        this.sessionService = sessionService;
        this.rfPieceRepository = rfPieceRepository;
    }


    public CheckQrCodeResponse checkQrCode(QrCodeCheckDto qrCodeCheckDto) {
        Optional<RfPiece> setupOpt = rfPieceRepository.findByQrCode(qrCodeCheckDto.getQrCode());
        boolean exists = setupOpt.isPresent();

        if (exists){
            return new CheckQrCodeResponse(true,"Already in Database");
        }
        return new CheckQrCodeResponse(false,"Free to use");

    }

    @Transactional
    public AddItemResponse saveSetupDTO(BadPieceDto badPieceDto) {
        Session session = sessionService.getSession(badPieceDto.getSessionId());
        session.setEndOfSession(LocalDateTime.now());
        RfProblems problem = RfProblems.valueOf(badPieceDto.getProblem());
        RfPiece rfPiece = new RfPiece();

        rfPiece.setSession(session);
        rfPiece.setQrCode(badPieceDto.getQrCode());
        rfPiece.setProductionTime(LocalDateTime.now());
        rfPiece.setProblems(problem);
        rfPiece.setComment(badPieceDto.getComment());
        rfPieceRepository.save(rfPiece);

        return new AddItemResponse(true,"Rf saved successfully");

    }
}
