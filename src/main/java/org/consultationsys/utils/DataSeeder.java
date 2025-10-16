package org.consultationsys.utils;

import org.consultationsys.models.Generalist;
import org.consultationsys.models.Nurse;
import org.consultationsys.models.Specialist;
import org.consultationsys.services.AuthenticationService;
import org.consultationsys.repositories.UserRepository;

/**
 * Utility to seed the database with test users
 * Run this once to create test accounts for each role
 */
public class DataSeeder {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  Database Seeding - Creating Test Users");
        System.out.println("==============================================\n");

        AuthenticationService authService = new AuthenticationService();
        UserRepository userRepository = new UserRepository();

        try {
            // Create a Nurse
            System.out.println("Creating Nurse account...");
            Nurse nurse = new Nurse(
                "Benali",
                "Fatima",
                "nurse@hospital.ma",
                authService.hashPassword("nurse123"),
                true
            );
            userRepository.save(nurse);
            System.out.println("✅ Nurse created: nurse@hospital.ma / nurse123");

            // Create a Generalist
            System.out.println("\nCreating Generalist account...");
            Generalist generalist = new Generalist();
            generalist.setLastName("Alaoui");
            generalist.setFirstName("Mohammed");
            generalist.setEmail("generalist@hospital.ma");
            generalist.setPasswordHash(authService.hashPassword("generalist123"));
            generalist.setActive(true);
            userRepository.save(generalist);
            System.out.println("✅ Generalist created: generalist@hospital.ma / generalist123");

            // Create Specialists
            System.out.println("\nCreating Specialist accounts...");

            Specialist cardiologist = new Specialist(
                "Bennani",
                "Amina",
                "cardio@hospital.ma",
                authService.hashPassword("specialist123"),
                true,
                "Cardiologie",
                300.0
            );
            userRepository.save(cardiologist);
            System.out.println("✅ Cardiologist created: cardio@hospital.ma / specialist123");

            Specialist dermatologist = new Specialist(
                "El Amrani",
                "Youssef",
                "dermato@hospital.ma",
                authService.hashPassword("specialist123"),
                true,
                "Dermatologie",
                250.0
            );
            userRepository.save(dermatologist);
            System.out.println("✅ Dermatologist created: dermato@hospital.ma / specialist123");

            Specialist neurologist = new Specialist(
                "Benjelloun",
                "Sara",
                "neuro@hospital.ma",
                authService.hashPassword("specialist123"),
                true,
                "Neurologie",
                350.0
            );
            userRepository.save(neurologist);
            System.out.println("✅ Neurologist created: neuro@hospital.ma / specialist123");

            System.out.println("\n==============================================");
            System.out.println("  ✅ Data Seeding Complete!");
            System.out.println("==============================================");
            System.out.println("\n📋 Test Accounts Created:");
            System.out.println("   Nurse:       nurse@hospital.ma       / nurse123");
            System.out.println("   Generalist:  generalist@hospital.ma  / generalist123");
            System.out.println("   Specialists: cardio@hospital.ma      / specialist123");
            System.out.println("                dermato@hospital.ma     / specialist123");
            System.out.println("                neuro@hospital.ma       / specialist123");
            System.out.println("\n🔐 All passwords are hashed using BCrypt");

        } catch (Exception e) {
            System.err.println("\n❌ Error during data seeding:");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

