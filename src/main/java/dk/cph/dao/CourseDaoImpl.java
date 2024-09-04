package dk.cph.dao;

import dk.cph.model.Course;
import dk.cph.model.Student;
import dk.cph.model.Teacher;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseDaoImpl implements GenericDAO<Student, Teacher> {

    private static CourseDaoImpl instance;
    private static EntityManagerFactory emf;

    public static CourseDaoImpl getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CourseDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Student> findAll() {
        return List.of();
    }

    @Override
    public void persistEntity(Student entity) {
    }

    @Override
    public void removeEntity(Teacher id) {
    }

    @Override
    public Student findEntity(Teacher id) {
        return null;
    }

    @Override
    public Student updateEntity(Student entity, Teacher id) {
        return null;
    }
}