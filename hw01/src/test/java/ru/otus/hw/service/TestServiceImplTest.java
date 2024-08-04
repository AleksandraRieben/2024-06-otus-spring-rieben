package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

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
        List<Answer> answers1 = List.of(
                new Answer("Science doesn't know this yet", true),
                new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                new Answer("Absolutely not", false)
        );
        List<Answer> answers2 = List.of(
                new Answer("ClassLoader#getResourceAsStream or ClassPathResource#getInputStream", true),
                new Answer("ClassLoader#getResource#getFile + FileReader", false),
                new Answer("Wingardium Leviosa", false)
        );
        List<Question> questions = List.of(
                new Question("Is there life on Mars?", answers1),
                new Question("How should resources be loaded form jar in Java?", answers2)
        );

        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readString("")).thenReturn("1").thenReturn("1");

        //Act
        testService.executeTest();

        //Assert
        verify(ioService).printFormattedLine("Please answer the questions below:");
    }

    @Test
    void printQuestionsAndAnswers() {
        // Arrange
        List<Answer> answers = List.of(
                new Answer("Correct answer", true),
                new Answer("Wrong answer", false),
                new Answer("Another wrong answer", false)
        );
        Question question = new Question("Sample question?", answers);

        when(questionDao.findAll()).thenReturn(List.of(question));
        when(ioService.readString("")).thenReturn("1");

        // Act
        testService.executeTest();

        // Assert
        verify(ioService).printFormattedLine("Sample question?");
        verify(ioService).printFormattedLine("1. Correct answer");
        verify(ioService).printFormattedLine("2. Wrong answer");
        verify(ioService).printFormattedLine("3. Another wrong answer");
        verify(ioService).printLine("Correct! The answer is: Correct answer");
    }
}