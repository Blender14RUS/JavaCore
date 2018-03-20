package servlet;

import booklibrary.entities.Author;
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


@WebServlet(urlPatterns = {"/editAuthor"})
public class EditAuthorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditAuthorServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = DBUtils.getStoredConnection(request);

        String id_author = (String) request.getParameter("id_author");

        String errorString = null;
        Author author = null;
        try {
            author = DBUtils.getAuthor(conn, Integer.parseInt(id_author));
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        if (errorString != null && author == null) {
            response.sendRedirect(request.getServletPath() + "/authorList");
            return;
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("author", author);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/editAuthorView.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = DBUtils.getStoredConnection(request);
        String id_author_str = (String) request.getParameter("id_author");
        String first_name = (String) request.getParameter("first_name");
        String last_name = (String) request.getParameter("last_name");

        String errorString = null;
        Author author = null;
        try {
            int id_author = 0;
            id_author = Integer.parseInt(id_author_str);
            author = new Author(id_author, first_name, last_name);
            DBUtils.updateAuthor(conn, author);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("author", author);

        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/editAuthorView.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/authorList");
        }
    }

}