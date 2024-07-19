package kitae.spring.library.dto;

import kitae.spring.library.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShelfCurrentLoansResponse {
    private Book book;          // 책 정보
    private int daysLeft;       // 반납까지 남은 일수
}
