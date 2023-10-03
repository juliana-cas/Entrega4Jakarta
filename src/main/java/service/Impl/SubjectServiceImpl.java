package service.Impl;

import mapper.dtos.SubjectDto;
import repository.Impl.StudentRepositoryImpl;
import repository.Impl.SubjectRepositoryImpl;
import repository.Impl.SubjectRepositoryLogicImpl;
import repository.Impl.TeacherRepositoryLogicImpl;
import repository.SubjectRepository;
import service.SubjectService;

import java.sql.Connection;
import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    private SubjectRepository repo;

    public SubjectServiceImpl(Connection connection) {
        this.repo = new SubjectRepositoryImpl(connection);
    }

    @Override
    public List<SubjectDto> subjectList() {
        return repo.subjectList();
    }

    @Override
    public SubjectDto byId(Long id) {
        return repo.byId(id);
    }

    @Override
    public void update(SubjectDto subject) {
        repo.update(subject);
    }

    @Override
    public void delete(Long id) {
        repo.delete(id);
    }
}