package kitae.spring.library.controller;

import kitae.spring.library.dto.AddBookRequest;
import kitae.spring.library.service.AdminService;
import kitae.spring.library.utils.ExtractJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value = "Authorization") String token,
                         @RequestBody AddBookRequest addBookRequest) {
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if(admin == null || !admin.equals("admin")) {
            throw new IllegalArgumentException("관리자만 접근 가능합니다.");
        }

        adminService.postBook(addBookRequest);
    }
}
