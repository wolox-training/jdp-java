package wolox.training.models.dto;

import java.util.List;

public class BookDTO {
    private String ISBN;
    private String url;
    private String key;
    private String pagination;
    private IdentifierDTO identifiers;
    private ClassificationDTO classifications;
    private List<PlaceDTO> publish_places;
    private List<SubjectDTO> subjects;
    private CoverDTO cover;
    private String title;
    private String subtitle;
    private List<PublisherDTO> publishers;
    private String publish_date;
    private int number_of_pages;
    private List<AuthorDTO> authors;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public IdentifierDTO getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(IdentifierDTO identifiers) {
        this.identifiers = identifiers;
    }

    public ClassificationDTO getClassifications() {
        return classifications;
    }

    public void setClassifications(ClassificationDTO classifications) {
        this.classifications = classifications;
    }

    public List<PlaceDTO> getPublish_places() {
        return publish_places;
    }

    public void setPublish_places(List<PlaceDTO> publish_places) {
        this.publish_places = publish_places;
    }

    public List<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) {
        this.subjects = subjects;
    }

    public CoverDTO getCover() {
        return cover;
    }

    public void setCover(CoverDTO cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<PublisherDTO> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<PublisherDTO> publishers) {
        this.publishers = publishers;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public int getNumber_of_pages() {
        return number_of_pages;
    }

    public void setNumber_of_pages(int number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }
}
