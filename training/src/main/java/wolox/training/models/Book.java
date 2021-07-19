package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static wolox.training.utils.ErrorMessage.*;

/**
 * The class Book model.
 */
@Entity
@ApiModel(description = "Books from database")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @ApiModelProperty(notes = "The book genre")
    private String genre;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The book's author")
    private String author;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The book image")
    private String image;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The book title")
    private String title;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The book subtitle")
    private String subtitle;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The book's publisher")
    private String publisher;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The book year of publishing")
    private String year;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The number of pages the book has")
    private Integer pages;

    @Column(nullable = false)
    @ApiModelProperty(notes = "The code of the book")
    private String isbn;


    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        checkNotNull(genre, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(genre), EMPTY_MESSAGE);
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        checkNotNull(author, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(author), EMPTY_MESSAGE);
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        checkNotNull(image, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(image), EMPTY_MESSAGE);
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        checkNotNull(title, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(title), EMPTY_MESSAGE);
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        checkNotNull(subtitle, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(subtitle), EMPTY_MESSAGE);
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        checkNotNull(publisher, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(publisher), EMPTY_MESSAGE);
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        checkNotNull(year, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(year), EMPTY_MESSAGE);
        checkArgument(Long.parseLong(year) < LocalDate.now().getYear(), YEAR_AFTER);
        this.year = year;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        checkNotNull(pages, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(pages), EMPTY_MESSAGE);
        checkArgument(pages > 0, MORE_PAGES);
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        checkNotNull(isbn, NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(isbn), EMPTY_MESSAGE);
        this.isbn = isbn;
    }

}
