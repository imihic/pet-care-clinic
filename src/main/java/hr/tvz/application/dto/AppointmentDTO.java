package hr.tvz.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppointmentDTO {
    private Long id;
    private Long userId;
    private Long shelterId;
    private Long petId;
    private Date appointmentDate;
    private String status;

}
