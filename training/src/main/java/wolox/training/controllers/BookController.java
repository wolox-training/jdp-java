package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookIdNotFoundException;
import wolox.training.exceptions.IdMismatchException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.utils.EndPoints;

import java.util.List;

/**
 * The CRUD Book controller.
 */
@RestController
@RequestMapping(EndPoints.BOOK_BASE_PATH)
public class BookController {

    /**
     * The Book repository.
     */
    @Autowired
    BookRepository bookRepository;


    /**
     * This method find all Books
     *
     * @return {@link List}<{@link Book}>
     */
    @GetMapping
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * This method find book by id param
     *
     * @param id: Id of the book
     * @return {@link Book}
     * @throws BookIdNotFoundException when book not found
     */
    @GetMapping(EndPoints.PATH_CONSTANT_ID)
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookIdNotFoundException::new);
    }

    /**
     * This method creates an {@link Book} with the data from book param
     *
     * @param book {@link Book}: Data to create
     * @return created {@link Book}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * This method delete book by id param
     *
     * @param id: Id of the book
     */
    @DeleteMapping(EndPoints.PATH_CONSTANT_ID)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Update book.
     *
     * @param {@link Book} the book
     * @param id:    the id of the book
     * @return {@link Book}
     * @throws IdMismatchException     when id's doesn't match
     * @throws BookIdNotFoundException when book not found
     */
    @PutMapping(EndPoints.PATH_CONSTANT_ID)
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new IdMismatchException();
        }
        bookRepository.findById(id)
                .orElseThrow(BookIdNotFoundException::new);
        return bookRepository.save(book);
    }
}
