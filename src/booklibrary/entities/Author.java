package booklibrary.entities;

public class Author {

    private int id_author;
    private String first_name;
    private String last_name;

    public Author() {
    }

    public Author(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Author(int id_author, String first_name, String last_name) {
        this.id_author = id_author;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getId() {
        return id_author;
    }

    public void setId(int id_author) {
        this.id_author = id_author;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return String.format("Author[id_author=%s, Name=%s %s]", id_author, first_name, last_name);
    }

    public String getFields() {
        return String.format("first_name = '%s', last_name = '%s'", first_name, last_name);
    }
}
//CREATE TABLE Authors (id_author INT NOT NULL PRIMARY KEY AUTO_INCREMENT, first_name CHAR(20), last_name CHAR(20));
//CREATE TABLE BooksOfAuthor (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, id_author INT NOT NULL, id_book INT NOT NULL);