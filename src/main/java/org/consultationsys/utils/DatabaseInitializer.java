package org.consultationsys.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Simple database initialization utility
 * Run this main method to create all database tables
 */
public class DatabaseInitializer {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  Database Migration - Table Creation");
        System.out.println("==============================================\n");

        EntityManagerFactory emf = null;

        try {
            System.out.println("⏳ Connecting to PostgreSQL database...");
            System.out.println("Database: consultation_sys");
            System.out.println("Host: localhost:5432\n");

            // This will trigger Hibernate to create/update all tables
            emf = Persistence.createEntityManagerFactory("consultationPU");

            System.out.println("✅ Connection established successfully!");
            System.out.println("✅ Tables created/updated based on JPA entities\n");

            System.out.println("📋 Expected tables created:");
            System.out.println("   - users (with discriminator for NURSE, GENERAL_PRACTITIONER, SPECIALIST)");
            System.out.println("   - patients");
            System.out.println("   - consultations");
            System.out.println("   - vital_signs");
            System.out.println("   - waiting_queue");
            System.out.println("   - expertise_requests");
            System.out.println("   - time_slots");
            System.out.println("   - technical_procedures\n");

            System.out.println("==============================================");
            System.out.println("  ✅ Database Migration Complete!");
            System.out.println("==============================================");

        } catch (Exception e) {
            System.err.println("\n❌ Error during database initialization:");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.err.println("\n⚠️  Please check:");
            System.err.println("   1. PostgreSQL is running");
            System.err.println("   2. Database 'consultation_sys' exists");
            System.err.println("   3. Credentials in persistence.xml are correct");
            System.exit(1);
        } finally {
            if (emf != null && emf.isOpen()) {
                emf.close();
                System.out.println("\n🔒 Database connection closed.");
            }
        }
    }
}
