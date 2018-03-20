package booklibrary.utils;

import booklibrary.entities.Author;
import booklibrary.entities.Book;

import javax.servlet.ServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME_CONNECTION, conn);
    }

    public static Connection getStoredConnection(ServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
        return conn;
    }

    public static void addBook(Connection conn, Book book) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO books (ISBN, title, year, date_upload, count_available) VALUES " +
                String.format("('%s', '%s', %s, '%s', %s)", book.getISBN(), book.getTitle(), book.getYear(), book.getDate_upload(), book.getCount_available()), Statement.RETURN_GENERATED_KEYS);
        ResultSet result = stmt.getGeneratedKeys();
        if (result.next()) {
            int book_id = result.getInt(1);
            book.getList_id_author().forEach(value -> {
                try {
                    addBooksOfAuthor(conn, value, book_id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void deleteBook(Connection conn, int id_book) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM books WHERE id_book = " + id_book);
        stmt.executeUpdate("DELETE FROM BooksOfAuthor WHERE id_book = " + id_book);
    }

    public static void updateBook(Connection conn, Book book) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE books SET " + book.getFields() + " WHERE id_book=" + book.getId());
        stmt.executeUpdate("DELETE FROM BooksOfAuthor WHERE id_book = " + book.getId());
        for (Integer id_author : book.getList_id_author()) {
            addBooksOfAuthor(conn, id_author, book.getId());
        }
    }

    public static Book getBook(Connection conn, int id_book) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM books WHERE id_book=" + id_book);
        Book book = new Book();
        if (rs.next()) {
            book.setId(rs.getInt("id_book"));
            book.setISBN(rs.getString("ISBN"));
            book.setTitle(rs.getString("title"));
            book.setYear(rs.getInt("year"));
            book.setDate_upload(rs.getString("date_upload"));
            book.setCount_available(rs.getInt("count_available"));
            book.setList_id_author(getListIdAuthors(conn, id_book));
        }
        return book;
    }

    public static List<Book> getBooks(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT id_book FROM books");
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            books.add(getBook(conn, rs.getInt("id_book")));
        }
        return books;
    }

    public static void addBooksOfAuthor(Connection conn, int id_author, int id_book) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO BooksOfAuthor (id_author, id_book) VALUES " +
                String.format("(%s, %s)", id_author, id_book));

    }

    public static void addAuthor(Connection conn, Author author) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO authors (first_name, last_name) VALUES " +
                String.format("('%s', '%s')", author.getFirst_name(), author.getLast_name()));
    }

    public static List<Integer> getListIdAuthors(Connection conn, int id_book) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT id_author FROM BooksOfAuthor WHERE id_book = " + id_book);
        List<Integer> list_id_author = new ArrayList<>();
        while (rs.next()) {
            list_id_author.add(rs.getInt("id_author"));
        }
        return list_id_author;
    }

    public static List<Book> getBooksOfAuthor(Connection conn, int id_author) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT id_book FROM BooksOfAuthor WHERE id_author = " + id_author);
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            books.add(getBook(conn, rs.getInt("id_book")));
        }
        return books;
    }

    public static List<Author> getAuthors(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM authors");
        List<Author> authors = new ArrayList<>();
        while (rs.next()) {
            authors.add(getAuthor(conn, rs.getInt("id_author")));
        }
        return authors;
    }

    public static Author getAuthor(Connection conn, int id_author) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM authors WHERE id_author = " + id_author);
        Author author = new Author();
        if (rs.next()) {
            author.setId(rs.getInt("id_author"));
            author.setFirst_name(rs.getString("first_name"));
            author.setLast_name(rs.getString("last_name"));
        }
        return author;
    }

    public static void deleteAuthor(Connection conn, int id_author) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM authors WHERE id_author = " + id_author);
        stmt.executeUpdate("DELETE FROM BooksOfAuthor WHERE id_author = " + id_author);
    }

    public static void updateAuthor(Connection conn, Author author) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE authors SET " + author.getFields() + " WHERE id_author = " + author.getId());
    }
}
