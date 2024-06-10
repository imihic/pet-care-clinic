package hr.tvz.application.data;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "shelters")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String address;

    @OneToMany(mappedBy = "shelter")
    private Set<Pet> pets;

    @OneToMany(mappedBy = "shelter")
    private Set<AdoptionApplication> applications;

    @OneToMany(mappedBy = "shelter")
    private Set<Appointment> appointments;

    // Getters and setters
}