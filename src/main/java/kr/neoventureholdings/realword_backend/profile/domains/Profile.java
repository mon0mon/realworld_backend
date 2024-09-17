package kr.neoventureholdings.realword_backend.profile.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import kr.neoventureholdings.realword_backend.profile.dto.ProfileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile", indexes = @Index(name = "idx_username", columnList = "username"))
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  @OneToOne(fetch = FetchType.EAGER)
  private User user;
  @NotBlank
  @Column(name = "username", unique = true)
  private String username;
  @Column
  private String bio;
  @Column
  private String image;

  public ProfileResponseDto of(User user) {
    return ProfileResponseDto
        .builder()
        .bio(getBio())
        .username(getUsername())
        .image(getImage())
        .following(user != null && user.getFollowees()
            .stream()
            .anyMatch(followee -> followee.getProfile().equals(this))
          )
        .build();
  }
}
