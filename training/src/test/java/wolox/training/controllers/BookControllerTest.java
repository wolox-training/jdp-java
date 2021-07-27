package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.exceptions.BookIdNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.services.impl.OpenLibraryServiceImpl;
import wolox.training.utils.EndPoints;
import wolox.training.utils.TestConstants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Mock
    private OpenLibraryServiceImpl openLibraryService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private MockMvc mvc;
    private User user;

    private User user2;

    private List<Book> listBooks;

    private Book book;

    private Book book2;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        listBooks = new ArrayList<>();
        book = new Book();
        book.setId(3L);
        book.setAuthor("Paulline Asgar");
        book.setImage("123zx53");
        book.setIsbn("12-c2-98");
        book.setPublisher("Extralight");
        book.setTitle("100 miles aways");
        book.setSubtitle("A true love story");
        book.setPages(470);
        book.setGenre("Love");
        book.setYear("2015");
        book2 = new Book();
        book2.setId(1L);
        book2.setAuthor("Susanne Collins");
        book2.setImage("image2.jpg");
        book2.setIsbn("775-32");
        book2.setPublisher("Norma");
        book2.setTitle("Hunger games");
        book2.setSubtitle("Catching fire");
        book2.setPages(500);
        book2.setGenre("Adventure");
        book2.setYear("2013");
        listBooks.add(book);
        listBooks.add(book2);
        user = new User();
        user.setId(21L);
        user.setUsername("joseph");
        user.setName("Juan Daniel");
        user.setPassword("panda15");
        user.setBirthdate(LocalDate.of(1990, 03, 03));
        user.setBooks(listBooks);
        user2 = new User();
        user2.setId(18L);
        user2.setUsername("rucho");
        user2.setName("Juan Daniel");
        user2.setPassword("123");
        user2.setBirthdate(LocalDate.of(1990, 03, 03));

    }

    @WithMockUser(value = "joseph")
    @Test
    public void whenFindAllBooks_thenReturnOkay() throws Exception {
        Mockito.when(bookRepository.findAll(null, null, null,
                null, null, null,
                null, null, 470, PageRequest.of(0, 10, Sort.by("id").ascending())))
                .thenReturn(new PageImpl<>(Arrays.asList(book)));
        mvc.perform(get(EndPoints.BOOK_BASE_PATH).queryParam("pages", "470"))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "joseph")
    @Test
    public void whenFindOneBook_thenReturnOkay() throws Exception {

        Mockito.when(bookRepository.findById(TestConstants.BOOK_EXISTS)).thenReturn(Optional.of(book));
        mvc.perform(get(EndPoints.BOOK_BASE_PATH + TestConstants.BOOK_MOCK_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre", is("Love")));
    }

    @WithMockUser(value = "joseph")
    @Test
    public void whenCannotFindOneBook_thenReturnNotFound() throws Exception {

        Mockito.when(bookRepository.findById(TestConstants.USER_UPDATE)).thenReturn(Optional.of(book));
        mvc.perform(get(EndPoints.BOOK_BASE_PATH + TestConstants.NEW_USER_MOCK_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookIdNotFoundException));
    }

    @WithMockUser(value = "joseph")
    @Test
    void whenDeleteABookThatExists_thenReturnAccepted() throws Exception {
        doNothing().when(bookRepository).deleteById(TestConstants.BOOK_EXISTS);
        mvc.perform(delete(EndPoints.BOOK_BASE_PATH + TestConstants.BOOK_MOCK_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @WithMockUser(value = "joseph")
    @Test
    void whenUpdateABookThatExists_thenReturnOk() throws Exception {
        Optional<Book> optionalBook = Optional.of(book);


        Mockito.when(bookRepository.findById(TestConstants.BOOK_EXISTS)).thenReturn(optionalBook);
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        mvc.perform(put(EndPoints.BOOK_BASE_PATH + TestConstants.BOOK_MOCK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "joseph")
    @Test
    void FindABookThatExistsByIsbn_thenReturnOk() throws Exception {
        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepository.findById(TestConstants.BOOK_EXISTS)).thenReturn(optionalBook);
        when(openLibraryService.bookInfo(TestConstants.BOOK_MOCK_EXTERNAL_ISBN)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        mvc.perform(get(EndPoints.WIREMOCK_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }
}