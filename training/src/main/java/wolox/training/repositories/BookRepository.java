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

    /**
     * Find all fields list.
     *
     * @param genre     the genre
     * @param author    the author
     * @param image     the image
     * @param title     the title
     * @param subtitle  the subtitle
     * @param publisher the publisher
     * @param year      the year
     * @param pages     the pages
     * @param isbn      the isbn
     * @return the list of {@link Book}
     */

    @Query(value = "SELECT b FROM Book b "
            + "WHERE (:publisher IS NULL OR b.publisher = :publisher) "
            + "AND (:year IS NULL OR b.year = :year) "
            + "AND (:genre IS NULL OR b.genre = :genre) "
            + "AND (:author IS NULL OR b.author = :author) "
            + "AND (:isbn IS NULL OR b.isbn = :isbn) "
            + "AND (:image IS NULL OR b.image = :image) "
            + "AND (:title IS NULL OR b.title = :title) "
            + "AND (:subtitle IS NULL OR b.subtitle = :subtitle) "
            + "AND (:pages IS NULL OR b.pages = :pages)"
    )
    List<Book> findAll(@Param("publisher") String publisher,
                       @Param("year") String year, @Param("genre") String genre,
                       @Param("author") String author, @Param("isbn") String isbn,
                       @Param("image") String image, @Param("title") String title,
                       @Param("subtitle") String subtitle, @Param("pages") Integer pages);
}
