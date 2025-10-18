package org.consultationsys.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.consultationsys.models.User;
import org.consultationsys.utils.JPAUtil;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Optional;

public class UserRepository {

    public User save(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public User update(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        User mergedUser = em.merge(user);
        em.getTransaction().commit();
        em.close();
        return mergedUser;
    }

    public Optional<User> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        User user = em.find(User.class, id);
        em.close();
        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> users = query.getResultList();
        em.close();
        return users;
    }

    public Optional<User> findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        User user = query.getResultStream().findFirst().orElse(null);
        em.close();
        return Optional.ofNullable(user);
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
        em.close();
    }
}
