package swc3.demowebshop.security.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.security.entities.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
