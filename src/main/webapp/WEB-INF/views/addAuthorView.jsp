<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Author</title>
</head>
<body>

<jsp:include page="_menu.jsp"></jsp:include>

<h3>Add Author</h3>

<p style="color: red;">${errorString}</p>

<div align="center">
    <h1>Add Author</h1>
    <form method="POST" action="${pageContext.request.contextPath}/addAuthor">
        <table>
            <tr>
                <td>First name:</td>
                <td><input type="text" class="form-control" name="first_name" value="${author.first_name}"/></td>
            </tr>
            <tr>
                <td>Last name:</td>
                <td><input type="text" class="form-control" name="last_name" value="${author.last_name}"/></td>
            </tr>

            <tr align="center">
                <td colspan="2">
                    <input type="submit" class="btn btn-success" value="Submit"/>
                    <a href="authorList" class="btn btn-danger">Cancel</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>