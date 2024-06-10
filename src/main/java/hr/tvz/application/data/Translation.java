package hr.tvz.application.data;

import jakarta.persistence.*;

@Entity
@Table(name = "translations")
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;
    private String key;
    private String value;

    // Getters and setters
}
