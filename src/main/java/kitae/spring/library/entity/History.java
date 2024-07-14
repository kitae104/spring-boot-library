package kitae.spring.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;                // 번호

    private String userEmail;       // 사용자 이메일

    private String checkoutDate;    // 대출일

    private String returnedDate;    // 반납일

    private String title;           // 책 제목

    private String author;          // 저자

    @Column(columnDefinition = "TEXT")
    private String description;     // 책 설명

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String img;             // 책 이미지
}
