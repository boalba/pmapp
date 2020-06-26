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
    <title>Pracownicy</title>
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

        <%@include file="/WEB-INF/includes/navigationBar.jsp" %>


        <!-- Page Content -->
        <div class="w3-teal1">
            <button class="avatar" onclick="w3_open()">
                <img src="../css/images/logo.jpg" alt="LOGO">
            </button>
        </div>
        <div class="custom5">
            <p class="custom6 p-b-10">Wyszukaj pracownika:</p>
            <div class="form-group">
                <form method="post" action="/person/search">
                    <input name="firstName" type="text" class="form-control custom12" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Imię pracownika"/>
                    <input name="sureName" type="text" class="form-control custom13" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Nazwisko pracownika"/>
                    <button type="submit" class="buttonSearch">Szukaj</button>
                </form>
            </div>
        </div>
        <div id="div">
        <div class="custom5 p-t-50">
            <div class="custom7">
            <p class="custom6">Lista wszystkich pracowników:</p>
            </div>
            <c:choose>
                <c:when test="${empty allPeople}">
                    <p class="custom6">Brak pracowników</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <c:forEach items="${allPeople}" var="person">
                            <tr>
                                <td class="custom8"><a href="/person/details/${person.id}"><img class="avatar" src="<c:url value="data:image/jpg;base64,${person.image}"/>" alt="LOGO"></a></td>
                                <td class="custom9">${person.firstName}</td>
                                <td class="custom9">${person.sureName}</td>
                                <td class="custom9">${person.birthDate}</td>
                                <td class="custom9">${person.position}</td>
                                <sec:authorize access="hasAuthority('SUPERADMIN')">
                                    <td class="custom9"><a href="/person/edit/${person.id}">Edytuj dane pracownika</a></td>
                                    <td class="custom9"><a href="/user/edit/${person.id}">Edytuj dane użytkownika</a></td>
                                    <td class="custom9"><a href="/user/editPass/${person.id}">Edytuj hasło użytkownika</a></td>
                                    <td class="custom9"><a href="/user/delete/${person.id}" onclick="return confirm('Czy napewno chcesz usunąć użytkownika: ${person.firstName} ${person.sureName}');">Usuń</a></td>
                                </sec:authorize>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
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
