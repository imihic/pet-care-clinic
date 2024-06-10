package hr.tvz.application.data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int age;
    private boolean vaccinated;
    private Date birthDate;
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    // Getters and setters
}
