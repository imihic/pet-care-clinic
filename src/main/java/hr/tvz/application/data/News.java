package hr.tvz.application.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "news")
@Getter
@Setter
public class News extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    private Date publishDate;
    private boolean active;
    private LocalDate expiryDate;

}
