package wolox.training.utils;

public class ErrorMessage {
    public static final String NOT_NULL_MESSAGE = "This field is null, please fill it!";
    public static final String BIRTHDAY_BEFORE_CURRENTDATE = "The birth date cannot be after today.";
    public static final String EMPTY_MESSAGE = "The gave object is empty, please fix it!.";
    public static final String YEAR_AFTER = "The year of publishing is greater than the current.";
    public static final String MORE_PAGES = "The # of pages needs to be greater than 0.";

    private ErrorMessage() {
    }
}
