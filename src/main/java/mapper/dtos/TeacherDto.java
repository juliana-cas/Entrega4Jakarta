package mapper.dtos;

import lombok.Builder;

@Builder
public record TeacherDto (
        Long teacherId,
        String teacherName,
        String teacherEmail){
}