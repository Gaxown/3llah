package org.consultationsys.services;

import org.consultationsys.models.Nurse;
import org.consultationsys.models.User;
import org.consultationsys.models.enums.Role;
import org.consultationsys.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService() {
        this.userRepository = new UserRepository();
    }

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> BCrypt.checkpw(password, user.getPasswordHash()));
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void createTestNurse() {
        if (userRepository.findByEmail("nurse.test@system.com").isEmpty()) {
            User nurse = new Nurse();
            nurse.setFirstName("Test");
            nurse.setLastName("Nurse");
            nurse.setEmail("nurse.test@system.com");
            nurse.setPasswordHash(hashPassword("password"));
            nurse.setActive(true);
            userRepository.save(nurse);
        }
    }
}
