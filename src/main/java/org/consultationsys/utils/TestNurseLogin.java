package org.consultationsys.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.consultationsys.models.User;
import org.mindrot.jbcrypt.BCrypt;

public class TestNurseLogin {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("consultationPU");
            em = emf.createEntityManager();

            // Try to find the nurse user
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", "nurse@hospital.com")
                    .getSingleResult();

            System.out.println("✅ User found:");
            System.out.println("   ID: " + user.getId());
            System.out.println("   Name: " + user.getFullName());
            System.out.println("   Email: " + user.getEmail());
            System.out.println("   Class: " + user.getClass().getSimpleName());
            System.out.println("   Role: " + user.getRole());
            System.out.println("   Active: " + user.isActive());

            // Test password
            String testPassword = "password123";
            boolean matches = BCrypt.checkpw(testPassword, user.getPasswordHash());
            System.out.println("   Password matches: " + matches);

        } catch (Exception e) {
            System.err.println("❌ Error:");
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}

