<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="bootstrap.min.css"/>
    <script type="text/javascript" src="js/jquery.1.9.1.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
</head>
<body>

    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span> Menu <i
                        class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">Simple Library</a>
            </div>

    <div class="collapse navbar-collapse"
         id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav navbar-left">
            <li class="active"></li>
            <li><a href="${pageContext.request.contextPath}/bookList">Book List</a></li>
            <li><a href="${pageContext.request.contextPath}/addBook">Add Book</a></li>
        </ul>
    </div>
        </div>
    </nav>
</div>
</body>
</html>
