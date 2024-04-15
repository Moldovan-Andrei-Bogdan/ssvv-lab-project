package org.example;

import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepo;
import org.example.repository.StudentXMLRepo;
import org.example.repository.TemaXMLRepo;
import org.example.service.Service;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.example.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class TestBigBangClass {
    @Mock
    private StudentValidator studentValidator;

    @Mock
    private TemaValidator temaValidator;

    @Mock
    private NotaValidator notaValidator;

    @Mock
    private StudentXMLRepo studentXMLRepository;

    @Mock
    private TemaXMLRepo temaXMLRepository;

    @Mock
    private NotaXMLRepo notaXMLRepository;

    private Service service;

    @BeforeEach
    public void setup() {
        studentValidator = mock(StudentValidator.class);
        temaValidator = mock(TemaValidator.class);
        notaValidator = mock(NotaValidator.class);
        studentXMLRepository = mock(StudentXMLRepo.class);
        temaXMLRepository = mock(TemaXMLRepo.class);
        notaXMLRepository = mock(NotaXMLRepo.class);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @AfterEach
    public void tearDown() {
        studentValidator = null;
        temaValidator = null;
        notaValidator = null;
        studentXMLRepository = null;
        temaXMLRepository = null;
        notaXMLRepository = null;
        service = null;
    }

    @Test
    public void addStudent_Valid() {
        String studentId = "1";
        String nume = "TestStudentName";
        int grupa = 931;
        String email = "miguel@yahoo.com";

        Student student = new Student(studentId, nume, grupa, email);

        try {
            when(studentXMLRepository.save(student)).thenReturn(student);
            Student returnedStudent = service.addStudent(student);

            Assertions.assertEquals(student.getID(), returnedStudent.getID());
        } catch (ValidationException ve) {
            ve.printStackTrace();
            assert false;
        }
    }

    @Test
    public void addTema_Valid() {
        String nrTema = "1";
        String descriere = "descriere";
        int deadline = 20;
        int primire = 2;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        try {
            when(temaXMLRepository.save(tema)).thenReturn(tema);
            Tema returnedTema = service.addTema(tema);

            Assertions.assertEquals(tema.getID(), returnedTema.getID());
        } catch (ValidationException ve) {
            ve.printStackTrace();
            assert false;
        }
    }

    @Test
    public void addGrade_Valid() {
        String studentId = "1";
        String nume = "TestStudentName";
        int grupa = 931;
        String email = "miguel@yahoo.com";
        Student student = new Student(studentId, nume, grupa, email);

        String nrTema = "1";
        String descriere = "descriere";
        int deadline = 14;
        int primire = 2;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        service.addStudent(student);
        service.addTema(tema);

        try {
            String notaId = "nt1";
            double notaVal = 9.5;
            LocalDate date = LocalDate.of(2024, 4, 12);
            Nota nota = new Nota(notaId, studentId, nrTema, notaVal, date);

            when(notaXMLRepository.save(nota)).thenReturn(nota);
            when(temaXMLRepository.findOne(nrTema)).thenReturn(tema);
            when(studentXMLRepository.findOne(studentId)).thenReturn(student);

            double returnedNota = service.addNota(nota, "feedback");
            Assertions.assertEquals(nota.getNota(), returnedNota);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void addStudent_Valid_addTema_Valid_addNota_Valid() {
        String studentId = "1";
        String nume = "Miguel";
        int grupa = 931;
        String email = "miguel@yahoo.com";
        Student student = new Student(studentId, nume, grupa, email);

        String nrTema = "1";
        String descriere = "descriere";
        int deadline = 6;
        int primire = 2;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        String notaId = "nt1";
        double notaVal = 9.5;
        LocalDate date = LocalDate.of(2024, 4, 15);
        Nota nota = new Nota(notaId, studentId, nrTema, notaVal, date);

        try {
            doNothing().when(studentValidator).validate(student);
            when(studentXMLRepository.save(student)).thenReturn(student);

            doNothing().when(temaValidator).validate(tema);
            when(temaXMLRepository.save(tema)).thenReturn(tema);

            when(studentXMLRepository.findOne(studentId)).thenReturn(student);
            when(temaXMLRepository.findOne(nrTema)).thenReturn(tema);

            Student returnedStudent = service.addStudent(student);
            Assertions.assertEquals(returnedStudent.getID(), student.getID());

            Tema returnedTema = service.addTema(tema);
            Assertions.assertEquals(returnedTema.getID(), tema.getID());

            double returnedNota = service.addNota(nota, "feedback");
            Assertions.assertEquals(9.5, nota.getNota());
            Assertions.assertEquals(returnedNota, nota.getNota());
        } catch (ValidationException ve) {
            assert false;
            ve.printStackTrace();
        }
    }
}
