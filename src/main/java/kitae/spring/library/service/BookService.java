package kitae.spring.library.service;

import kitae.spring.library.dto.ShelfCurrentLoansResponseDto;
import kitae.spring.library.entity.Book;
import kitae.spring.library.entity.Checkout;
import kitae.spring.library.entity.History;
import kitae.spring.library.repository.BookRepository;
import kitae.spring.library.repository.CheckoutRepository;
import kitae.spring.library.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final CheckoutRepository checkoutRepository;

    private final HistoryRepository historyRepository;

    // 책을 대출하는 메소드
    public Book checkoutBook(String userEmail, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (validateCheckout != null) {
            throw new IllegalArgumentException("이미 대출한 책입니다.");
        }

        book.setCopiesAvailable(book.getCopiesAvailable() - 1); // 대출한 책의 재고를 1 줄임
        bookRepository.save(book);

        Checkout checkout = Checkout.builder()  // 대출 정보를 저장
                .userEmail(userEmail)
                .checkoutDate(LocalDate.now().toString())
                .returnDate(LocalDate.now().plusDays(7).toString())
                .bookId(book.getId())
                .build();
        checkoutRepository.save(checkout);

        return book;
    }

    // 사용자가 이미 대출한 책인지 확인하는 메소드
    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (validateCheckout != null) {
            return true;
        } else {
            return false;
        }
    }

    // 대출한 책들의 수를 가져오는 메소드
    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

    // 대출한 책들의 정보를 가져오는 메소드
    public List<ShelfCurrentLoansResponseDto> currentLoans(String userEmail) throws ParseException {
        List<ShelfCurrentLoansResponseDto> shelfCurrentLoansResponseDtos = new ArrayList<>();   // 대출한 책들의 정보를 담을 DTO 리스트
        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);   // 대출한 책들의 정보를 가져옴
        List<Long> bookIdList = new ArrayList<>();  // 대출한 책들의 ID를 담을 리스트

        for (Checkout checkout : checkoutList) {
            bookIdList.add(checkout.getBookId());   // 대출한 책들의 ID를 리스트에 추가
        }

        List<Book> bookList = bookRepository.findBooksByBookId(bookIdList);    // 대출한 책들의 정보를 가져옴
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // 날짜 포맷을 지정

        for (Book book : bookList) {
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(c -> c.getBookId() == book.getId()).findFirst();  // bookId가 일치하는 첫번째 checkout 객체를 찾음

            if (checkout.isPresent()) {                                   // checkout 객체가 존재하면
                Date d1 = sdf.parse(checkout.get().getCheckoutDate());    // 대출 날짜를 가져와서
                Date d2 = sdf.parse(LocalDate.now().toString());          // 현재 날짜와 비교하여
                TimeUnit time = TimeUnit.DAYS;                            // 시간 단위를 일로 설정
                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS); // 대출 날짜와 현재 날짜의 차이를 계산

                shelfCurrentLoansResponseDtos.add(new ShelfCurrentLoansResponseDto(book, (int) difference_In_Time)); // 책 정보와 대출 날짜와의 차이를 DTO에 추가
            }
        }
        return shelfCurrentLoansResponseDtos;
    }

    public void returnBook(String userEmail, Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(!book.isPresent() || validateCheckout == null) {
            throw new IllegalArgumentException("존재하지 않는 책이거나 사용자에 의해 대출된 적이 없습니다.");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        bookRepository.save(book.get());
        checkoutRepository.deleteById(validateCheckout.getId());

        History history = History.builder()     // 대출 히스토리 정보를 저장
                .userEmail(userEmail)
                .checkoutDate(validateCheckout.getCheckoutDate())
                .returnedDate(LocalDate.now().toString())
                .title(book.get().getTitle())
                .author(book.get().getAuthor())
                .description(book.get().getDescription())
                .img(book.get().getImg())
                .build();

        historyRepository.save(history);    // 대출 히스토리 정보를 저장

    }

    public void renewLoan(String userEmail, Long bookId) throws Exception {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId); // 대출한 책인지 확인

        if(validateCheckout == null) {
            throw new IllegalArgumentException("대출한 책이 아닙니다.");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // 날짜 포맷을 지정
        Date d1 = sdf.parse(validateCheckout.getReturnDate());    // 반납 날짜를 가져와서
        Date d2 = sdf.parse(LocalDate.now().toString());          // 현재 날짜와 비교하여

        if(d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0){
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(validateCheckout);
        }
    }
}
