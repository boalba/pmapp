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
    <title>Rejestracja nowego użytkownika</title>
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
    <link rel="stylesheet" type="text/css" href="../css/cssfile.css">

    <!--===============================================================================================-->
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>

<div class="limiter">
    <div class="custom1" style="background-image: url('/css/images/construction.jpg');">
        <div class="w3-sidebar w3-bar-block w3-border-right" style="display:none" id="mySidebar">
            <button onclick="w3_close()" class="w3-bar-item w3-large">Close &times;</button>
            <a href="/" class="w3-bar-item w3-button">Główna</a>
            <a href="/logout" class="w3-bar-item w3-button">Wyloguj</a>
        </div>
        <div class="w3-teal1">
            <button class="avatar" onclick="w3_open()">
                <img src="../css/images/logo.jpg" alt="LOGO">
            </button>
        </div>
            <div class="custom2">

                <span class="login100-form-title p-b-45">
						Dodaj nowego użytkownika
                </span>

    <form:form method="post" modelAttribute="user">
        <div class="form-group">
            <label for="exampleInputEmail1">Email nowego użytkownika</label>
            <form:input path="email" type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Podaj email użytkownika"/>
            <form:errors path="email" class="error-message"/>
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Hasło dostępu nowego użytkownika</label>
            <form:input path="password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Podaj hasło użytkownika"/>
            <form:errors path="password" class="error-message"/>
        </div>
        <div class="form-group">
            <label for="exampleFormControlSelect1">Uprawnienia nowego użytkownika</label>
            <form:select path="role" class="form-control" id="exampleFormControlSelect1">
                <c:forEach items="${roles}" var="role">
                    <form:option value="${role}">${role.role}</form:option>
                </c:forEach>
            </form:select>
            <form:errors path="role" class="error-message"/>
        </div>
        <button type="submit" class="login100-form-btn">Dodaj</button>
    </form:form>

        </div>
    </div>
</div>

<script>
    function w3_open() {
        document.getElementById("mySidebar").style.display = "block";
    }

    function w3_close() {
        document.getElementById("mySidebar").style.display = "none";
    }
</script>

</body>
</html>
