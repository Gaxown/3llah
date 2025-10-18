package org.consultationsys.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("GENERAL_PRACTITIONER")
public class Generalist extends User {

    @OneToMany(mappedBy = "generalist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Consultation> consultations;

    public Generalist() {
        super();
        this.consultations = new ArrayList<>();
    }
}
