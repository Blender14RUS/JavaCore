<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Book</title>
</head>
<body>

<jsp:include page="_menu.jsp"></jsp:include>

<h3>Edit Book</h3>

<p style="color: red;">${errorString}</p>

<div align="center">
    <h1>Edit Book</h1>
    <c:if test="${not empty book}">
        <form method="POST" action="${pageContext.request.contextPath}/editBook">
            <input type="hidden" class="form-control" name="id_book" value="${book.getId()}"/>
            <table border="0">
                <tr>
                    <td>ID:</td>
                    <td style="color:red;" class="form-control">${book.getId()}</td>
                </tr>
                <tr>
                    <td>ISBN:</td>
                    <td><input type="text" class="form-control" name="isbn" value="${book.ISBN}"/></td>
                </tr>
                <tr>
                    <td>Authors:</td>
                    <td><input type="text" class="form-control" name="authors" value="${book.getStringListIdAuthor()}"/>
                    </td>
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
                <p></p>
                <tr align="center">
                    <td colspan="2">
                        <input type="submit" class="btn btn-success" value="Submit"/>
                        <a href="bookList" class="btn btn-danger">Cancel</a>
                    </td>
                </tr>
            </table>
        </form>
    </c:if>
</div>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>