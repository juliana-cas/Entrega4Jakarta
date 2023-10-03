package mapper.dtos;

import lombok.Builder;

@Builder
public record StudentDto(
        Long studentId,
        String studentName,
        String studentEmail,
        String career,
        String semester) {

}