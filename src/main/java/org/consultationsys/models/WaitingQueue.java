package org.consultationsys.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "waiting_queue")
public class WaitingQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    // @Enumerated(EnumType.STRING)
    // private QueueStatus status;

    public WaitingQueue() {
    }

    public WaitingQueue(Patient patient, LocalDateTime arrivalTime) {
        this.patient = patient;
        this.arrivalTime = arrivalTime;
        // this.status = QueueStatus.WAITING;
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

}
