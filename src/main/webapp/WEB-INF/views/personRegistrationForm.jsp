<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 31.05.2020
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rejestracja nowego pracownika</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="../css/images/icons/mw.ico"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../css/vendor/bootstrap/css/bootstrap.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../css/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../css/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../css/vendor/animate/animate.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../css/vendor/css-hamburgers/hamburgers.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../css/vendor/select2/select2.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../css/css/util.css">
    <link rel="stylesheet" type="text/css" href="../css/css/main.css">
    <!--===============================================================================================-->
</head>
<body>

<div class="limiter">
    <div class="container-login100" style="background-image: url('/css/images/construction.jpg');">
        <div class="wrap-login100 p-t-30 p-b-30">
                <div class="btn-group, text-center" role="group" aria-label="Basic example">
                    <a href="/">
                        <button type="button" class="btn btn-primary">Menu</button>
                    </a>
                    <a href="/logout">
                        <button type="button" class="btn btn-primary">Wyloguj</button>
                    </a>
                </div>

                <span class="login100-form-title p-t-20 p-b-45">
						Dodaj nowego pracownika
                </span>

    <form:form method="post" modelAttribute="personDetails" enctype="multipart/form-data">
        <div class="form-group">
            <label for="exampleFormControlSelect1">Email pracownika</label><br>
            <form:select path="user" class="form-control" id="exampleFormControlSelect1">
                <c:forEach items="${usersWithoutDetails}" var="user">
                    <form:option value="${user}">${user.email}</form:option>
                </c:forEach>
            </form:select>
            <form:errors path="user" class="error-message"/>
        </div>
        <div class="form-group">
            <label for="exampleInputEmail1">Imię pracownika</label>
            <form:input path="firstName" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Podaj imię pracownika"/>
            <form:errors path="firstName" class="error-message"/>
        </div>
        <div class="form-group">
            <label for="exampleInputEmail1">Nazwisko pracownika</label>
            <form:input path="sureName" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Podaj nazwisko pracownika"/>
            <form:errors path="sureName" class="error-message"/>
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Data urodzenia pracownika</label>
            <form:input path="birthDate" type="date" class="form-control" id="exampleInputPassword1" placeholder="Podaj datę urodzenia pracownika"/>
            <form:errors path="birthDate" class="error-message"/>
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Opis pracownika</label>
            <form:textarea path="description" class="form-control" id="exampleInputPassword1" placeholder="Podaj opis pracownika"/>
            <form:errors path="description" class="error-message"/>
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Zdjęcie pracownika</label>
            <input type="file" name="file" class="form-control" id="exampleInputPassword1"/>
        </div>
        <button type="submit" class="login100-form-btn">Dodaj</button>
    </form:form>

        </div>
    </div>
</div>

</body>
</html>
