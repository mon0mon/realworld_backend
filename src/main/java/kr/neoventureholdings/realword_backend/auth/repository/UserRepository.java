package kr.neoventureholdings.realword_backend.auth.repository;

import java.util.Optional;
import kr.neoventureholdings.realword_backend.auth.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail (String email);

  Optional<User> findByEmailAndPassword (String email, String password);
}
