package kr.neoventureholdings.realword_backend.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {
  @PostMapping("/users")
  public ResponseEntity<String> registerUser() {
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  @GetMapping("/users")
  public ResponseEntity<UserDetails> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    return new ResponseEntity<>(userDetails, HttpStatus.OK);
  }

  @PutMapping("/user")
  public ResponseEntity<UserDetails> updateUser(@AuthenticationPrincipal UserDetails userDetails) {
    return new ResponseEntity<>(userDetails, HttpStatus.OK);
  }
}
