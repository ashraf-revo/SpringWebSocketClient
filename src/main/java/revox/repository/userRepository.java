package revox.repository;

import org.springframework.data.repository.CrudRepository;
import revox.domain.user;

import java.util.Optional;

/**
 * Created by ashraf on 8/2/15.
 */
public interface userRepository extends CrudRepository<user,Long> {
   public Optional<user>  findByEmail(String email);
}
