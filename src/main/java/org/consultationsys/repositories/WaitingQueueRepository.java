package org.consultationsys.repositories;

import org.consultationsys.models.WaitingQueue;
import org.consultationsys.utils.JPAUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class WaitingQueueRepository {

    public WaitingQueueRepository() {
    }

    public WaitingQueue save(WaitingQueue waitingQueue) {
        if (waitingQueue.getId() == null) {
            JPAUtil.executeInTransaction(em -> em.persist(waitingQueue));
            return waitingQueue;
        }
        final WaitingQueue[] updated = new WaitingQueue[1];
        JPAUtil.executeInTransaction(em -> updated[0] = em.merge(waitingQueue));
        return updated[0];
    }

    public Optional<WaitingQueue> findById(Long id) {
        return Optional.ofNullable(JPAUtil.getEntityManager().find(WaitingQueue.class, id));
    }

    public List<WaitingQueue> findByDate(LocalDate date) {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT wq FROM WaitingQueue wq WHERE wq.arrivalTime >= :start AND wq.arrivalTime < :end", WaitingQueue.class)
                .setParameter("start", date.atStartOfDay())
                .setParameter("end", date.plusDays(1).atStartOfDay())
                .getResultList();
    }

    public Optional<WaitingQueue> findByPatientId(Long patientId) {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT wq FROM WaitingQueue wq WHERE wq.patient.id = :patientId", WaitingQueue.class)
                .setParameter("patientId", patientId)
                .getResultStream()
                .findFirst();
    }

    public List<WaitingQueue> findAll() {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT wq FROM WaitingQueue wq", WaitingQueue.class)
                .getResultList();
    }

    public void delete(Long id) {
        findById(id).ifPresent(waitingQueue ->
            JPAUtil.executeInTransaction(em -> em.remove(em.contains(waitingQueue) ? waitingQueue : em.merge(waitingQueue)))
        );
    }

}
