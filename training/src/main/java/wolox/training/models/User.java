package wolox.training.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.util.ObjectUtils;
import wolox.training.exceptions.BookAlreadyOwnedException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static wolox.training.utils.ErrorMessage.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@ApiModel(description = "Users from database")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @ApiModelProperty(notes = "The user username")
    private String username;

    @NonNull
    @ApiModelProperty(notes = "The user fullname")
    private String name;

    @NonNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @ApiModelProperty(notes = "The birth date of the user")
    private LocalDate birthdate;

    @NonNull
    @ApiModelProperty(notes = "The user password")
    private String password;

    @NonNull
    @ManyToMany
    @JoinTable(name = "book_user",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @ApiModelProperty(notes = "The user books: books associated to user")
    private List<Book> books = new ArrayList<>();


    public void setId(Long id) {
        this.id = id;
    }


    public void setUsername(String username) {
        checkNotNull(username, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(username), EMPTY_MESSAGE);
        this.username = username;
    }

    public void setName(String name) {
        checkNotNull(name, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(name), EMPTY_MESSAGE);
        this.name = name;
    }


    public void setBirthdate(LocalDate birthdate) {
        checkNotNull(birthdate, NOT_NULL_MESSAGE);
        checkArgument(birthdate.isBefore(LocalDate.now()), BIRTHDAY_BEFORE_CURRENTDATE);
        this.birthdate = birthdate;
    }


    public void setBooks(List<Book> books) {
        checkNotNull(books, NOT_NULL_MESSAGE);
        this.books = books;
    }


    public void setPassword(String password) {
        this.password = password;
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
