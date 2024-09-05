package dk.cph;

import dk.cph.config.HibernateConfig;
import dk.cph.dao.CourseDaoImpl;
import dk.cph.model.Course;
import dk.cph.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("university");
        CourseDaoImpl dao = CourseDaoImpl.getInstance(emf);

        Course c1 = Course.builder()
                .courseName(Course.CourseName.HISTORY)
                .description("Fedt kursus om gamle dage")
                .startDate(LocalDate.of(2022, 8, 1))
                .endDate(LocalDate.of(2022, 12, 2))
                .build();

        Teacher t1 = Teacher.builder()
                .name("Jørgen")
                .email("jøhskfe@hejsa.dk")
                .zoom("jllg.zoom.dk")
                .build();

        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(t1);
            em.getTransaction().commit();
        }

        c1.addTeacher(t1);
        dao.persistEntity(c1);
    }
}