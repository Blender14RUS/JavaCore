<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Author List</title>
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
                        <legend>Author Lists</legend>
                    </div>
                    <div class="table-responsive">
                        <table id="authors" class="table table-striped table-bordered"
                               dt:table="true">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>First name</th>
                                <th>Last name</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${authorList}" var="author">
                                <tr>
                                    <td>${author.getId()}</td>
                                    <td>${author.getFirst_name()}</td>
                                    <td>${author.getLast_name()}</td>
                                    <td>
                                        <a href="editAuthor?id_author=${author.getId()}" class="btn btn-info">Edit</a>
                                    </td>
                                    <td>
                                        <a href="deleteAuthor?id_author=${author.getId()}"
                                           class="btn btn-danger">Delete</a>
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
    <a href="addAuthor" class="btn btn-info">Add Author</a>
</div>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>