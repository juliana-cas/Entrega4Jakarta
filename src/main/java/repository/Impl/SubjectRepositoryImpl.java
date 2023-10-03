package repository.Impl;

import conexion.ConexionDB;
import domain.models.Subject;
import domain.models.Teacher;
import mapper.dtos.SubjectDto;
import mapper.mappers.SubjectMapper;
import repository.SubjectRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepositoryImpl implements SubjectRepository {

    private Connection conn;

    public  SubjectRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    private Subject createSubject(ResultSet rs) throws SQLException {
        Subject subject = new Subject();
        subject.setId(rs.getLong("id"));
        subject.setName(rs.getString("name"));

        Teacher teacher = new Teacher();
        teacher.setId(rs.getLong("teacher_id"));
        teacher.setName(rs.getString("teacher_name"));
        teacher.setEmail(rs.getString("email"));
        subject.setTeachers(teacher);

        return subject;
    }

    @Override
    public List<SubjectDto> subjectList() {
        List<Subject> subjectList = new ArrayList<>();

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT sb.*, th.name as teacher_name, th.email " +
                     "FROM subjects sb " +
                     "JOIN teachers th ON sb.teacher_id = th.id ;" )) {
            while (resultSet.next()) {
                Subject subject = createSubject(resultSet);
                subjectList.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SubjectMapper.mapFrom(subjectList);
    }

    @Override
    public SubjectDto byId(Long id) {
        Subject subject = null;
        try (PreparedStatement preparedStatement = conn
                .prepareStatement("SELECT sb.*, th.name as teacher_name, th.email " +
                        "FROM subjects sb " +
                        "JOIN teachers th ON sb.teacher_id = th.id " +
                        "WHERE sb.id=?;")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                subject = createSubject(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SubjectMapper.mapFrom(subject);
    }

    @Override
    public void update(SubjectDto subject) {
        String sql;
        if (subject.subjectId() != null && subject.subjectId()>0) {
            sql = "UPDATE subjects SET name=? WHERE id=?";
        } else {
            sql = "INSERT INTO subjects(name) VALUES(?)";
        }
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (subject.subjectId() != null && subject.subjectId()>0) {
                stmt.setString(1, subject.subjectName());
                stmt.setLong(2, subject.subjectId());
            } else{
                stmt.setString(1, subject.subjectName());
            }
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(Long id) {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM subjects WHERE id =?")){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}