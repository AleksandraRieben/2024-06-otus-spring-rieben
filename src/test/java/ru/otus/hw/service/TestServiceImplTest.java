package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {
    private IOService ioService;
    private QuestionDao questionDao;
    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        ioService = mock(IOService.class);
        questionDao = mock(QuestionDao.class);
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    void executeTest() {
        // Arrange
        Question question = new Question("How should resources be loaded form jar in Java?",
                Arrays.asList(
                        new Answer("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream", true),
                        new Answer("ClassLoader#geResource#getFile + FileReader", false),
                        new Answer("Wingardium Leviosa", false)
                )
        );
        List<Question> questions = List.of(question);

        when(questionDao.findAll()).thenReturn(questions);

        // Act
        testService.executeTest();

        // Assert
        verify(ioService).printFormattedLine("Please answer the questions below:%n");
        verify(ioService).printFormattedLine("How should resources be loaded form jar in Java?");
        verify(ioService).printFormattedLine("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream");
        verify(ioService).printFormattedLine("ClassLoader#geResource#getFile + FileReader");
        verify(ioService).printFormattedLine("Wingardium Leviosa");
    }
}