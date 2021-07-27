package wolox.training.utils;

public class EndPoints {
    public static final String BOOK_BASE_PATH = "/api/books";
    public static final String BOOKS_PATH = "/books";
    public static final String PATH_CONSTANT_ID = "/{id}";
    public static final String USER_BASE_PATH = "/api/users";
    public static final String BOOK_ID_PATH = "/{bookId}";
    public static final String USERNAME_PATH = "/username";
    public static final String WIREMOCK_PATH = "localhost:8080/byIsbn";

    private EndPoints() {
    }
}
