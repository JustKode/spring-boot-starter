package justkode.starter.domain.user.repository;

import justkode.starter.domain.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = {"authorities"})
    Optional<User> findOneWithAuthoritiesByUserId(String userId);

    Optional<User> findById(Integer id);
    Optional<User> findByUserId(String userId);
}
