package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import wolox.training.exceptions.BookIdNotFoundException;
import wolox.training.exceptions.IdMismatchException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;
import wolox.training.utils.Constants;
import wolox.training.utils.EndPoints;

import java.util.List;
import java.util.Optional;

/**
 * The CRUD Book controller.
 */
@RestController
@RequestMapping(EndPoints.BOOK_BASE_PATH)
@Api
public class BookController {

    /**
     * The Book repository.
     */
    @Autowired
    BookRepository bookRepository;

    /**
     * The Open library service.
     */
    @Autowired
    OpenLibraryService openLibraryService;


    /**
     * This method find all Books
     *
     * @return {@link List}<{@link Book}>
     */
    @GetMapping
    @ApiOperation(value = "Return all books with any parameter entered", response = Book[].class)
    @ApiResponse(code = 200, message = "Books successfully retrieved")
    public Page<Book> findAll(
            @RequestParam(value = "publisher", required = false) String publisher,
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "subtitle", required = false) String subtitle,
            @RequestParam(value = "pages", required = false) Integer pages
    ) {
        Pageable pagination = PageRequest.of(0, 10, Sort.by(Constants.ID));
        return bookRepository.findAll(publisher, year, genre, author, isbn, image,
                title, subtitle, pages, pagination);
    }

    /**
     * This method find book by id param
     *
     * @param id : Id of the book
     * @return {@link Book}
     * @throws BookIdNotFoundException when book not found
     */
    @GetMapping(EndPoints.PATH_CONSTANT_ID)
    @ApiOperation(value = "Return one book", response = Book[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book successfully retrieved"),
            @ApiResponse(code = 404, message = "Book not found")
    })
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
    @ApiOperation(value = "Create a book with the data from a book object, return book created", response = Book.class)
    @ApiResponse(code = 201, message = "Book created successfully")
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * This method delete book by id param
     *
     * @param id : Id of the book
     */
    @DeleteMapping(EndPoints.PATH_CONSTANT_ID)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Delete a book with a specific id", response = Book.class)
    @ApiResponse(code = 202, message = "Book deleted successfully")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Update book.
     *
     * @param book the book
     * @param id   :    the id of the book
     * @return {@link Book}
     * @throws IdMismatchException     when id's doesn't match
     * @throws BookIdNotFoundException when book not found
     */
    @PutMapping(EndPoints.PATH_CONSTANT_ID)
    @ApiOperation(value = "Update a book with the id from book object and a specific book id param, " +
            "return book updated", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book successfully updated"),
            @ApiResponse(code = 400, message = "Bad request from the validation"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new IdMismatchException();
        }
        bookRepository.findById(id)
                .orElseThrow(BookIdNotFoundException::new);
        return bookRepository.save(book);
    }

    /**
     * Find book by isbn response entity.
     *
     * @param isbn the isbn
     * @return the response entity
     * @throws BookIdNotFoundException when book not found
     */
    @GetMapping("/isbn/{isbn}")
    @ApiOperation(value = "Method to consume an external api if the book doesn't exist in the repository",
            response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book successfully retrieved"),
            @ApiResponse(code = 201, message = "Bad successfully created"),
            @ApiResponse(code = 404, message = "Book not found in the external api")
    })
    public ResponseEntity<Book> findBookByIsbn(@PathVariable String isbn) {
        Optional<Book> bookLocal = bookRepository.findByIsbn(isbn);
        if (!bookLocal.isPresent()) {
            Book bookApi = openLibraryService.bookInfo(isbn);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(bookApi));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(bookLocal.get());
        }
    }
}
