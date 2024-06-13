package hr.tvz.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String street;
    private String zip;
    private String city;
    private String country;
    private String breedPreferences;
    private byte[] profilePicture;
    private double budget;
    private boolean openToAdoptions;
    private String adoptionFromDate;
    private String adoptionToDate;
}
