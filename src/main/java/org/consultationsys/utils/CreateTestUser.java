package org.consultationsys.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.consultationsys.models.Generalist;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;

public class CreateTestUser {
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  Creating Test User");
        System.out.println("==============================================\n");

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("consultationPU");
            em = emf.createEntityManager();

            em.getTransaction().begin();

            // Check if user already exists
            Long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                    .setParameter("email", "admin@test.com")
                    .getSingleResult();

            if (count > 0) {
                System.out.println("⚠️  User 'admin@test.com' already exists!");
                em.getTransaction().rollback();
                return;
            }

            // Create a test Generalist (General Practitioner)
            String hashedPassword = BCrypt.hashpw("password123", BCrypt.gensalt(12));

            Generalist generalist = new Generalist();
            generalist.setFirstName("Admin");
            generalist.setLastName("Test");
            generalist.setEmail("admin@test.com");
            generalist.setPasswordHash(hashedPassword);
            generalist.setActive(true);

            em.persist(generalist);
            em.getTransaction().commit();

            System.out.println("✅ Test user created successfully!\n");
            System.out.println("📋 Login Credentials:");
            System.out.println("   Email: admin@test.com");
            System.out.println("   Password: password123");
            System.out.println("   Role: GENERAL_PRACTITIONER\n");
            System.out.println("==============================================");

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("❌ Error creating test user:");
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
