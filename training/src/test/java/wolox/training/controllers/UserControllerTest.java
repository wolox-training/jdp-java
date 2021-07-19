package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.EndPoints;
import wolox.training.utils.TestConstants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    PasswordEncoder passwordEncoder;
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
    public void whenFindAllUsers_thenReturnOkay() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        mvc.perform(get(EndPoints.USER_BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }


    @WithMockUser(value = "spring")
    @Test
    public void whenFindOneUser_thenReturnOkay() throws Exception {

        Mockito.when(userRepository.findById(TestConstants.USER_EXISTS_19)).thenReturn(Optional.of(user));
        mvc.perform(get(EndPoints.USER_BASE_PATH + TestConstants.USER_MOCK_ID_19))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("joseph")));
    }

    @WithMockUser(value = "spring")
    @Test
    public void whenCannotFindOneUser_thenReturnNotFound() throws Exception {

        Mockito.when(userRepository.findById(TestConstants.USER_NOT_EXISTS_19)).thenReturn(Optional.of(user));
        mvc.perform(get(EndPoints.USER_BASE_PATH + TestConstants.USER_MOCK_ID_19))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

    @Test
    public void whenCreateUser_thenReturnUserCreated() throws Exception {

        mvc.perform(post(EndPoints.USER_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @WithMockUser(value = "spring")
    @Test
    void whenDeleteAUserThatExists_thenReturnAccepted() throws Exception {
        doNothing().when(userRepository).deleteById(TestConstants.USER_EXISTS_19);
        mvc.perform(delete(EndPoints.USER_BASE_PATH + TestConstants.USER_MOCK_ID_19)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @WithMockUser(value = "spring")
    @Test
    void whenUpdateAUserThatExists_thenReturnOk() throws Exception {
        Optional<User> optionalUser = Optional.of(user);


        Mockito.when(userRepository.findById(TestConstants.USER_UPDATE)).thenReturn(optionalUser);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        mvc.perform(put(EndPoints.USER_BASE_PATH + TestConstants.USER_PATH_UPDATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }


    @WithMockUser(value = "spring")
    @Test
    public void whenDeleteUserBook_ThenDeletedUserBookOk() throws Exception {

        Optional<User> optionalUser = Optional.of(user);
        Optional<Book> optionalBook = Optional.of(book2);

        Mockito.when(userRepository.findById(TestConstants.USER_EXISTS_19)).thenReturn(optionalUser);
        Mockito.when(bookRepository.findById(TestConstants.CONSTANT_ID)).thenReturn(optionalBook);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        mvc.perform(delete(EndPoints.USER_BASE_PATH + TestConstants.USER_MOCK_ID_19 +
                EndPoints.BOOKS_PATH + TestConstants.BOOK_MOCK_PATH))
                .andExpect(status().isOk());


    }

    @WithMockUser(value = "spring")
    @Test
    public void whenAddUserBook_ThenReturnOk() throws Exception {
        Optional<User> optionalUser = Optional.of(user2);
        Optional<Book> optionalBook = Optional.of(book2);

        Mockito.when(userRepository.findById(18L)).thenReturn(optionalUser);
        Mockito.when(bookRepository.findById(TestConstants.CONSTANT_ID)).thenReturn(optionalBook);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        mvc.perform(put(EndPoints.USER_BASE_PATH + TestConstants.NEW_USER_MOCK_ID +
                EndPoints.BOOKS_PATH + TestConstants.BOOK_MOCK_PATH))
                .andExpect(status().isOk());

    }

    @WithMockUser(value = "spring")
    @Test
    public void whenAddUserBook_ThenReturnBookAlreadyOwned() throws Exception {
        Optional<User> optionalUser = Optional.of(user);
        Optional<Book> optionalBook = Optional.of(book);

        Mockito.when(userRepository.findById(TestConstants.USER_EXISTS_19)).thenReturn(optionalUser);
        Mockito.when(bookRepository.findById(TestConstants.CONSTANT_ID)).thenReturn(optionalBook);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        mvc.perform(put(EndPoints.USER_BASE_PATH + TestConstants.USER_MOCK_ID_19 +
                EndPoints.BOOKS_PATH + TestConstants.BOOK_MOCK_PATH))
                .andExpect(status().isBadRequest());

    }

    @WithMockUser(value = "spring")
    @Test
    public void whenAddUserBook_ThenReturnIdNotFound() throws Exception {
        Optional<User> optionalUser = Optional.of(user);
        Optional<Book> optionalBook = Optional.of(book);

        Mockito.when(userRepository.findById(TestConstants.USER_EXISTS_19)).thenReturn(optionalUser);
        Mockito.when(bookRepository.findById(TestConstants.BOOK_EXISTS)).thenReturn(optionalBook);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        mvc.perform(put(EndPoints.USER_BASE_PATH + TestConstants.USER_MOCK_ID_19 +
                EndPoints.BOOKS_PATH + TestConstants.BOOK_MOCK_PATH))
                .andExpect(status().isNotFound());

    }

}