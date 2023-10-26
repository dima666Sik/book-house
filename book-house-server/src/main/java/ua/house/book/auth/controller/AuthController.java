package ua.house.book.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.house.book.auth.domain.Role;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.User;
import ua.house.book.auth.service.AuthService;

import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    @GetMapping("/registration")
    public String registration() {
        System.out.println("Registration is successful!");
            Account user = User.builder()
                    .id(null)
                    .email("dimon@gmail.com")
                    .password("qwerty")
                    .username("dimas")
                    .roleSet(Set.of(Role.USER))
                    .build();
            authService.registration(user);
        return "registration";
    }
    @GetMapping("/authorization")
    public String authorization(Model model) {
            Account user = User.builder()
                .id(null)
                .email("dimon@gmail.com")
                .password("qwerty")
                .username("dimas")
                .roleSet(Set.of(Role.USER))
                .build();

        Account userRes = authService.authorization(user.getEmail(),user.getPassword(), User.class)
                .orElseThrow(()->new RuntimeException("Not found!"));
        System.out.println("Authorization is successful!");

        // Добавьте объект user в модель
        model.addAttribute("user", userRes);
        return "authorization";
    }
}
