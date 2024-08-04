package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final IOService ioService;

    @Override
    public Student createStudent() {
        var firstName = ioService.readString("Please input your first name");
        var lastName = ioService.readString("Please input your last name");
        return new Student(firstName, lastName);
    }
}
