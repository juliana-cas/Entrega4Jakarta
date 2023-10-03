package service.Impl;

import mapper.dtos.GradesDto;
import repository.GradesRepository;
import repository.Impl.GradesRepositoryImpl;
import repository.Impl.StudentRepositoryImpl;
import repository.StudentRepository;
import service.GradesService;

import java.sql.Connection;
import java.util.List;

public class GradesServiceImpl implements GradesService {

    private GradesRepository repo;

    public GradesServiceImpl(Connection connection) {
        this.repo = new GradesRepositoryImpl(connection);
    }
    @Override
    public List<GradesDto> gradesList() {
        return repo.gradesList();
    }

    @Override
    public GradesDto byId(Long id) {
        return repo.byId(id);
    }

    @Override
    public void update(GradesDto grades) {
        repo.update(grades);
    }

    @Override
    public void delete(Long id) {
        repo.delete(id);
    }
}