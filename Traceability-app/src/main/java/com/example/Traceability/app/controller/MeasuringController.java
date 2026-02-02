package com.example.Traceability.app.controller;

import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.repository.SessionRepository;
import com.example.Traceability.app.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/measuring")
public class MeasuringController {

    private SessionService sessionService;


    @Autowired
    public MeasuringController(SessionService sessionService) {
        this.sessionService = sessionService;

    }


    @GetMapping("/daily")
    public String showDailyTable(Model model){
//        List<Session> sessions = sessionService.getAllSessionsOfDay();

        model.addAttribute("sessions",sessionService.getAllForView());
        return "daily-table";
    }

    @PostMapping("/daily/{sessionId}")
    public String postEndSession(@PathVariable Long sessionId){
        Session session = sessionService.getSession(sessionId);
        if (session == null){
            return "/";
        }

        session.setEndOfSession(LocalDateTime.now());
        sessionService.sendToRepo(session);

        return "redirect:/measuring/daily";
    }

    @GetMapping("/log")
    public String showMeasuringLog(@RequestParam Long sessionId, Model model){
        model.addAttribute("sessionId",sessionId);
        return "log-measuring";
    }


    @PostMapping("/box-info")
    public String startSession(@RequestParam String inbox ,@RequestParam String outbox){

        Session session = sessionService.startSession(inbox,outbox);


        return "redirect:/session/"+ session.getId();

    }


    @GetMapping("/box-info")
    public String showBoxInfo(){
        return "container-data";
    }

}
