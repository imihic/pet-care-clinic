package hr.tvz.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

// Getters and setters
@Getter
@Setter
public class NewsDTO {
    private Long id;
    private String title;
    private String content;
    private Date publishDate;
    private Long shelterId;

}
