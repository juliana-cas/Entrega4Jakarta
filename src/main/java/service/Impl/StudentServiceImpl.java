package service.Impl;


import mapper.dtos.StudentDto;
import repository.Impl.StudentRepositoryImpl;
import repository.Impl.StudentRepositoryLogicImpl;
import repository.StudentRepository;
import service.StudentService;

import java.sql.Connection;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    private StudentRepository repo;

    public StudentServiceImpl(Connection connection) {
        this.repo = new StudentRepositoryImpl(connection);
    }

    @Override
    public List<StudentDto> studentList() {
        return repo.studentList();
    }

    @Override
    public StudentDto byId(Long id) {
        return repo.byId(id);
    }

    @Override
    public void update(StudentDto student) {
        repo.update(student);
    }

    @Override
    public void delete(Long id) {
        repo.delete(id);
    }
}