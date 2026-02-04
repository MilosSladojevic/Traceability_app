package com.example.Traceability.app.sevice;

import com.example.Traceability.app.db.dto.SessionViewDto;
import com.example.Traceability.app.db.entity.*;
import com.example.Traceability.app.repository.ReferenceRepository;
import com.example.Traceability.app.repository.SessionRepository;
import com.example.Traceability.app.service.EmployeeService;
import com.example.Traceability.app.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SessionServiceTest {

    @Mock
    SessionRepository sessionRepository;

    @Mock
    ReferenceRepository referenceRepository;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    SessionService sessionService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test_mappingViveDto() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");

        Session session = new Session();
        session.setStartOfSession(LocalDateTime.of(2026, 2, 4, 9, 0));
        session.setEndOfSession(LocalDateTime.of(2026, 2, 4, 17, 0));
        session.setNoOutbox("5");
        session.setOkPiece(Set.of(new Piece(),new Piece()));
        session.setControlledPieces(Set.of(new ControlledPiece()));
        session.setRusPieces(Set.of());
        session.setRfPieces(Set.of(new RfPiece()));
        session.setSetupPieces(Set.of(new  SetupPiece() , new SetupPiece(),new SetupPiece()));
        session.setEmployee(employee);

        List<Session> sessionList = List.of(session);

        Method method = SessionService.class.getDeclaredMethod("mappingViveDto", List.class);
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<SessionViewDto> result = (List<SessionViewDto>) method.invoke(sessionService, sessionList);

        // then
        assertEquals(1, result.size());
        SessionViewDto dto = result.get(0);
        assertEquals(LocalDateTime.of(2026, 2, 4, 9, 0), dto.getStartOfSession());
        assertEquals(LocalDateTime.of(2026, 2, 4, 17, 0), dto.getEndOfSession());
        assertEquals("5", dto.getNoOutbox());
        assertEquals(2, dto.getOkPiece());
        assertEquals(1, dto.getControlledPieces());
        assertEquals(3, dto.getSetupPieces());
        assertEquals(0, dto.getRusPieces());
        assertEquals(1, dto.getRfPieces());
        assertEquals("John Doe", dto.getEmployeeFullName());
    }

    @Test
    public void test_getAllWhitTicketNumber(){

       Employee employee = new Employee();

       employee.setFirstName("John");
       employee.setLastName("Doe");

       Session session = new Session();
       session.setStartOfSession(LocalDateTime.of(2026, 2, 4, 9, 0));
       session.setEndOfSession(LocalDateTime.of(2026, 2, 4, 17, 0));
       session.setNoOutbox("123");
       session.setOkPiece(Set.of(new Piece(),new Piece()));
       session.setControlledPieces(Set.of(new ControlledPiece()));
       session.setRusPieces(Set.of());
       session.setRfPieces(Set.of(new RfPiece()));
       session.setSetupPieces(Set.of(new  SetupPiece() , new SetupPiece(), new SetupPiece()));
       session.setEmployee(employee);

       when(sessionRepository.findByNoOutbox("123")).thenReturn(List.of(session));

       List<SessionViewDto> result =
               sessionService.getAllWhitTicketNumber("123");

       assertEquals(1, result.size());
       assertEquals("John Doe", result.get(0).getEmployeeFullName());
       assertEquals(2,result.get(0).getOkPiece());

       verify(sessionRepository, times(1)).findByNoOutbox("123");

    }

    @Test
    public void test_getAllForView(){

        Employee employee = new Employee();

        employee.setFirstName("John");
        employee.setLastName("Doe");

        Session session = new Session();
        session.setStartOfSession(LocalDateTime.of(2026, 2, 4, 9, 0));
        session.setEndOfSession(LocalDateTime.of(2026, 2, 4, 17, 0));
        session.setNoOutbox("123");
        session.setOkPiece(Set.of(new Piece(),new Piece()));
        session.setControlledPieces(Set.of(new ControlledPiece()));
        session.setRusPieces(Set.of());
        session.setRfPieces(Set.of(new RfPiece()));
        session.setSetupPieces(Set.of(new  SetupPiece() , new SetupPiece(), new SetupPiece()));
        session.setEmployee(employee);

        when(sessionRepository.findTop100ByOrderByIdDesc()).thenReturn(List.of(session));

        List<SessionViewDto> result =
                sessionService.getAllForView();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getEmployeeFullName());
        assertEquals(2,result.get(0).getOkPiece());

        verify(sessionRepository, times(1)).findTop100ByOrderByIdDesc();

    }

    @Test
    public void test_getAllTodayForView(){

        Clock fixedClock = Clock.fixed(
                LocalDateTime.of(2026,2,4,10,0)
                        .toInstant(ZoneOffset.UTC),
                ZoneOffset.UTC);

        sessionService= new SessionService(sessionRepository,referenceRepository,employeeService,fixedClock);

        LocalDate today = LocalDate.of(2026, 2, 4);
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        Employee employee = new Employee();

        employee.setFirstName("John");
        employee.setLastName("Doe");

        Session session = new Session();
        session.setStartOfSession(LocalDateTime.of(2026, 2, 4, 9, 0));
        session.setEndOfSession(LocalDateTime.of(2026, 2, 4, 17, 0));
        session.setNoOutbox("123");
        session.setOkPiece(Set.of(new Piece(),new Piece()));
        session.setControlledPieces(Set.of(new ControlledPiece()));
        session.setRusPieces(Set.of());
        session.setRfPieces(Set.of(new RfPiece()));
        session.setSetupPieces(Set.of(new  SetupPiece() , new SetupPiece(), new SetupPiece()));
        session.setEmployee(employee);

        when(sessionRepository.findSessionsForDay(startOfDay,endOfDay)).thenReturn(List.of(session));

        List<SessionViewDto> result =
                sessionService.getAllTodayForView();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getEmployeeFullName());
        assertEquals(2,result.get(0).getOkPiece());

        verify(sessionRepository, times(1)).findSessionsForDay(startOfDay,endOfDay);

    }

    @Test
    void test_sendToRepo(){

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");

        Session session = new Session();
        session.setStartOfSession(LocalDateTime.of(2026, 2, 4, 9, 0));
        session.setEndOfSession(LocalDateTime.of(2026, 2, 4, 17, 0));
        session.setNoOutbox("123");
        session.setOkPiece(Set.of(new Piece(),new Piece()));
        session.setControlledPieces(Set.of(new ControlledPiece()));
        session.setRusPieces(Set.of());
        session.setRfPieces(Set.of(new RfPiece()));
        session.setSetupPieces(Set.of(new  SetupPiece() , new SetupPiece(), new SetupPiece()));
        session.setEmployee(employee);

        sessionService.sendToRepo(session);

        verify(sessionRepository, times(1)).save(session);



    }

    @Test
    void test_getSession(){
        Long sessionId = 1L;

        Session session = new Session();
        session.setId(sessionId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

       Session result= sessionService.getSession(sessionId);

       assertNotNull(result);
       assertEquals(sessionId,result.getId());

        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    void test_startSession(){

        Clock fixedClock = Clock.fixed(
                LocalDateTime.of(2026,2,4,10,0)
                        .toInstant(ZoneOffset.UTC),
                ZoneOffset.UTC);

        sessionService= new SessionService(sessionRepository,referenceRepository,employeeService,fixedClock);

        String inbox = "ABCDEFGHIJ12345";
        String outbox = "KLMNOPQRST67890";

        Reference reference = new Reference();
        reference.setId(1L);
        when(referenceRepository.findByReferenceNumber("12345")).thenReturn(reference);

        Employee employee = new Employee();
        employee.setId(2L);
        when(employeeService.getLoggedEmployeeUsername()).thenReturn("john.doe");
        when(employeeService.findByUserName("john.doe")).thenReturn(employee);

        Session result = sessionService.startSession(inbox, outbox);

        assertNotNull(result);
        assertEquals(reference,result.getReference());
        assertEquals(employee,result.getEmployee());
        assertEquals("ABCDEFGHI",result.getNoInbox());
        assertEquals("KLMNOPQRS",result.getNoOutbox());
        assertEquals(LocalDateTime.of(2026,2,4,10,0),result.getStartOfSession());
        assertEquals(LocalDateTime.of(2026,2,4,10,0),result.getEndOfSession());

        verify(sessionRepository,times(1)).save(result);

        verify(referenceRepository,times(1)).findByReferenceNumber("12345");
        verify(employeeService,times(1)).findByUserName("john.doe");
        verify(employeeService,times(1)).getLoggedEmployeeUsername();

    }









}
