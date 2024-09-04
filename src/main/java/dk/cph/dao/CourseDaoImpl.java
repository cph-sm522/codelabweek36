package dk.cph.dao;

import dk.cph.model.Course;
import dk.cph.model.Student;
import dk.cph.model.Teacher;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseDaoImpl implements GenericDAO<Course, Integer> {

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
    public List<Course> findAll() {
        return List.of();
    }

    @Override
    public void persistEntity(Course course) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        }
    }

    @Override
    public void removeEntity(Integer id) {

    }

    @Override
    public Course findEntity(Integer id) {
        return null;
    }

    @Override
    public Course updateEntity(Course entity, Integer id) {
        return null;
    }
}