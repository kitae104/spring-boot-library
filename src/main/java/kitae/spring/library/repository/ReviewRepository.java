package kitae.spring.library.repository;

import kitae.spring.library.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewRepository extends JpaRepository<Review, Long>{

    // 책 id로 리뷰 조회하기
    Page<Review> findByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);

    // 사용자 이메일과 책 id로 리뷰 조회하기
    Review findByUserEmailAndBookId(String userEmail, Long bookId);
}
