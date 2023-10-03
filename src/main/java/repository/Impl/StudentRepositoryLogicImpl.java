package repository.Impl;

import domain.models.Student;
import exceptions.Exception;
import mapper.dtos.StudentDto;
import mapper.mappers.StudentMapper;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryLogicImpl implements StudentRepository {

    private List<Student> students;

    public StudentRepositoryLogicImpl() {
        Student s1 = new Student(1L,"Laura","lau@gmail.com","Ingenieria de software","II");
        Student s2 = new Student(2L,"Juan","juan@gmail.com","Psicologia","III");
        Student s3 = new Student(3L,"Luis","luis@gmail.com","Enfermeria","IV");
        students = new ArrayList<>(List.of(s1,s2,s3));
    }

    @Override
    public List<StudentDto> studentList() {
        return StudentMapper.mapFrom(students);
    }

    @Override
    public StudentDto byId(Long id) {
        return students.stream()
                .filter(e->e.getId().equals(e.getId()))
                .findFirst()
                .map(StudentMapper::mapFrom)
                .orElseThrow(()-> new Exception("Student not found"));
    }

    @Override
    public void update(StudentDto student) {
        students.add(StudentMapper.mapFrom(student));
    }

    @Override
    public void delete(Long id) {
        students.removeIf(e->e.getId().equals(id));
    }
}