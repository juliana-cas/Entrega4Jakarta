package domain.models;

public class Grades {
    private Long id;
    private Student student;
    private Subject subject;
    private Double grade;

    public Grades(Long id, Student student, Subject subject, Double grade) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.grade = grade;
    }

    public Grades() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Grades{" +
                "id=" + id +
                ", student=" + student +
                ", subject=" + subject +
                ", grade=" + grade;
    }
}