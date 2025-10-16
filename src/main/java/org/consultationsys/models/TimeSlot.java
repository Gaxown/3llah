package org.consultationsys.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "time_slots")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialist_id", nullable = false)
    private User specialist;


    @OneToMany(mappedBy = "reservedTimeSlot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExpertiseRequest> expertiseRequests;


    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Column(nullable = false)
    private int duration = 30;
    @Column(nullable = false)
    private boolean available;
    @Column(nullable = false)
    private boolean reserved;

    public TimeSlot() {
        this.expertiseRequests = new ArrayList<>();
    }

    public TimeSlot(LocalDateTime dateTime, int duration, boolean available, boolean reserved) {
        this();
        this.dateTime = dateTime;
        this.duration = duration;
        this.available = available;
        this.reserved = reserved;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public User getSpecialist() {
        return specialist;
    }

    public void setSpecialist(User specialist) {
        this.specialist = specialist;
    }

    public List<ExpertiseRequest> getExpertiseRequests() {
        return expertiseRequests;
    }

    public void setExpertiseRequests(List<ExpertiseRequest> expertiseRequests) {
        this.expertiseRequests = expertiseRequests;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
