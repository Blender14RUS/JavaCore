<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Book</title>
</head>
<body>

<jsp:include page="_menu.jsp"></jsp:include>

<h3>Add Book</h3>

<p style="color: red;">${errorString}</p>

<div align="center">
    <h1>Add Book</h1>
    <form method="POST" action="${pageContext.request.contextPath}/addBook">
        <table>
            <tr>
                <td>ISBN:</td>
                <td><input type="text" class="form-control" name="isbn" value="${book.ISBN}"/></td>
            </tr>
            <tr>
                <td>Authors:</td>
                <td><input type="text" class="form-control" name="authors" value="${book.list_id_author}"/></td>
            </tr>
            <tr>
                <td>Title:</td>
                <td><input type="text" class="form-control" name="title" value="${book.title}"/></td>
            </tr>
            <tr>
                <td>Year:</td>
                <td><input type="text" class="form-control" name="year" value="${book.year}"/></td>
            </tr>
            <tr>
                <td>Date upload:</td>
                <td><input type="text" class="form-control" name="date_upload" value="${book.date_upload}"/></td>
            </tr>
            <tr>
                <td>Count available:</td>
                <td><input type="text" class="form-control" name="count_available" value="${book.count_available}"/>
                </td>
            </tr>
            <tr align="center" >
                <td colspan="2">
                    <input type="submit" class="btn btn-success" value="Submit"/>
                    <a href="bookList" class="btn btn-danger">Cancel</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>