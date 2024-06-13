package hr.tvz.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplicationDTO {
    private Long id;
    private Long userId;
    private Long petId;
    private String status;
    private Date submissionDate;
    private String notes;


}
