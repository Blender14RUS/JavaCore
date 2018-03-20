package booklibrary.servlet;


import booklibrary.entities.Book;
import booklibrary.utils.DBUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/editBook"})
public class EditBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditBookServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = DBUtils.getStoredConnection(request);

        String id_book = (String) request.getParameter("id_book");

        String errorString = null;
        Book book = null;

        try {
            book = DBUtils.getBook(conn, Integer.parseInt(id_book));
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        if (errorString != null && book == null) {
            response.sendRedirect(request.getServletPath() + "/bookList");
            return;
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("book", book);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/editBookView.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = DBUtils.getStoredConnection(request);
        String id_book_str = (String) request.getParameter("id_book");
        String isbn = (String) request.getParameter("isbn");
        String title = (String) request.getParameter("title");
        String yearStr = (String) request.getParameter("year");
        String date_upload = (String) request.getParameter("date_upload");
        String count_available_str = (String) request.getParameter("count_available");
        String authors_srt = (String) request.getParameter("authors");

        String errorString = null;
        Book book = null;
        try {
            int id_book, year, count_available = 0;
            year = Integer.parseInt(yearStr);
            id_book = Integer.parseInt(id_book_str);
            count_available = Integer.parseInt(count_available_str);
            List<Integer> list_id_author = new ArrayList<>();
            Arrays.asList(authors_srt.split(" ")).forEach(id -> list_id_author.add(Integer.parseInt(id)));

            book = new Book(id_book, isbn, list_id_author, title, year, date_upload, count_available);

            DBUtils.updateBook(conn, book);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("book", book);

        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/editBookView.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/bookList");
        }
    }

}