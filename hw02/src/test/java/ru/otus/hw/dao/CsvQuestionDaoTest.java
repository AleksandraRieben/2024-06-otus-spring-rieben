//package ru.otus.hw.dao;
//
//import org.junit.jupiter.api.Test;
//import ru.otus.hw.config.TestFileNameProvider;
//import ru.otus.hw.domain.Question;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.mock;
//
//class CsvQuestionDaoTest {
//    @Test
//    void findAll() {
//        var testFileNameProvider = mock(TestFileNameProvider.class);
//        var repository = new CsvQuestionDao(testFileNameProvider);
//        List<Question> questions = repository.findAll();
//        assertNotNull(repository);
//        assertEquals("Is there life on Mars?", questions.get(0).text());
//    }
//}