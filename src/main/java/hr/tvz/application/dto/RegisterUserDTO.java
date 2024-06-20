package hr.tvz.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegisterUserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String street;
    private Integer zip;
    private String city;
    private String country;
    private String breedPreferences;
    private byte[] profilePicture;
    private double budget;
    private boolean openToAdoptions;
    private Date adoptionFromDate;
    private Date adoptionToDate;
}
