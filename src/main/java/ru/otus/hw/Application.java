package ru.otus.hw;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.service.TestRunnerService;
import ru.otus.hw.service.TestRunnerServiceImpl;

public class Application {
    public static void main(String[] args) {

        // Прописать бины в spring-context.xml и создать контекст на его основе
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestRunnerService testRunnerService = context.getBean(TestRunnerServiceImpl.class);
        testRunnerService.run();
    }
}