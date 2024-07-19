package kitae.spring.library.repository;

import kitae.spring.library.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // 사용자 이메일에 따라 메시지를 찾는 메소드
    Page<Message> findByUserEmail(@RequestParam("userEmail")String userEmail, Pageable pageable);

    // 답변 완료 여부에 따라 메시지를 찾는 메소드
    Page<Message> findByClosed(@RequestParam("closed")boolean closed, Pageable pageable);
}
