package com.example.Traceability.app.controller;

import com.example.Traceability.app.db.dto.PieceDto;
import com.example.Traceability.app.service.PieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/piece")
public class PieceController {

    private PieceService pieceService;
    @Autowired
    public PieceController(PieceService pieceService) {
        this.pieceService = pieceService;
    }


    @PostMapping("/add")
    public ResponseEntity<String> addPiece(@RequestBody PieceDto pieceDto){
        try {
            pieceService.save(pieceDto);

            return ResponseEntity.ok("Saved successfully");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during saving");
        }
    }
}
