package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class StudentServiceImplTest {

    private IOService ioService;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        ioService = mock(IOService.class);
        studentService = new StudentServiceImpl(ioService);
    }

    @Test
    void createStudent() {
        // Arrange
        // Act
        studentService.createStudent();
        // Assert
//        Assertions.assertEquals();
    }
}