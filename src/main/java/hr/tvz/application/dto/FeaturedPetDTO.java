package hr.tvz.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeaturedPetDTO {

    private String name;
    private String description;
    private byte[] photo;
}
