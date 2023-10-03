package repository;

import mapper.dtos.TeacherDto;

import java.util.List;

public interface TeacherRepository {
    List<TeacherDto> teacherList();
    TeacherDto byId(Long id);
    void update(TeacherDto teacher);

    void delete(Long id);
}