package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.EndPoints;

import java.util.List;
import java.util.Optional;

/**
 * The type User controller.
 */
@RestController
@RequestMapping(EndPoints.USER_BASE_PATH)
public class UserController {

    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * The Book repository.
     */
    @Autowired
    BookRepository bookRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Find all list.
     *
     * @return {@link List}<{@link User}>
     */
    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Find one user.
     *
     * @param id the id
     * @return {@link User}
     * @throws UserNotFoundException when User is not found
     */
    @GetMapping(EndPoints.PATH_CONSTANT_ID)
    public User findOne(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * This method creates an {@link User} with the data from user param
     *
     * @param user {@link User}: Data to create
     * @return created {@link User}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Delete user.
     *
     * @param id of the user
     */
    @DeleteMapping(EndPoints.PATH_CONSTANT_ID)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Update book user.
     *
     * @param user {@link User}: User object
     * @param id   the id
     * @return updated {@link User}
     * @throws UserIdMismatchException when id's doesn't match
     * @throws UserNotFoundException   when User is not found
     */
    @PutMapping(EndPoints.PATH_CONSTANT_ID)
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        if (user.getId() != id) {
            throw new UserIdMismatchException();
        }
        User userDB = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(userDB.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Add book user.
     *
     * @param id     the user id
     * @param bookId the book id
     * @return Added {@link User}
     * @throws BookIdNotFoundException when book is not found
     */
    @PutMapping(EndPoints.PATH_CONSTANT_ID +
            EndPoints.BOOKS_PATH + EndPoints.BOOK_ID_PATH)
    public User addBook(@PathVariable Long id, @PathVariable Long bookId) {
        Optional<User> user = userRepository.findById(id);
        user.get().addBook(bookRepository.findById(bookId).orElseThrow(BookIdNotFoundException::new));
        return userRepository.save(user.get());
    }

    /**
     * Remove book user.
     *
     * @param id     the user id
     * @param bookId the book id
     * @return Removed {@link User}
     * @throws BookIdNotFoundException when book is not found
     */
    @DeleteMapping(EndPoints.PATH_CONSTANT_ID +
            EndPoints.BOOKS_PATH + EndPoints.BOOK_ID_PATH)
    public User removeBook(@PathVariable Long id, @PathVariable Long bookId) {
        Optional<User> user = userRepository.findById(id);
        user.get().removeBook(bookRepository.findById(bookId).orElseThrow(BookIdNotFoundException::new));
        return userRepository.save(user.get());
    }

    /**
     * Retrieves the username of the logged in user
     *
     * @param authentication: Authentication context (Authentication)
     * @return Username username of the logged in user
     */
    @GetMapping(value = EndPoints.USERNAME_PATH)
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }

}
