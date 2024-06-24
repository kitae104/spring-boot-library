package kitae.spring.library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 45)
    private String title;

    @Column(length = 45)
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int copies;

    private int copiesAvailable;

    @Column(length = 11)
    private String category;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String img;
}
