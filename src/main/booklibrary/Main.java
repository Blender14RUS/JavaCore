package main.booklibrary;


import main.booklibrary.model.Book;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConnectH2 db = new ConnectH2();
        db.getConnection();
        List<Book> list = db.getBooks();
        for (Book a : list) {
            System.out.println(a.toString());
        }
        System.out.println("  --  ");

    }
}
