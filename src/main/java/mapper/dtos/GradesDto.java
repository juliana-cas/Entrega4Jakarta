package mapper.dtos;

import domain.models.Student;
import domain.models.Subject;
import lombok.Builder;

@Builder
public record GradesDto(
        Long gradesId,
        Student student,
        Subject subject,
        Double grade) {

    public GradesDto(Long gradesId, Double grade) {
        this(gradesId, null, null, grade);
    }
}