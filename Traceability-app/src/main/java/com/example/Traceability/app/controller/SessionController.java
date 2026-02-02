package com.example.Traceability.app.controller;

import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {

    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    @GetMapping("/session/{sessionId}")
    public String getSession(@PathVariable Long sessionId, Model model){
        Session session = sessionService.getSession(sessionId);

        if (session == null){
            return "redirect:/";
        }

        model.addAttribute("sessionId",session.getId());
        model.addAttribute("inboxNumber",session.getNoInbox());
        model.addAttribute("outboxNumber",session.getNoOutbox());
        model.addAttribute("referenceNumber",session.getReference().getReferenceNumber());
        model.addAttribute("customer",session.getReference().getClient());

        return "log-measuring";

    }
}
