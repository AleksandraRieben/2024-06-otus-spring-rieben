package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CsvQuestionDaoTest {
    @Mock
    private TestFileNameProvider fileNameProvider;

    @InjectMocks
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_FileNotFound_ThrowsException() {
        // Arrange
        when(fileNameProvider.getTestFileName()).thenReturn("non_existing_file.csv");

        // Act & Assert
        assertThrows(QuestionReadException.class, () -> {
            csvQuestionDao.findAll();
        });
    }
}