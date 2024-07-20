package kitae.spring.library.controller;

import kitae.spring.library.dto.ShelfCurrentLoansResponse;
import kitae.spring.library.entity.Book;
import kitae.spring.library.service.BookService;
import kitae.spring.library.utils.ExtractJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token) throws ParseException {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        List<ShelfCurrentLoansResponse> currentLoans = bookService.currentLoans(userEmail);
        System.out.println("==============>> currentLoans: " + currentLoans);
        return currentLoans;
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        int count = bookService.currentLoansCount(userEmail);
        System.out.println("==============>> count: " + count);
        return count;
    }

    @GetMapping("/secure/ischeckout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        Boolean check = bookService.checkoutBookByUser(userEmail, bookId);
        System.out.println("==============>> check: " + check);
        return check;
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        Book book = bookService.checkoutBook(userEmail, bookId);
        System.out.println("==============>> book: " + book);
        return book;
    }

    @PutMapping("/secure/return")
    public void returnBook(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        bookService.returnBook(userEmail, bookId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        bookService.renewLoan(userEmail, bookId);
    }
}
