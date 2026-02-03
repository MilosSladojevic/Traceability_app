package com.example.Traceability.app.service;

import com.example.Traceability.app.db.dto.CheckQrCodeResponse;
import com.example.Traceability.app.db.dto.QrCodeCheckDto;
import com.example.Traceability.app.db.entity.SetupPiece;
import com.example.Traceability.app.repository.SetupPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SetupPieceService {

    private SetupPieceRepository setupPieceRepository;

    @Autowired
    public SetupPieceService(SetupPieceRepository setupPieceRepository) {
        this.setupPieceRepository = setupPieceRepository;
    }


    public CheckQrCodeResponse checkQrCode(QrCodeCheckDto qrCodeCheckDto) {
        Optional<SetupPiece> setupOpt = setupPieceRepository.findByQrCode(qrCodeCheckDto.getQrCode());
        boolean exists = setupOpt.isPresent();

        if (exists){
            return new CheckQrCodeResponse(true,"Already in Database");
        }
        return new CheckQrCodeResponse(false,"Free to use");
    }
}
