package kitae.spring.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String userEmail;

    @CreationTimestamp
    private LocalDateTime date;

    private double rating;

    private Long bookId;

    private String reviewDescription;
}
