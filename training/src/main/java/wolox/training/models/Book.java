package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static com.google.common.base.Preconditions.checkNotNull;
import static wolox.training.utils.ErrorMessage.NOT_NULL_MESSAGE;

/**
 * The class Book model.
 */
@Entity
@Getter
@Setter
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
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        checkNotNull(author, NOT_NULL_MESSAGE);
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        checkNotNull(image, NOT_NULL_MESSAGE);
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        checkNotNull(title, NOT_NULL_MESSAGE);
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        checkNotNull(subtitle, NOT_NULL_MESSAGE);
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        checkNotNull(publisher, NOT_NULL_MESSAGE);
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        checkNotNull(year, NOT_NULL_MESSAGE);
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        checkNotNull(pages, NOT_NULL_MESSAGE);
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        checkNotNull(isbn, NOT_NULL_MESSAGE);
        this.isbn = isbn;
    }

}
