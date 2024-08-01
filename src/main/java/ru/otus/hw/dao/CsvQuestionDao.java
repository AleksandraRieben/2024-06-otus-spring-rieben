package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        String fileName = this.fileNameProvider.getTestFileName();
        var inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new QuestionReadException("File not found: " + fileName);
        }

        try (var reader = new InputStreamReader(inputStream)) {
            return getQuestionList(reader);

        } catch (IOException e) {
            throw new QuestionReadException("Error: ", e);
        }
    }

    private static List<Question> getQuestionList(InputStreamReader reader) {
        CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(reader)
               .withType(QuestionDto.class)
               .withIgnoreLeadingWhiteSpace(true)
               .withSkipLines(1)
               .withSeparator(';')
               .build();

        List<QuestionDto> questionDtos = csvToBean.parse();
        return questionDtos.stream().map(QuestionDto::toDomainObject).toList();
    }
}
