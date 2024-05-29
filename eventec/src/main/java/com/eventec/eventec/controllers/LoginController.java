package com.eventec.eventec.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.eventec.eventec.config.LoginResponse;
import com.eventec.eventec.models.UserItem;
import com.eventec.eventec.services.UserItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserItemService userItemService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        Optional<UserItem> user = userItemService.getByEmailAndPassword(email, password);
        if (user.isPresent()) {
            if (user.get().isEmailConfirmed()) {
                // Login successful
                return ResponseEntity.ok().body(new LoginResponse("Login realizado com sucesso!"));
            } else {
                // Email not confirmed
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você precisa confirmar seu email antes de começar a usar o Eventec");
            }
        } else {
            // Login failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos!");
        }
    }
}