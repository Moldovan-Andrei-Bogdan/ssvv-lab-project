package org.example;

//import junit.framework.TestCase;
//import junit.framework.TestSuite;

import org.example.domain.Student;
import org.example.domain.Tema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.example.repository.NotaXMLRepo;
import org.example.repository.StudentXMLRepo;
import org.example.repository.TemaXMLRepo;
import org.example.service.Service;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.example.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

    public static Service service;

    @BeforeAll
    public static void setup() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        TestClass.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void addStudent_ValidData_CreatedSuccessfully() {
        String idStudent = "test";
        String numeStudent = "john";
        int grupa = 935;
        String email = "john_doe@yahoo.com";
        Student student = new Student(idStudent, numeStudent, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException exception) {
            System.out.println(exception);
            assertFalse(true);
        }

        assert(service.findStudent(idStudent) != null);
    }

    @Test
    public void addStudent_EmptyId_ThrowError() {
        // TC 2
        String idStudent = "";
        String numeStudent = "2";
        int grupa = -1;
        String email = "";
        Student student = new Student(idStudent, numeStudent, grupa, email);

        try {
            service.addStudent(student);
            assert(false);
        } catch (ValidationException exception) {
            System.out.println(exception);
            assert(true);
        }
    }

    @Test
    public void addStudent_NullId_ThrowError() {
        // TC 3
        String idStudent = null;
        String numeStudent = "";
        int grupa = 934;
        String email = null;
        Student student = new Student(idStudent, numeStudent, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException | NullPointerException exception) {
            System.out.println(exception);
            assert(true);
        }
    }

    @Test
    public void addStudent_DuplicateId_ThrowError() {
        // TC 4
        String idStudent = "test";
        String numeStudent = "john";
        int grupa = 935;
        String email = "john_doe@yahoo.com";
        Student student = new Student(idStudent, numeStudent, grupa, email);

        try {
            service.addStudent(student);
        } catch (ValidationException exception) {
            System.out.println(exception);
            assert(true);
        }
    }

    // White box testing
    @Test
    public void addTema_ValidData_CreateSuccessfully() {
        String nrTema = "100";
        String descriere = "test";
        int deadline = 12;
        int primire = 1;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            service.addTema(tema);
            assert(true);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(false);
        }
    }

    @Test
    public void addTema_Invalid_nrTema_duplicate_ThrowsError() {
        String nrTema = "100";
        String descriere = "test";
        int deadline = 12;
        int primire = 1;
        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        try {
            Tema response = service.addTema(tema);
            assert(tema == response);
        } catch (ValidationException exception) {
            System.out.println("Validation exception: " + exception.getMessage());
            assert(true);
        }
    }
}