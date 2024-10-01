package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Configuration
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {
    TestServiceImpl testService;
    @Mock
    StreamsIOService streamsIOService;
    @Mock
    CsvQuestionDao questionDao;

    @BeforeEach
    void setUp() {
        testService = new TestServiceImpl(streamsIOService, questionDao);
    }

    @Test
    void executeTestFor() {
        // Arrange
        Student student = new Student("Peter", "Petrov");

        List<Answer> answers1 = List.of(
                new Answer("Science doesn't know this yet", true),
                new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                new Answer("Absolutely not", false)
        );
        List<Answer> answers2 = List.of(
                new Answer("ClassLoader#getResource#getFile + FileReader", false),
                new Answer("ClassLoader#getResourceAsStream or ClassPathResource#getInputStream", true),
                new Answer("Wingardium Leviosa", false)
        );
        List<Question> questions = List.of(
                new Question("Is there life on Mars?", answers1),
                new Question("How should resources be loaded form jar in Java?", answers2)
        );

        when(questionDao.findAll()).thenReturn(questions);
        when(streamsIOService.readIntForRange(eq(0), eq(3), anyString())).thenReturn(1);

        //Act
        var result = testService.executeTestFor(student);

        //Assert
        assertEquals(student, result.getStudent());
        assertEquals(result.getRightAnswersCount(),1);
    }
}