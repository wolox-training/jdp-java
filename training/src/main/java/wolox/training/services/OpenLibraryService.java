package wolox.training.services;

import wolox.training.models.Book;

public interface OpenLibraryService {

    Book bookInfo(String isbn);
}
