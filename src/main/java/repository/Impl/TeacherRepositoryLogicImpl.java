package repository.Impl;
import domain.models.Teacher;
import exceptions.Exception;
import mapper.dtos.TeacherDto;
import mapper.mappers.TeacherMapper;
import repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

public class TeacherRepositoryLogicImpl implements TeacherRepository {

    private List<Teacher> teachers;

    public TeacherRepositoryLogicImpl() {
        Teacher t1 = new Teacher(1L,"Monica Tobon","tobon@gmail.com");
        Teacher t2 = new Teacher(2L,"Monica Mesa","mesa@gmail.com");
        Teacher t3 = new Teacher(3L,"Arle Morales","morales@gmail.com");
        teachers = new ArrayList<>(List.of(t1,t2,t3));
    }

    @Override
    public List<TeacherDto> teacherList() {
        return TeacherMapper.mapFrom(teachers);
    }

    @Override
    public TeacherDto byId(Long id) {
        return teachers.stream()
                .filter(e->e.getId().equals(e.getId()))
                .findFirst()
                .map(TeacherMapper::mapFrom)
                .orElseThrow(()-> new Exception("Teacher not found"));
    }

    @Override
    public void update(TeacherDto teacher) {
        teachers.add(TeacherMapper.mapFrom(teacher));
    }

    @Override
    public void delete(Long id) {
        teachers.removeIf(e->e.getId().equals(id));
    }
}