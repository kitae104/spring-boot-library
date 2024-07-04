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
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;                // 대출 ID

    private String userEmail;    // 사용자 이메일

    private String checkoutDate;    // 대출 날짜

    private String returnDate;  // 반납 날짜

    private Long bookId;    // 책 ID
}
