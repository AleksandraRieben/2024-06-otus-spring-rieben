package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;
    private final QuestionDao questionDao;
    private final AtomicInteger correctAnswers = new AtomicInteger();
    private List<Question> questions;

    @Override
    public void executeTest() {
        ioService.printFormattedLine("Please answer the questions below:");
        ioService.printFormattedLine("----------------------------------");
        printQuestionsAndAnswers();
        printExamResult();
    }

    private void printQuestionsAndAnswers() {
        this.questions = questionDao.findAll();
        questions.forEach(question -> {
            ioService.printFormattedLine(question.text());
            ioService.printFormattedLine("----------------------------------");
            List<Answer> answers = question.answers();
            for (int i = 0; i < answers.size(); i++) {
                ioService.printFormattedLine((i + 1) + ". " + answers.get(i).text());
            }
            ioService.printFormattedLine("----------------------------------");
            ioService.printFormattedLine("Please select one of the answers:");

            int selectedOption = selectOption(question);

            printAnswer(selectedOption, answers);
        });
    }

    private int selectOption(Question question) {
        int selectedOption;
        while (true) {
            String input = ioService.readString("");
            try {
                selectedOption = Integer.parseInt(input);
                if (selectedOption < 1 || selectedOption > question.answers().size()) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                ioService.printLine("Invalid input. Please select a number between 1 and " + question.answers().size() + ".");
            }
        }
        return selectedOption;
    }

    private void printAnswer(int selectedOption, List<Answer> answers) {
        Answer selectedAnswer = answers.get(selectedOption - 1);
        if (selectedAnswer.isCorrect()) {
            ioService.printLine("Correct! The answer is: " + selectedAnswer.text());
            correctAnswers.incrementAndGet();
        } else {
            ioService.printLine("Incorrect. The correct answer is: " + getCorrectAnswer(answers));
        }
    }

    private String getCorrectAnswer(List<Answer> answers) {
        return answers.stream()
                .filter(Answer::isCorrect)
                .map(Answer::text)
                .findFirst()
                .orElse("No correct answer found");
    }

    private void printExamResult() {
        ioService.printFormattedLine("------------RESULT:------------");
        ioService.printFormattedLine(hasPassedExam() ? "You have passed the exam" : "Sorry, you have to repeat it.");
    }

    private boolean hasPassedExam() {
        return correctAnswers.get() >= questions.size() / 2;
    }
}
