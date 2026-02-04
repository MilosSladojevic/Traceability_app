package com.example.Traceability.app.service;

import com.example.Traceability.app.config.AddItemResponse;
import com.example.Traceability.app.db.dto.BadPieceDto;
import com.example.Traceability.app.config.CheckQrCodeResponse;
import com.example.Traceability.app.db.dto.QrCodeCheckDto;
import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.db.entity.SetupPiece;
import com.example.Traceability.app.db.entity.enums.ReasonForToolReplacement;
import com.example.Traceability.app.repository.SetupPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SetupPieceService {

    private SetupPieceRepository setupPieceRepository;
    private SessionService sessionService;
    private Clock clock;


    public SetupPieceService(SetupPieceRepository setupPieceRepository, SessionService sessionService, Clock clock) {
        this.setupPieceRepository = setupPieceRepository;
        this.sessionService = sessionService;
        this.clock = clock;
    }


    public CheckQrCodeResponse checkQrCode(QrCodeCheckDto qrCodeCheckDto) {
        Optional<SetupPiece> setupOpt = setupPieceRepository.findByQrCode(qrCodeCheckDto.getQrCode());
        boolean exists = setupOpt.isPresent();

        if (exists){
            return new CheckQrCodeResponse(true,"Already in Database");
        }
        return new CheckQrCodeResponse(false,"Free to use");
    }

    @Transactional
    public AddItemResponse saveSetupDTO(BadPieceDto badPieceDto) {
        Session session = sessionService.getSession(badPieceDto.getSessionId());
        session.setEndOfSession(LocalDateTime.now(clock));
        ReasonForToolReplacement problem = ReasonForToolReplacement.valueOf(badPieceDto.getProblem());
        SetupPiece setupPiece = new SetupPiece();

        setupPiece.setSession(session);
        setupPiece.setQrCode(badPieceDto.getQrCode());
        setupPiece.setProductionTime(LocalDateTime.now(clock));
        setupPiece.setComment(badPieceDto.getComment());
        setupPiece.setReason(problem);
        setupPieceRepository.save(setupPiece);
        return new AddItemResponse(true,"Setup saved successfully");
    }
}
