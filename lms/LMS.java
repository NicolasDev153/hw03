package lms.lms;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LMS {

    private List<Book> books = new ArrayList<>();
    private Map<Book, Student> borrowedBooks = new HashMap<>();

    /**
     * Adds a book to the LMS.
     *
     * @param book the book to be added
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Removes a book from the LMS.
     *
     * @param book the book to be removed
     * @return true if the book was successfully removed, false otherwise
     */
    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    /**
     * Borrows a book for a student.
     *
     * @param book    the book to be borrowed
     * @param student the student borrowing the book
     * @return true if the book was successfully borrowed, false otherwise
     */
    public boolean borrowBook(Book book, Student student) {
        if (!borrowedBooks.containsKey(book)) {
            borrowedBooks.put(book, student);
            return true;
        }
        return false;
    }

    /**
     * Returns a borrowed book.
     *
     * @param book the book to be returned
     * @return true if the book was successfully returned, false otherwise
     */
    public boolean returnBook(Book book) {
        if (borrowedBooks.containsKey(book)) {
            borrowedBooks.remove(book);
            return true;
        }
        return false;
    }

    /**
     * Saves the current state of the LMS to a file.
     *
     * @param filePath the path of the file to save the state
     */
    public void saveState(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Books:");
            writer.newLine();

            for (Book book : books) {
                String line = String.format("Title: %-30s Author: %-20s", book.getTitle(), book.getAuthor());
                writer.write(line);
                writer.newLine();

                if (borrowedBooks.containsKey(book)) {
                    Student student = borrowedBooks.get(book);
                    String borrowedBy = String.format("Borrowed by: Name: %-20s Surname: %-20s Personal Number: %-20s",
                            student.getName(), student.getSurname(), student.getPersonalNumber());
                    writer.write(borrowedBy);
                    writer.newLine();
                }

                writer.newLine(); // Additional blank line after each book
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the state of the LMS from a file.
     *
     * @param filePath the path of the file to load the state from
     * @return true if the state was successfully loaded, false otherwise
     */
    public boolean loadState(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            books.clear();
            borrowedBooks.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String title = parts[0].trim();
                String author = parts[1].trim();
                Book book = new Book(title, author);
                books.add(book);
                if (parts.length > 2) {
                    String name = parts[2].trim();
                    String surname = parts[3].trim();
                    String personalNumber = parts[4].trim();
                    Student student = new Student(name, surname, personalNumber);
                    borrowedBooks.put(book, student);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}