<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book List</title>
</head>
<body>

<jsp:include page="_menu.jsp"></jsp:include>

<p style="color: red;">${errorString}</p>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div>
                    <div align="center">
                        <legend>Book Lists</legend>
                    </div>
                    <div class="table-responsive">
                        <table id="myBook" class="table table-striped table-bordered"
                               dt:table="true">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>ISBN</th>
                                <th>Authors</th>
                                <th>Title</th>
                                <th>Year</th>
                                <th>Date upload</th>
                                <th>Available</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${bookList}" var="book">
                                <tr>
                                    <td>${book.getId()}</td>
                                    <td>${book.getISBN()}</td>
                                    <td>${book.getList_id_author().toString()}</td>
                                    <td>${book.getTitle()}</td>
                                    <td>${book.getYear()}</td>
                                    <td>${book.getDate_upload()}</td>
                                    <td>${book.getCount_available()}</td>
                                    <td>
                                        <a href="editBook?id_book=${book.getId()}" class="btn btn-info">Edit</a>
                                    </td>
                                    <td>
                                        <a href="deleteBook?id_book=${book.getId()}" class="btn btn-danger">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <a href="addBook" class="btn btn-info">Add Book</a>
</div>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>