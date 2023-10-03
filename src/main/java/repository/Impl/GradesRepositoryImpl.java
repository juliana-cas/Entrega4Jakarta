package repository.Impl;

import domain.models.Grades;
import domain.models.Student;
import domain.models.Subject;
import domain.models.Teacher;
import mapper.dtos.GradesDto;
import mapper.mappers.GradeMappers;
import repository.GradesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradesRepositoryImpl implements GradesRepository {

    private Connection conn;

    public GradesRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    public GradesRepositoryImpl() {

    }

    private Grades createGrades(ResultSet rs) throws SQLException {
        Grades grades = new Grades();
        grades.setId(rs.getLong("id"));

        Student student = new Student();
        student.setId(rs.getLong("student_id"));
        student.setName(rs.getString("student_name"));
        student.setEmail(rs.getString("student_email"));
        student.setCareer(rs.getString("career"));
        student.setSemester(rs.getString("semester"));
        grades.setStudent(student);

        Subject subject = new Subject();
        subject.setId(rs.getLong("subject_id"));
        subject.setName(rs.getString("subject_name"));
        Teacher teacher = new Teacher();
        teacher.setId(rs.getLong("teacher_id"));
        teacher.setName(rs.getString("teacher_name"));
        teacher.setEmail(rs.getString("teacher_email"));
        subject.setTeachers(teacher);
        grades.setSubject(subject);


        grades.setGrade(rs.getDouble("grade"));

        return grades;
    }
    @Override
    public List<GradesDto> gradesList() {
        List<Grades> gradestList = new ArrayList<>();

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT gr.*," +
                     "       st.id AS student_id," +
                     "       st.name AS student_name," +
                     "       st.email AS student_email," +
                     "       st.career," +
                     "       st.semester," +
                     "       sb.id AS subject_id," +
                     "       sb.name AS subject_name," +
                     "       th.id AS teacher_id," +
                     "       th.name AS teacher_name," +
                     "       th.email AS teacher_email " +
                     "FROM grades gr " +
                     "JOIN students st ON gr.student_id = st.id " +
                     "JOIN subjects sb ON gr.subject_id = sb.id " +
                     "JOIN teachers th ON sb.teacher_id = th.id; " )) {
            while (resultSet.next()) {
                Grades grades = createGrades(resultSet);
                gradestList.add(grades);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return GradeMappers.mapFrom(gradestList);
    }

    @Override
    public GradesDto byId(Long id) {
        Grades grades = null;
        try (PreparedStatement preparedStatement = conn
                .prepareStatement("SELECT gr.*," +
                        "       st.id AS student_id," +
                        "       st.name AS student_name," +
                        "       st.email AS student_email," +
                        "       st.career," +
                        "       st.semester," +
                        "       sb.id AS subject_id," +
                        "       sb.name AS subject_name," +
                        "       th.id AS teacher_id," +
                        "       th.name AS teacher_name," +
                        "       th.email AS teacher_email " +
                        "FROM grades gr " +
                        "JOIN students st ON gr.student_id = st.id " +
                        "JOIN subjects sb ON gr.subject_id = sb.id " +
                        "JOIN teachers th ON sb.teacher_id = th.id " +
                        "WHERE gr.id = ?;")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                grades = createGrades(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return GradeMappers.mapFrom(grades);
    }

    @Override
    public void update(GradesDto grades) {
        String sql;
        if (grades.gradesId() != null && grades.gradesId()>0) {
            sql = "UPDATE grades SET grade=?, corte=? WHERE id=?";
        } else {
            sql = "INSERT INTO grades(grade, corte) VALUES(?,?)";
        }
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, grades.grade());

            if (grades.gradesId() != null && grades.gradesId()>0) {
                stmt.setLong(3, grades.gradesId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM grades WHERE id =?")){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}