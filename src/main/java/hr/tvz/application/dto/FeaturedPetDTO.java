package hr.tvz.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeaturedPetDTO extends PetDTO {

    private String name;
    private String description;
    private String photo;
}
