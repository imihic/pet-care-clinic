package hr.tvz.application.data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    private Date publishDate;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    // Getters and setters
}
