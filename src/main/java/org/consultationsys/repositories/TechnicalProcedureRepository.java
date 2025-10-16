package org.consultationsys.repositories;

import java.util.List;
import java.util.Optional;

import org.consultationsys.models.TechnicalProcedure;
import org.consultationsys.utils.JPAUtil;

public class TechnicalProcedureRepository {

    public TechnicalProcedureRepository() {
    }

    public TechnicalProcedure save(TechnicalProcedure technicalProcedure) {
        if (technicalProcedure.getId() == null) {
            JPAUtil.executeInTransaction(em -> em.persist(technicalProcedure));
            return technicalProcedure;
        }
        final TechnicalProcedure[] updated = new TechnicalProcedure[1];
        JPAUtil.executeInTransaction(em -> updated[0] = em.merge(technicalProcedure));
        return updated[0];
    }

    public Optional<TechnicalProcedure> findById(Long id) {
        return Optional.ofNullable(JPAUtil.getEntityManager().find(TechnicalProcedure.class, id));
    }

    public List<TechnicalProcedure> findAll() {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT tp FROM TechnicalProcedure tp", TechnicalProcedure.class)
                .getResultList();
    }

    public void delete(Long id) {
        findById(id).ifPresent(technicalProcedure ->
            JPAUtil.executeInTransaction(em -> em.remove(em.contains(technicalProcedure) ? technicalProcedure : em.merge(technicalProcedure))
            ));
    }

    public List<TechnicalProcedure> getByDate(String date) {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT tp FROM TechnicalProcedure tp WHERE tp.date = :date", TechnicalProcedure.class)
                .setParameter("date", date)
                .getResultList();
    }

    public List<TechnicalProcedure> getByType(String type) {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT tp FROM TechnicalProcedure tp WHERE tp.type = :type", TechnicalProcedure.class)
                .setParameter("type", type)
                .getResultList();
    }

}
