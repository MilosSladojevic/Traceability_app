package com.example.Traceability.app.controller;

import com.example.Traceability.app.config.AddItemResponse;
import com.example.Traceability.app.config.CheckQrCodeResponse;
import com.example.Traceability.app.db.dto.*;
import com.example.Traceability.app.service.*;
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
    private RfPieceService rfPieceService;
    private ControlledPieceService controlledPieceService;


    @Autowired
    public PieceController(PieceService pieceService, SessionService sessionService, RusPieceService rusPieceService, SetupPieceService setupService, RfPieceService rfPieceService, ControlledPieceService controlledPieceService) {
        this.pieceService = pieceService;
        this.sessionService = sessionService;
        this.rusPieceService = rusPieceService;
        this.setupService = setupService;
        this.rfPieceService = rfPieceService;
        this.controlledPieceService = controlledPieceService;
    }


    @PostMapping("/add-ok")
    public AddItemResponse addPiece(@RequestBody PieceDto pieceDto){

       return pieceService.saveDto(pieceDto);


    }

    @PostMapping("/rus/qr-check")
    public CheckQrCodeResponse checkRusQrCode(@RequestBody QrCodeCheckDto qrCodeCheckDto){
        return rusPieceService.checkQrCode(qrCodeCheckDto);
    }

    @PostMapping("/rus/save-data")

    public AddItemResponse saveRusPiece(@RequestBody BadPieceDto badPieceDto){

        return rusPieceService.saveRusDTO(badPieceDto);
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

    @PostMapping("/setup/save-data")
    public AddItemResponse saveSetupPiece(@RequestBody BadPieceDto badPieceDto) {

        return setupService.saveSetupDTO(badPieceDto);
    }

    @PostMapping("/rf/qr-check")
    public CheckQrCodeResponse checkRfQrCode(@RequestBody QrCodeCheckDto qrCodeCheckDto){
        return rfPieceService.checkQrCode(qrCodeCheckDto);
    }

    @PostMapping("/rf/save-data")
    public AddItemResponse saveRfPiece(@RequestBody BadPieceDto badPieceDto) {

        return rfPieceService.saveSetupDTO(badPieceDto);
    }

    @PostMapping("/add-kk")
    public AddItemResponse addControlledPiece(@RequestBody PieceDto pieceDto) {

        return controlledPieceService.saveDto(pieceDto);

    }


}
