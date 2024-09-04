package dk.cph.dao;

import dk.cph.model.Course;
import dk.cph.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeacherDaoImpl implements GenericDAO<Teacher, Integer> {

    private static TeacherDaoImpl instance;
    private static EntityManagerFactory emf;

    public static TeacherDaoImpl getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TeacherDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Teacher> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Teacher t", Teacher.class);
            return query.getResultList();
        }
    }

    @Override
    public void persistEntity(Teacher entity) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Set<Course> courses = entity.getCourses();

            if (!courses.isEmpty()) {
                Set<Course> newCourses = new HashSet<>();

                courses.forEach(course -> {
                    TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE c.courseName = :courseName", Course.class);
                    query.setParameter("courseName", course.getCourseName());

                    try {
                        Course foundCourse = query.getSingleResult();
                        newCourses.add(foundCourse);
                    } catch (NoResultException e) {
                        em.persist(course);
                        newCourses.add(course);
                    }
                });

                entity.setCourses(newCourses);
            }

            em.persist(entity);
            em.getTransaction().commit();
        }
    }

    @Override
    public void removeEntity(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Teacher teacher = findEntity(id);
            em.remove(teacher);

            em.getTransaction().commit();
        }
    }

    @Override
    public Teacher findEntity(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Teacher.class, id);
        }
    }

    @Override
    public Teacher updateEntity(Teacher entity, Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Teacher foundTeacher = em.find(Teacher.class, entity.getId());

            em.getTransaction().begin();

            if (!entity.getCourses().isEmpty()) {
                entity.getCourses().forEach(course -> {
                    Course foundCourse = em.find(Course.class, course.getId());

                    if (course.getCourseName() != null) {
                        foundCourse.setCourseName(course.getCourseName());
                    }
                    if (course.getDescription() != null) {
                        foundCourse.setDescription(course.getDescription());
                    }
                    if (course.getStartDate() != null) {
                        foundCourse.setStartDate(course.getStartDate());
                    }
                    if (course.getEndDate() != null) {
                        foundCourse.setEndDate(course.getEndDate());
                    }
                });
            }
            if (entity.getName() != null) {
                foundTeacher.setName(entity.getName());
            }
            if (entity.getEmail() != null) {
                foundTeacher.setEmail(entity.getEmail());
            }
            if (entity.getZoom() != null) {
                foundTeacher.setZoom(entity.getZoom());
            }

            em.getTransaction().commit();
        }

        return entity;
    }
}
