package hr.tvz.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String name;
    private String street;
    private String zip;
    private String city;
    private String country;
    private String breedPreferences;
    private byte[] profilePicture;
    private double budget;
    private boolean openToAdoptions;
    private String role;
}
