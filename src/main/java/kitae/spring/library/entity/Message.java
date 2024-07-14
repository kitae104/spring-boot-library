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
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;            // 번호

    private String userEmail;   // 사용자 이메일

    private String title;       // 제목

    private String question;    // 질문

    private String adminEmail;  // 관리자 이메일

    private String response;    // 답변

    private boolean closed;     // 답변 완료 여부
}
