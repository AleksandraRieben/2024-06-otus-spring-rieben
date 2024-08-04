package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CsvQuestionDaoTest {

    private static final String FILE_NAME = "questions.csv";

    @Test
    void findAll() {
        var testFileNameProvider = new AppProperties(FILE_NAME);
        var repository = new CsvQuestionDao(testFileNameProvider);
        List<Question> questions = repository.findAll();
        assertNotNull(repository);
        assertEquals("Is there life on Mars?", questions.get(0).text());
    }
}