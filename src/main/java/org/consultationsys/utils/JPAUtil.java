package org.consultationsys.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "consultationPU";
    private static EntityManagerFactory entityManagerFactory;

    private void JAPUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            System.err.println("Failed to create EntityManagerFactory: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            throw new IllegalStateException("EntityManagerFactory is not available");
        }
        return entityManagerFactory.createEntityManager();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            System.out.println("EntityManagerFactory closed successfully");
        }
    }

    public static void executeInTransaction(TransactionAction action) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            action.execute(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Transaction failed", e);
        } finally {
            em.close();
        }
    }

    @FunctionalInterface
    public interface TransactionAction {
        void execute(EntityManager em);
    }
}


