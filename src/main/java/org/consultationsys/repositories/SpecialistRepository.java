package org.consultationsys.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.consultationsys.models.Specialist;
import org.consultationsys.utils.JPAUtil;

import java.util.List;
import java.util.Optional;

public class SpecialistRepository {

    public Specialist save(Specialist specialist) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(specialist);
            em.getTransaction().commit();
            return specialist;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error saving specialist", e);
        } finally {
            em.close();
        }
    }

    public Specialist update(Specialist specialist) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Specialist updated = em.merge(specialist);
            em.getTransaction().commit();
            return updated;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error updating specialist", e);
        } finally {
            em.close();
        }
    }

    public Optional<Specialist> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Specialist specialist = em.find(Specialist.class, id);
            return Optional.ofNullable(specialist);
        } finally {
            em.close();
        }
    }

    public List<Specialist> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Specialist> query = em.createQuery(
                "SELECT s FROM Specialist s", Specialist.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Specialist> findBySpecialty(String specialty) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Specialist> query = em.createQuery(
                "SELECT s FROM Specialist s WHERE s.specialty = :specialty", Specialist.class);
            query.setParameter("specialty", specialty);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Specialist> findActiveBySpecialty(String specialty) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Specialist> query = em.createQuery(
                "SELECT s FROM Specialist s WHERE s.specialty = :specialty AND s.active = true " +
                "ORDER BY s.consultationFee ASC", Specialist.class);
            query.setParameter("specialty", specialty);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Specialist specialist = em.find(Specialist.class, id);
            if (specialist != null) {
                em.remove(specialist);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error deleting specialist", e);
        } finally {
            em.close();
        }
    }
}
