package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find by user name optional.
     *
     * @param username the username
     * @return the optional {@link User}
     */
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u "
            + " WHERE (:birthdateStart is null or u.birthdate between :birthdateStart and :birthdateEnd)"
            + " AND (:name is null or UPPER(u.name) like UPPER(concat('%', :name,'%')))")
    List<User> findByBirthdateBetweenAndNameContainingIgnoreCase(@Param("birthdateStart") LocalDate birthdateStart,
                                                                 @Param("birthdateEnd") LocalDate birthdateEnd,
                                                                 @Param("name") String name);
}
