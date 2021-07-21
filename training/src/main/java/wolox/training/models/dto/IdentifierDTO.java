package wolox.training.models.dto;

import java.util.List;

public class IdentifierDTO {

    private List<String> librarything;
    private List<String> goodreads;
    private List<String> isbn_10;
    private List<String> lccn;
    private List<String> openlibrary;

    public List<String> getLibrarything() {
        return librarything;
    }

    public void setLibrarything(List<String> librarything) {
        this.librarything = librarything;
    }

    public List<String> getGoodreads() {
        return goodreads;
    }

    public void setGoodreads(List<String> goodreads) {
        this.goodreads = goodreads;
    }

    public List<String> getIsbn_10() {
        return isbn_10;
    }

    public void setIsbn_10(List<String> isbn_10) {
        this.isbn_10 = isbn_10;
    }

    public List<String> getLccn() {
        return lccn;
    }

    public void setLccn(List<String> lccn) {
        this.lccn = lccn;
    }

    public List<String> getOpenlibrary() {
        return openlibrary;
    }

    public void setOpenlibrary(List<String> openlibrary) {
        this.openlibrary = openlibrary;
    }
}
