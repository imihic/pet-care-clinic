package hr.tvz.application.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int age;
    private boolean vaccinated;
    private Date birthDate;
    private byte[] photo;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    private boolean featured;
}
