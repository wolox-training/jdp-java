package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import wolox.training.exceptions.BookAlreadyOwnedException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static wolox.training.utils.ErrorMessage.BIRTHDAY_BEFORE_CURRENTDATE;
import static wolox.training.utils.ErrorMessage.NOT_NULL_MESSAGE;

@Entity
@Table(name = "users")
@ApiModel(description = "Users from database")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The user username")
    private String username;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The user fullname")
    private String name;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The birth date of the user")
    private LocalDate birthdate;

    @Column(nullable = false)
    @OneToMany
    @ApiModelProperty(notes = "The user books: books associated to user")
    private List<Book> books = new ArrayList<>();

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        checkNotNull(username, NOT_NULL_MESSAGE);
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkNotNull(name, NOT_NULL_MESSAGE);
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        checkNotNull(birthdate, NOT_NULL_MESSAGE);
        checkArgument(birthdate.isBefore(LocalDate.now()), BIRTHDAY_BEFORE_CURRENTDATE);
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        checkNotNull(books, NOT_NULL_MESSAGE);
        this.books = books;
    }

    /**
     * This method add book in collection {@link #books}
     *
     * @param book {@link Book}: Data to create
     * @throws BookAlreadyOwnedException when book is already associated to user
     */
    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException();
        }
        books.add(book);
    }

    /**
     * This method remove a book of a collection
     *
     * @param book {@link Book}: Book to remove
     */
    public void removeBook(Book book) {
        books.remove(book);
    }
}
