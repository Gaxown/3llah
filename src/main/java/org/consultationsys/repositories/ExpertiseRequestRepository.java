package org.consultationsys.repositories;

import java.util.List;
import java.util.Optional;

import org.consultationsys.models.ExpertiseRequest;
import org.consultationsys.models.enums.ExpertiseRequestStatus;
import org.consultationsys.models.enums.Priority;
import org.consultationsys.utils.JPAUtil;

import jakarta.persistence.EntityManager;

public class ExpertiseRequestRepository {

    public ExpertiseRequestRepository() {

    }

    public List<ExpertiseRequest> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT exp FROM ExpertiseRequest exp", ExpertiseRequest.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<ExpertiseRequest> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(ExpertiseRequest.class, id));
        } finally {
            em.close();
        }
    }

    public List<ExpertiseRequest> getByGeneralistId(Long generalistId){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT exp FROM ExpertiseRequest exp WHERE exp.consultation.generalist.id = :id", ExpertiseRequest.class)
                    .setParameter("id", generalistId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<ExpertiseRequest> findBySpecialistId(Long specialistId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT exp FROM ExpertiseRequest exp WHERE exp.specialist.id = :id", ExpertiseRequest.class)
                    .setParameter("id", specialistId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<ExpertiseRequest> findByConsultationId(Long consultationId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT exp FROM ExpertiseRequest exp WHERE exp.consultation.id = :id", ExpertiseRequest.class)
                    .setParameter("id", consultationId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<ExpertiseRequest> findByStatus(ExpertiseRequestStatus status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT exp FROM ExpertiseRequest exp WHERE exp.status = :status", ExpertiseRequest.class)
                    .setParameter("status", status)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<ExpertiseRequest> getByPriority(Priority priority) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT exp FROM ExpertiseRequest exp WHERE exp.priority = :priority", ExpertiseRequest.class)
                    .setParameter("priority", priority)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public ExpertiseRequest save(ExpertiseRequest expertiseRequest) {
        if (expertiseRequest.getId() == null) {
            JPAUtil.executeInTransaction(em -> em.persist(expertiseRequest));
            return expertiseRequest;
        } else {
            final ExpertiseRequest[] updated = new ExpertiseRequest[1];
            JPAUtil.executeInTransaction(em -> updated[0] = em.merge(expertiseRequest));
            return updated[0];
        }
    }

    public ExpertiseRequest update(ExpertiseRequest expertiseRequest) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ExpertiseRequest updated = em.merge(expertiseRequest);
            em.getTransaction().commit();
            return updated;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error updating expertise request", e);
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        findById(id).ifPresent(expertiseRequest ->
            JPAUtil.executeInTransaction(em -> {
                ExpertiseRequest managed = em.contains(expertiseRequest) ? expertiseRequest : em.merge(expertiseRequest);
                em.remove(managed);
            })
        );
    }
}
