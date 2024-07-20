package kitae.spring.library.service;

import kitae.spring.library.dto.AddBookRequest;
import kitae.spring.library.entity.Book;
import kitae.spring.library.repository.BookRepository;
import kitae.spring.library.repository.CheckoutRepository;
import kitae.spring.library.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final BookRepository bookRepository;

    private final ReviewRepository reviewRepository;

    private final CheckoutRepository checkoutRepository;

    // 책을 추가하는 메소드
    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());

        bookRepository.save(book);
    }

    // 책의 재고를 늘리는 메소드
    public void increaseBookQuantity(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));
        book.setCopies(book.getCopies() + 1);
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        bookRepository.save(book);
    }

    // 책의 재고를 줄이는 메소드
    public void decreaseBookQuantity(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));
        book.setCopies(book.getCopies() - 1);
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));

        bookRepository.delete(book);
        checkoutRepository.deleteAllByBookId(bookId);
        reviewRepository.deleteAllByBookId(bookId);
    }
}
