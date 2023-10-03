package domain.models;

public class Student {
    private Long id;
    private String name;
    private String email;
    private String career;
    private String semester;

    public Student(Long id, String name, String email, String career, String semester) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.career = career;
        this.semester = semester;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String Career) {
        this.career = career;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", career='" + career + '\'' +
                '}';
    }
}