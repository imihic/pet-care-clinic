package hr.tvz.application.data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "applications")
public class AdoptionApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private Date submissionDate;
    private String notes;

    // Getters and setters

}

