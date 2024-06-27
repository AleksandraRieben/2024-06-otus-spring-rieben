package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        String fileName = this.fileNameProvider.getTestFileName();
        var inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
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
