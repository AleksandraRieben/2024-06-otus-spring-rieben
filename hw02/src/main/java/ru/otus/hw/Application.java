package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw.service.TestRunnerServiceImpl;

@ComponentScan
@PropertySource("classpath:application.properties")
public class Application {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(Application.class);
        var testRunnerService = context.getBean(TestRunnerServiceImpl.class);
        testRunnerService.run();
    }
}