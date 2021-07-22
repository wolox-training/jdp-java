package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

import java.util.List;
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

    /**
     * Find by publisher and genre and year list.
     *
     * @param publisher the publisher
     * @param genre     the genre
     * @param year      the year
     * @return the list of {@link Book}
     */
    @Query("SELECT b FROM Book b "
            + "WHERE (:publisher IS NULL OR b.publisher = :publisher) "
            + "AND (:year IS NULL OR b.year = :year) "
            + "AND (:genre IS NULL OR b.genre = :genre)")
    List<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher,
                                              @Param("genre") String genre, @Param("year") String year);
}
