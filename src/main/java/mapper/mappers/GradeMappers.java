package mapper.mappers;



import domain.models.Grades;
import mapper.dtos.GradesDto;

import java.util.List;

public class GradeMappers {
    public static GradesDto mapFrom(Grades source) {
        return new GradesDto(source.getId(),
                source.getStudent(),
                source.getSubject(),
                source.getGrade());
    }

    public static Grades mapFrom(GradesDto source){
        return new Grades(source.gradesId(),
                source.student(),
                source.subject(),
                source.grade());
    }

    public static List<GradesDto> mapFrom(List<Grades> sources){
        return sources.parallelStream().map(e-> mapFrom(e)).toList();
    }
}