package wolox.training.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.exceptions.BookIdNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.dto.BookDTO;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;
import wolox.training.utils.Constants;

/**
 * The type Open library service.
 */
@Service
public class OpenLibraryServiceImpl implements OpenLibraryService {

    /**
     * The Book repository.
     */
    @Autowired
    BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Book bookInfo(String isbn) {
        ResponseEntity<String> response = restTemplate.getForEntity(Constants.URL_EXTERNAL_API
                + isbn + Constants.EXTERNAL_FORMAT, String.class);
        if (response.getBody().contains(Constants.CONSTANT_ISBN)) {
            return bookMapped(response.getBody(), isbn);
        } else {
            throw new BookIdNotFoundException();
        }

    }

    private Book bookMapped(String bookApiExternal, String isbn) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode parent = objectMapper.readTree(bookApiExternal);
            String jsonObject = parent.get(Constants.CONSTANT_ISBN + isbn).toString();
            BookDTO bookDTO = objectMapper.readValue(jsonObject, BookDTO.class);
            return bookDTOToBook(bookDTO, isbn);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BookIdNotFoundException();
        }

    }

    private Book bookDTOToBook(BookDTO bookDTO, String isbn) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setSubtitle(bookDTO.getSubtitle());
        book.setAuthor(bookDTO.getAuthors().get(0).getName());
        book.setIsbn(isbn);
        book.setPublisher(bookDTO.getPublishers().get(0).getName());
        book.setYear(bookDTO.getPublish_date());
        book.setPages(bookDTO.getNumber_of_pages());
        book.setImage("image");
        book.setGenre("genre");

        return book;


    }
}
