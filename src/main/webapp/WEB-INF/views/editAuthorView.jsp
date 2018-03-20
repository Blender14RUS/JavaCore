<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Author</title>
</head>
<body>

<jsp:include page="_menu.jsp"></jsp:include>

<h3>Edit Author</h3>

<p style="color: red;">${errorString}</p>

<div align="center">
    <h1>Edit Author</h1>
    <c:if test="${not empty author}">
        <form method="POST" action="${pageContext.request.contextPath}/editAuthor">
            <input type="hidden" class="form-control" name="id_author" value="${author.getId()}"/>
            <table border="0">
                <tr>
                    <td>ID:</td>
                    <td style="color:red;" class="form-control">${author.getId()}</td>
                </tr>
                <tr>
                    <td>First name:</td>
                    <td><input type="text" class="form-control" name="first_name" value="${author.getFirst_name()}"/></td>
                </tr>
                <tr>
                    <td>Last name:</td>
                    <td><input type="text" class="form-control" name="last_name" value="${author.getLast_name()}"/>
                    </td>
                </tr>
                <p></p>
                <tr align="center">
                    <td colspan="2">
                        <input type="submit" class="btn btn-success" value="Submit"/>
                        <a href="authorList" class="btn btn-danger">Cancel</a>
                    </td>
                </tr>
            </table>
        </form>
    </c:if>
</div>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>