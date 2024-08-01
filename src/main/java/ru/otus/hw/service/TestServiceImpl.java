package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;
    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        printQuestionsAndAnswers();
    }

    private void printQuestionsAndAnswers() {
        List<Question> questions = questionDao.findAll();
        questions.forEach(question -> {
            ioService.printFormattedLine(question.text());
            question.answers().forEach(answer -> ioService.printFormattedLine(answer.text()));
        });
    }
}
