package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProperties implements TestFileNameProvider {
    @Value("${test.fileName}")
    private String testFileName;
}
