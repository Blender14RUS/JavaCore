package main.booklibrary;

import java.security.PrivateKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.booklibrary.model.Author;
import main.booklibrary.model.Book;

public class ConnectH2 {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL;

    //  Database credentials
    private String USER;
    private String PASS;

    private Connection conn = null;

    public ConnectH2() {
        DB_URL = "jdbc:h2:~/db/library;IFEXISTS=TRUE";
        USER = "root";
        PASS = "745180";
    }

    private ConnectH2(String user, String password, String dbURL) {
        USER = user;
        PASS = password;
        DB_URL = dbURL;
    }

    public void getConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Driver isn't connected");
        }
    }

    public void closeConnection() throws SQLException {
        if (conn == null)
            return;
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addBook(Book book) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO books (ISBN, id_author, title, year, date_upload, count_available) VALUES " +
                    String.format("('%s', '%s', %s, '%s', %s)", book.getISBN(), book.getTitle(), book.getYear(), book.getDate_upload(), book.getCount_available()));
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Book getBook(int id) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM books WHERE id=" + id);
            Book book = new Book();
            book.setId(rs.getInt("id_book"));
            book.setISBN(rs.getString("ISBN"));
            book.setTitle(rs.getString("title"));
            book.setYear(rs.getInt("year"));
            book.setDate_upload(rs.getString("date_upload"));
            book.setCount_available(rs.getInt("count_available"));
            return book;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Book> getBooks() {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM books");
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id_book"));
                book.setISBN(rs.getString("ISBN"));
                book.setTitle(rs.getString("title"));
                book.setYear(rs.getInt("year"));
                book.setDate_upload(rs.getString("date_upload"));
                book.setCount_available(rs.getInt("count_available"));
                books.add(book);
            }
            return books;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteBook(int id) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM books WHERE id = " + id);
            stmt.executeUpdate("DELETE FROM BooksOfAuthor WHERE id_book = " + id);
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBook(int id_book, Book book) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE books SET " + book.getFields() + "WHERE ID=" + id_book);
            stmt.executeUpdate("DELETE FROM BooksOfAuthor WHERE id_book = " + id_book);
            for (Integer id_author : book.getList_id_author()){
                stmt.executeUpdate("INSERT INTO BooksOfAuthor (id_author, id_book) VALUES" + String.format("(%s, %s)", id_author, id_book));
            }

            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addBooksOfAuthor(int id_author, int id_book) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO BooksOfAuthor (id_author, id_book) VALUES " +
                    String.format("(%s, %s)", id_author, id_book));
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addAuthor(Author author) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO authors (first_name, last_name) VALUES " +
                    String.format("('%s', '%s')", author.getFirst_name(), author.getLast_name()));
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Book> getBooksOfAuthor(int id_author) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id_book FROM BooksOfAuthor WHERE id_author = " + id_author);
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(getBook(rs.getInt("id_book")));
            }
            return books;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Author> getAuthors() {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM authors");
            List<Author> authors = new ArrayList<>();
            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("id_author"));
                author.setBooks(getBooksOfAuthor(rs.getInt("id_author")));
                author.setFirst_name(rs.getString("first_name"));
                author.setLast_name(rs.getString("last_name"));
                authors.add(author);
            }
            return authors;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> getAuthors(int id_books) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id_author FROM BooksOfAuthor WHERE id_book = " + id_books);
            List<Integer> authors = new ArrayList<>();
            while (rs.next()) {
                authors.add(rs.getInt("id_author"));
            }
            return authors;
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
