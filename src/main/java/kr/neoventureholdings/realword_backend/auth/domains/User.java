package kr.neoventureholdings.realword_backend.auth.domains;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import kr.neoventureholdings.realword_backend.auth.dto.UserRequestDto;
import kr.neoventureholdings.realword_backend.auth.dto.UserResponseDto;
import kr.neoventureholdings.realword_backend.profile.domains.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Email
  @NotEmpty
  @Column(unique = true)
  private String email;
  @NotEmpty
  @Column
  private String password;
  @NotNull
  @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
  private Profile profile;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "follow",
      joinColumns = @JoinColumn(name = "follower_user_id"),
      inverseJoinColumns = @JoinColumn(name = "followee_user_id")
  )
  private Set<User> followees;

  public UserResponseDto userResponseDto() {
    return UserResponseDto.builder()
        .username(profile.getUsername())
        .email(getEmail())
        .bio(profile.getBio())
        .image(profile.getImage())
        .build();
  }

  public UserResponseDto userResponseDto(String token) {
    return UserResponseDto.builder()
        .username(profile.getUsername())
        .email(getEmail())
        .bio(profile.getBio())
        .image(profile.getImage())
        .token(token)
        .build();
  }

  public User of(UserRequestDto requestDto, PasswordEncoder passwordEncoder) {
    if (StringUtils.hasText(requestDto.getEmail())) {
      setEmail(requestDto.getEmail());
    }

    if (StringUtils.hasText(requestDto.getPassword())) {
      setPassword(passwordEncoder.encode(requestDto.getPassword()));
    }

    if (StringUtils.hasText(requestDto.getUsername())) {
      profile.setUsername(requestDto.getUsername());
    }

    if (StringUtils.hasText(requestDto.getBio())) {
      profile.setBio(requestDto.getBio());
    }

    if (StringUtils.hasText(requestDto.getImage())) {
      profile.setImage(requestDto.getImage());
    }

    return this;
  }
}
