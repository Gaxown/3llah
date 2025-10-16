package org.consultationsys.repositories;

import org.consultationsys.models.VitalSign;
import org.consultationsys.utils.JPAUtil;

import java.util.List;

public class VitalSignRepository {

    public VitalSignRepository() {
    }

    public VitalSign save(VitalSign vitalSign) {
        if (vitalSign.getId() == null) {
            JPAUtil.executeInTransaction(em -> em.persist(vitalSign));
            return vitalSign;
        }
        final VitalSign[] updated = new VitalSign[1];
        JPAUtil.executeInTransaction(em -> updated[0] = em.merge(vitalSign));
        return updated[0];
    }

    public List<VitalSign> findByPatientId(Long patientId) {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT vs FROM VitalSign vs WHERE vs.patient.id = :patientId ORDER BY vs.recordDate DESC", VitalSign.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }

}
