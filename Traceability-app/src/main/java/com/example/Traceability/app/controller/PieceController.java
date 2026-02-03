package com.example.Traceability.app.controller;

import com.example.Traceability.app.db.dto.*;
import com.example.Traceability.app.service.PieceService;
import com.example.Traceability.app.service.RusPieceService;
import com.example.Traceability.app.service.SessionService;
import com.example.Traceability.app.service.SetupPieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/piece")
public class PieceController {

    private PieceService pieceService;
    private SessionService sessionService;
    private RusPieceService rusPieceService;
    private SetupPieceService setupService;


    @Autowired
    public PieceController(PieceService pieceService, SessionService sessionService, RusPieceService rusPieceService, SetupPieceService setupService) {
        this.pieceService = pieceService;
        this.sessionService = sessionService;
        this.rusPieceService = rusPieceService;
        this.setupService = setupService;
    }


    @PostMapping("/add-ok")
    public AddItemResponse addPiece(@RequestBody PieceDto pieceDto){

       return pieceService.saveDto(pieceDto);
//        Session session = sessionService.getSession(pieceDto.getSessionId());
//        try {
//            Piece piece = new Piece();
//            piece.setSession(session);
//            piece.setQrCode(pieceDto.getQrCode());
//            piece.setProductionTime(LocalDateTime.now());
//            pieceRepository.save(piece);
//
//            return new AddItemResponse(true,"Piece saved successfully");
//        }catch (DataIntegrityViolationException e){
//            return new AddItemResponse(false,"Piece with QR code: "+pieceDto.getQrCode()+" already exist!");
//        }catch (Exception e){
//            return new AddItemResponse(false,"Unexpected error");
//        }

    }

    @PostMapping("/rus/qr-check")
    public CheckQrCodeResponse checkRusQrCode(@RequestBody QrCodeCheckDto qrCodeCheckDto){
        return rusPieceService.checkQrCode(qrCodeCheckDto);
//        Optional<RusPiece> rus = rusRepository.findByQrCode(rusQrCheck.getQrCode());
//        boolean exists = rus.isPresent();
//        if (exists){
//            return new CheckQrCodeResponse(true,"Already in Database");
//        }
//        return new CheckQrCodeResponse(false,"Free to use");
    }

    @PostMapping("/rus/save-data")

    public AddItemResponse saveRusP(@RequestBody RusDto rusDto){

        return rusPieceService.saveRusDTO(rusDto);
//        Session session = sessionService.getSession(rusDto.getSessionId());
//        RusProblems problem = RusProblems.valueOf(rusDto.getProblem());
//        RusPiece rusPiece = new RusPiece();
//
//        rusPiece.setSession(session);
//        rusPiece.setQrCode(rusDto.getQrCode());
//        rusPiece.setProductionTime(LocalDateTime.now());
//        rusPiece.setProblem(problem);
//        rusPiece.setComment(rusDto.getComment());
//
//        rusRepository.save(rusPiece);
//
//        return new AddItemResponse(true,"Piece saved successfully");
    }

    @PostMapping("/setup/qr-check")
    public CheckQrCodeResponse checkSetupQrCode(@RequestBody QrCodeCheckDto qrCodeCheckDto){
        return setupService.checkQrCode(qrCodeCheckDto);
    }


}
