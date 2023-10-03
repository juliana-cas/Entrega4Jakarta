package mapper.mappers;



import domain.models.Student;
import mapper.dtos.StudentDto;

import java.util.List;

public class StudentMapper {
    public static StudentDto mapFrom(Student source){
        return new StudentDto(source.getId(),
                source.getName(),
                source.getEmail(),
                source.getCareer(),
                source.getSemester());
    }
    public static Student mapFrom(StudentDto source){
        return new Student(source.studentId(),
                source.studentName(),
                source.studentEmail(),
                source.career(),
                source.semester());
    }
    public static List<StudentDto> mapFrom(List<Student> source) {
        return source.parallelStream().map(e-> mapFrom(e)).toList();
    }
}