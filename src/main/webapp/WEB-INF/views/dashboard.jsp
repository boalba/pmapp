<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 07.06.2020
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Witaj, <c:choose>
        <c:when test="${empty currentPerson}">
            <c:out value="Nowy Użytkowniku"></c:out>
        </c:when>
        <c:otherwise>
            <c:out value="${currentPerson.firstName}"></c:out>
        </c:otherwise>
    </c:choose>
    </title>
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
        <button onclick="w3_close()" class="w3-bar-item w3-large">Zamknij &times;</button>
            <sec:authorize access="hasAuthority('SUPERADMIN')">
                <a href="/register/user" class="w3-bar-item w3-button">Nowy użytkownik</a>
                <a href="/register/person" class="w3-bar-item w3-button">Nowy pracownik</a>
                <a href="/register/project" class="w3-bar-item w3-button">Nowy projekt</a>
                <a href="/register/team" class="w3-bar-item w3-button">Nowy zespół</a>
            </sec:authorize>
            <a href="/" class="w3-bar-item w3-button">Projekty</a>
            <a href="/" class="w3-bar-item w3-button">Pracownicy</a>
            <a href="/" class="w3-bar-item w3-button">Zespoły</a>
            <a href="/logout" class="w3-bar-item w3-button">Wyloguj</a>
    </div>

    <!-- Page Content -->
    <div class="w3-teal1">
        <button class="avatar" onclick="w3_open()">
            <img src="../css/images/logo.jpg" alt="LOGO">
        </button>

        <div class="custom3">
            <c:choose>
                <c:when test="${empty currentPersonPicture}">
                    <img class="avatar" src="../css/images/somepicture.jpg" alt="LOGO">
                </c:when>
                <c:otherwise>
                    <img class="avatar" src="<c:url value="data:image/jpg;base64,${currentPersonPicture}"/>" alt="LOGO">
                </c:otherwise>
            </c:choose>
            <div class="custom4">
                    <h1>Witaj, <c:choose>
                        <c:when test="${empty currentPerson}">
                            <c:out value="Nowy Użytkowniku"></c:out>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${currentPerson.firstName}"></c:out>
                        </c:otherwise>
                        </c:choose>
                    </h1>
            </div>
        </div>
    </div>

    <div class="w3-container">
        <p>This sidebar is hidden by default, (style="display:none")</p>
        <p>You must click on the "hamburger" icon (top left) to open it.</p>
        <p>The sidebar will hide a part of the page content.</p>
    </div>

    <script>
        function w3_open() {
            document.getElementById("mySidebar").style.display = "block";
        }

        function w3_close() {
            document.getElementById("mySidebar").style.display = "none";
        }
    </script>
</div>
</div>
</body>
</html>
