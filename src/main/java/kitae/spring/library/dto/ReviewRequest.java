package kitae.spring.library.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class ReviewRequest {

    private double rating;  // 별점

    private Long bookId;    // 책 ID

    private Optional<String> reviewDescription; // 리뷰 내용
}
