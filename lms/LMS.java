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
            boolean isBooksSection = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("Books:")) {
                    isBooksSection = true;
                    continue;
                }

                if (isBooksSection) {
                    if (line.trim().startsWith("Title:")) {
                        String title = line.substring(line.indexOf(":") + 1, line.indexOf("Author:")).trim();
                        String author = line.substring(line.lastIndexOf(":") + 1).trim();
                        Book book = new Book(title, author);
                        books.add(book);
                    } else if (line.trim().startsWith("Borrowed by:")) {
                        String name = line.substring(line.indexOf("Name:") + 5, line.indexOf("Surname:")).trim();
                        String surname = line.substring(line.indexOf("Surname:") + 9, line.indexOf("Personal Number:")).trim();
                        String personalNumber = line.substring(line.lastIndexOf(":") + 1).trim();
                        Student student = new Student(name, surname, personalNumber);
                        Book lastBook = books.get(books.size() - 1);
                        borrowedBooks.put(lastBook, student);
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}