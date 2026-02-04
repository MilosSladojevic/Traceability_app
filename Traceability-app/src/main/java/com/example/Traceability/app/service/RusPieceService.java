package com.example.Traceability.app.service;

import com.example.Traceability.app.config.AddItemResponse;
import com.example.Traceability.app.config.CheckQrCodeResponse;
import com.example.Traceability.app.db.dto.BadPieceDto;
import com.example.Traceability.app.db.dto.QrCodeCheckDto;
import com.example.Traceability.app.db.entity.RusPiece;
import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.db.entity.enums.RusProblems;
import com.example.Traceability.app.repository.RusPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RusPieceService {
    private RusPieceRepository rusRepository;
    private SessionService sessionService;

    @Autowired
    public RusPieceService(RusPieceRepository rusRepository, SessionService sessionService) {
        this.rusRepository = rusRepository;

        this.sessionService = sessionService;
    }


    @Transactional
    public AddItemResponse saveRusDTO(BadPieceDto badPieceDto){
        Session session = sessionService.getSession(badPieceDto.getSessionId());
        session.setEndOfSession(LocalDateTime.now());
        RusProblems problem = RusProblems.valueOf(badPieceDto.getProblem());
        RusPiece rusPiece = new RusPiece();

        rusPiece.setSession(session);
        rusPiece.setQrCode(badPieceDto.getQrCode());
        rusPiece.setProductionTime(LocalDateTime.now());
        rusPiece.setProblem(problem);
        rusPiece.setComment(badPieceDto.getComment());

        rusRepository.save(rusPiece);
        return new AddItemResponse(true,"Rus saved successfully");
    }

    public CheckQrCodeResponse checkQrCode(QrCodeCheckDto qrCodeCheckDto){
        Optional<RusPiece> rus = rusRepository.findByQrCode(qrCodeCheckDto.getQrCode());
        boolean exists = rus.isPresent();
        if (exists){
            return new CheckQrCodeResponse(true,"Already in Database");
        }
        return new CheckQrCodeResponse(false,"Free to use");
    }
}
