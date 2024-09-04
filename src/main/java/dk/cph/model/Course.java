package dk.cph.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToMany
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_name", nullable = false)
    private CourseName courseName;

    public void addTeacher(Teacher teacher){
        if (teacher != null){
            this.teacher = teacher;
            teacher.addCourse(this);
        }
    }

    public enum CourseName {
        SPORTS,
        MATH,
        ENGLISH,
        SCIENCE,
        ART,
        MUSIC,
        HISTORY
    }
}


