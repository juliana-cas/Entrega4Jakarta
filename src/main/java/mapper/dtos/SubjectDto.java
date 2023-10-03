package mapper.dtos;


import domain.models.Teacher;
import lombok.Builder;

@Builder
public record SubjectDto (
        Long subjectId,
        String subjectName,
        Teacher teacher){

    public SubjectDto(Long subjectId, String subjectName) {
        this(subjectId, subjectName, null);
    }
}
