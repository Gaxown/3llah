package org.consultationsys.models;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("NURSE")
public class Nurse extends User {

    public Nurse() {
        super();
    }

    public Nurse(String lastName, String firstName, String email, String passwordHash, boolean active) {
        super(lastName, firstName, email, passwordHash, active);
    }
}

