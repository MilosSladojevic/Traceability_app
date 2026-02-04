package com.example.Traceability.app.service;

import com.example.Traceability.app.db.dto.SessionViewDto;
import com.example.Traceability.app.db.entity.Employee;
import com.example.Traceability.app.db.entity.Reference;
import com.example.Traceability.app.db.entity.Session;
import com.example.Traceability.app.repository.EmployeeRepository;
import com.example.Traceability.app.repository.ReferenceRepository;
import com.example.Traceability.app.repository.SessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SessionService {


    private  SessionRepository sessionRepository;
   private ReferenceRepository referenceRepository;
   private EmployeeService employeeService;

   @Autowired
   public SessionService(SessionRepository sessionRepository, ReferenceRepository referenceRepository, EmployeeService employeeService) {
       this.sessionRepository = sessionRepository;
       this.referenceRepository = referenceRepository;
       this.employeeService = employeeService;
   }

   public Session startSession(String inbox , String outbox){
       Session session = new Session();
       String noRef;
       if (inbox.length()>9){
           noRef=inbox.substring(10);
       }else {
           noRef=outbox.substring(10);
       }
      Reference reference = referenceRepository.findByReferenceNumber(noRef);

       Employee employee = employeeService.findByUserName(employeeService.getLoggedEmployeeUsername());

       session.setReference(reference);
       String shortInbox = inbox.substring(0,9);
       session.setNoInbox(shortInbox);
       String shortOutBox = outbox.substring(0,9);
       session.setNoOutbox(shortOutBox);
       session.setStartOfSession(LocalDateTime.now());
       session.setEndOfSession(LocalDateTime.now());
       session.setEmployee(employee);
       sessionRepository.save(session);

       return session;


   }

    public Session getSession(Long sessionId) {

       return sessionRepository.findById(sessionId).orElse(null);
    }

    public void sendToRepo(Session session) {
       sessionRepository.save(session);
    }

//    public List<Session> getAllSessionsOfDay(){
//        LocalDate today = LocalDate.now();
//        LocalDateTime startOfDay = today.atStartOfDay();
//        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
//
//        return sessionRepository.findByStartOfSessionLessThanEqualAndEndOfSessionIsNullOrEndOfSessionGreaterThanEqual(startOfDay,endOfDay);
//    }

    @Transactional
    public List<SessionViewDto> getAllTodayForView(){
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

       return mappingViveDto(sessionRepository.findSessionsForDay(startOfDay,endOfDay));
    }

    @Transactional
    public List<SessionViewDto> getAllForView(){
        return mappingViveDto(sessionRepository.findTop100ByOrderByIdDesc());

    }


    public List<SessionViewDto> getAllWhitTicketNumber(String ticketId) {
       return mappingViveDto(sessionRepository.findByNoOutbox(ticketId));
//


    }

    private List<SessionViewDto> mappingViveDto(List<Session> sessions){
      return sessions.stream().map(s->new SessionViewDto(
                s.getStartOfSession(),
                s.getEndOfSession(),
                s.getNoOutbox(),
                s.getOkPiece().size(),
                s.getControlledPieces().size(),
                s.getSetupPieces().size(),
                s.getRusPieces().size(),
                s.getRfPieces().size(),
                s.getEmployee().getFirstName()+" "+s.getEmployee().getLastName()
       )).toList();


    }

}
