package org.consultationsys.repositories;

import java.util.List;
import java.util.Optional;

import org.consultationsys.models.TimeSlot;
import org.consultationsys.utils.JPAUtil;

import jakarta.persistence.EntityManager;

public class TimeSlotRepository {

    public TimeSlotRepository() {
    }

    public void save(TimeSlot timeSlot) {
        if (timeSlot.getId() == null) {
            JPAUtil.executeInTransaction(em -> em.persist(timeSlot));
        } else {
            JPAUtil.executeInTransaction(em -> em.merge(timeSlot));
        }
    }

    public Optional<TimeSlot> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(TimeSlot.class, id));
        } finally {
            em.close();
        }
    }

    public List<TimeSlot> findAllForSpecialist(Long specialist_id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT ts FROM TimeSlot ts WHERE ts.specialist.id = :specialist_id", TimeSlot.class)
                    .setParameter("specialist_id", specialist_id)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public TimeSlot update(TimeSlot timeSlot) {
        final TimeSlot[] updated = new TimeSlot[1];
        JPAUtil.executeInTransaction(em -> {
            updated[0] = em.merge(timeSlot);
        });
        return updated[0];
    }

    public void delete(Long id) {
        findById(id).ifPresent(timeSlot ->
            JPAUtil.executeInTransaction(em -> {
                TimeSlot managed = em.contains(timeSlot) ? timeSlot : em.merge(timeSlot);
                em.remove(managed);
            })
        );
    }

    public List<TimeSlot> findByDateForSpecialist(Long specialist_id, String date) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT ts FROM TimeSlot ts WHERE ts.specialist.id = :specialist_id AND DATE(ts.dateTime) = :date", TimeSlot.class)
                    .setParameter("specialist_id", specialist_id)
                    .setParameter("date", date)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<TimeSlot> findByAvailabilityForSpecialist(Long specialist_id, boolean isAvailable) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT ts FROM TimeSlot ts WHERE ts.specialist.id = :specialist_id AND ts.available = :isAvailable", TimeSlot.class)
                    .setParameter("specialist_id", specialist_id)
                    .setParameter("isAvailable", isAvailable)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<TimeSlot> findBySpecialistId(Long specialist_id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT ts FROM TimeSlot ts WHERE ts.specialist.id = :specialist_id", TimeSlot.class)
                    .setParameter("specialist_id", specialist_id)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
