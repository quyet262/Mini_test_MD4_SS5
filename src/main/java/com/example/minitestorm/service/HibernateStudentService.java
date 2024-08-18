package com.example.minitestorm.service;

import com.example.minitestorm.model.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


@Service
public class HibernateStudentService implements IStudentService {

    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> findAll() {
        String queryStr = "select s from Student as s";
        TypedQuery<Student> query = entityManager.createQuery(queryStr, Student.class);
        return query.getResultList();
    }

    @Override
    public Student findById(int id) {
        String queryStr = "select s from Student as s where s.id = :id";
        TypedQuery<Student> query = entityManager.createQuery(queryStr, Student.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void save(Student student) {
        Transaction transaction = null;
        Student origin;
        if (student.getId() == 0) {
            origin = new Student();
        } else {
            origin = findById(student.getId());
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setId(student.getId());
            origin.setFistName(student.getFistName());
            origin.setLastName(student.getLastName());
            origin.setDob(student.getDob());
            origin.setAddress(student.getAddress());
            origin.setMark(student.getMark());
            origin.setImage(student.getImage());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void delete(int id) {
        Student student = findById(id);
        if (student != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.delete(student);
                transaction.commit();
            }catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

    }

    @Override
    public void update(int id, Student student) {
        Transaction transaction = null;
        Student existingStudent = findById(id);

        if (existingStudent != null) {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();

                existingStudent.setFistName(student.getFistName());
                existingStudent.setLastName(student.getLastName());
                existingStudent.setDob(student.getDob());
                existingStudent.setAddress(student.getAddress());
                existingStudent.setMark(student.getMark());
                existingStudent.setImage(student.getImage());

                session.saveOrUpdate(existingStudent);
                transaction.commit();

            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
