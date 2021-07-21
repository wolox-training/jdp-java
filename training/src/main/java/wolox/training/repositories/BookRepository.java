package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

import java.util.Optional;

/**
 * The interface Book repository.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Find by author optional.
     *
     * @param author the author
     * @return the optional {@link Book}
     */
    Optional<Book> findByAuthor(String author);

    /**
     * Find by isbn optional.
     *
     * @param isbn the isbn
     * @return the optional {@link Book}
     */
    Optional<Book> findByIsbn(String isbn);
}
