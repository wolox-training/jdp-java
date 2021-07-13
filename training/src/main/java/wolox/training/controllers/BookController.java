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

@RestController
@RequestMapping(EndPoints.BOOK_BASE_PATH)
public class BookController {

    @Autowired
    BookRepository bookRepository;


    @GetMapping
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping(EndPoints.PATH_CONSTANT_ID)
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookIdNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping(EndPoints.PATH_CONSTANT_ID)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

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
