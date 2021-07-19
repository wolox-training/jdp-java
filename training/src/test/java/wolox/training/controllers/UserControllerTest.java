package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
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

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {


    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private MockMvc mvc;

    private User user;

    private List<Book> listBooks;

    private Book book;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        listBooks = new ArrayList<>();
        book = new Book();
        book.setId(1L);
        book.setAuthor("Paulline Asgar");
        book.setImage("123zx53");
        book.setIsbn("12-c2-98");
        book.setPublisher("Extralight");
        book.setTitle("100 miles aways");
        book.setSubtitle("A true love story");
        book.setPages(470);
        book.setGenre("Love");
        book.setYear("2015");
        Book book2 = new Book();
        book2.setId(3L);
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
        user.setId(19L);
        user.setUsername("rucho");
        user.setName("Juan Daniel");
        user.setBirthdate(LocalDate.of(1990, 03, 03));
        user.setBooks(listBooks);

    }

    @Test
    public void whenFindAllUsers_thenReturnOkay() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        mvc.perform(get(EndPoints.USER_BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }


    @Test
    public void whenFindOneUser_thenReturnOkay() throws Exception {

        Mockito.when(userRepository.findById(TestConstants.USER_EXISTS_19)).thenReturn(Optional.of(user));
        mvc.perform(get(EndPoints.USER_BASE_PATH + TestConstants.USER_MOCK_ID_19))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("rucho")));
    }

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

    @Test
    void whenDeleteAUserThatExists_thenReturnAccepted() throws Exception {
        doNothing().when(userRepository).deleteById(TestConstants.USER_EXISTS_19);
        mvc.perform(delete(EndPoints.USER_BASE_PATH + TestConstants.USER_MOCK_ID_19)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

}