package com.example.Traceability.app.controller;

import com.example.Traceability.app.db.dto.SessionViewDto;
import com.example.Traceability.app.db.dto.TicketIdNumberDto;
import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/session")
public class SessionController {

    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    @GetMapping("/{sessionId}")
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

    @GetMapping("/general")
    public String showDailyTable(@RequestParam(required = false)String code, Model model){
        List<SessionViewDto> sessions ;
        if (code == null || code.isBlank()){
            sessions=sessionService.getAllForView();
        }else {

            sessions = sessionService.getAllWhitTicketNumber(code);
        }

        model.addAttribute("sessions",sessions);
        model.addAttribute("code",code);

        return "general-table";

    }




}
