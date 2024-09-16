package kr.neoventureholdings.realword_backend.profile.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
  @OneToOne
  private User user;
  @NotBlank
  @Column(name = "username", unique = true)
  private String username;
  @Column
  private String bio;
  @Column
  private String image;

  //  TODO 현재 Following 중인지 체크하는 로직 추가 필요
  public ProfileResponseDto of(User user) {
    return ProfileResponseDto
        .builder()
        .bio(getBio())
        .username(getUsername())
        .image(getImage())
        .following(user == null ? false : false)
        .build();
  }
}
