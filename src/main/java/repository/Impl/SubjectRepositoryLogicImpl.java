package repository.Impl;


import domain.models.Subject;
import domain.models.Teacher;
import exceptions.Exception;
import mapper.dtos.SubjectDto;
import mapper.mappers.SubjectMapper;
import repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;

public class SubjectRepositoryLogicImpl implements SubjectRepository {

    private List<Subject> subjects;

    public SubjectRepositoryLogicImpl(){
        Subject sb1 = new Subject(1L,"Programacion 2",
                new Teacher(1L,"Monica Tobon","tobon@gmail.com"));
        Subject sb2 = new Subject(2L,"Fisica",
                new Teacher(2L,"Monica Mesa","mesa@gmail.com"));
        Subject sb3 = new Subject(3L,"Programacion 1",
                new Teacher(3L,"Arle Morales","morales@gmail.com"));
        subjects = new ArrayList<>(List.of(sb1,sb2,sb3));
    }

    @Override
    public List<SubjectDto> subjectList() {
        return SubjectMapper.mapFrom(subjects);
    }

    @Override
    public SubjectDto byId(Long id) {
        return subjects.stream()
                .filter(e->e.getId().equals(e.getId()))
                .findFirst()
                .map(SubjectMapper::mapFrom)
                .orElseThrow(()-> new Exception("Subject not found"));
    }

    @Override
    public void update(SubjectDto subject) {
        subjects.add(SubjectMapper.mapFrom(subject));
    }

    @Override
    public void delete(Long id) {
        subjects.removeIf(e -> e.getId().equals(id));
    }
}