package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.IdMismatchException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.utils.EndPoints;

import java.util.List;

/**
 * The type Book controller.
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
     * Greetings! with a name
     *
     * @param name:  Optional name of who is going to greet (String)
     * @param model: Contains the data that appears in the view (Model)
     * @return The name of the view to perform the greeting
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @GetMapping
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Create book book.
     *
     * @param book the book
     * @return the book
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * Delete book.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Update book book.
     *
     * @param book the book object
     * @param id   the id
     * @return the book
     * @throws IdMismatchException when id param and book's id mismatch
     */
    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new IdMismatchException();
        }
        bookRepository.findById(id);
        return bookRepository.save(book);
    }
}
