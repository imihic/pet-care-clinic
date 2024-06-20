package hr.tvz.application.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class Pet extends AbstractEntity {

    private String name;
    private String description;
    private int age;
    private boolean vaccinated;

    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Lob
    private String photo;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    private boolean featured;

    private boolean adopted;
}
