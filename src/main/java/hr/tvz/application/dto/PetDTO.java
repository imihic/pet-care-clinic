package hr.tvz.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PetDTO {
    private Long id;
    private String name;
    private String description;
    private int age;
    private boolean vaccinated;
    private Date birthDate;
    private String photo;
    private Long shelterId;

}
