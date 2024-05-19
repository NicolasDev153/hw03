package lms;

import java.util.ArrayList;
import java.util.List;

public class LMS {

    List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean removeBook(Book book) {
        boolean removed = false;
        // TODO must be implemented
        return removed;
    }

    public boolean borrowBook(Book book, Student student) {
        boolean borrowed = false;
        // TODO must be implemented
        return borrowed;
    }

    public boolean returnBook(Book book) {
        boolean returned = false;
        // TODO must be implemented
        return returned;
    }

    public void saveState(String filePath) {
        // TODO must be implemented
    }

    public boolean loadState(Book book) {
        boolean returned = false;
        // TODO must be implemented
        return returned;
    }

}
