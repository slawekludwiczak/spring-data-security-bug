package example.springdata.jpa.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :#{principal.username}")
    Optional<User> currentUserByName();

    @Query("SELECT u FROM User u WHERE u.username = :#{principal.username} AND 1=:#{isAuthenticated()? 1:0}")
    Optional<User> currentUserByNameAndAuthentication();
}
