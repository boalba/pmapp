<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 12.06.2020
  Time: 08:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${currentPersonDetails.firstName} ${currentPersonDetails.sureName}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="/">
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

            <div class="custom3">
                <img class="avatar" src="<c:url value="data:image/jpg;base64,${currentPersonDetails.image}"/>" alt="LOGO">
                <div class="custom4">
                    <h1>
                        <c:out value="${currentPersonDetails.firstName} ${currentPersonDetails.sureName}"/>
                    </h1>
                </div>
            </div>
        </div>

        <div class="custom5">
            <p class="custom6 p-t-10 p-b-10">Imię i nazwisko: <b><c:out value="${currentPersonDetails.firstName} ${currentPersonDetails.sureName}"/></b></p>
            <p class="custom6 p-t-10 p-b-10">Data urodzenia: <b><c:out value="${currentPersonDetails.birthDate}"/></b></p>
            <p class="custom6 p-t-10 p-b-10">Stanowisko: <b><c:out value="${currentPersonDetails.position}"/></b></p>
            <p class="custom6 p-t-10 p-b-10">Zespół:
                <b><c:choose>
                    <c:when test="${empty currentPersonTeamByTeamLeader}">
                        <c:choose>
                            <c:when test="${empty currentPersonTeamByUser}">
                            brak
                            </c:when>
                            <c:otherwise>
                                Członek zespołu <a href="/team/details/${currentPersonTeamByUser.id}" class="custom11"><c:out value="${currentPersonTeamByUser.teamName}"/></a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        Kierownik zespołu <a href="/team/details/${currentPersonTeamByTeamLeader.id}" class="custom11"><c:out value="${currentPersonTeamByTeamLeader.teamName}"/></a>
                    </c:otherwise>
                </c:choose></b>
            <p class="custom6 p-t-10 p-b-10">Charakterystyka: <br>
                <b>
                    <c:out value="${currentPersonDetails.description}"/>
                </b>
            </p>
        </div>
        <div class="custom5">
            <div class="custom7">
                <p class="custom6 p-t-10 p-b-10"><c:out value="${currentPersonDetails.firstName}"/> uczestniczy obecnie w następujących projektach:</p>
            </div>
            <c:choose>
                <c:when test="${empty currentPersonProjects}">
                    <p class="custom6">Brak projektów</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <c:forEach items="${currentPersonProjects}" var="project">
                            <tr>
                                <td class="custom8"><a href="/project/details/${project.id}"><img class="avatar" src="<c:url value="data:image/jpg;base64,${project.image}"/>" alt="LOGO"></a></td>
                                <td class="custom9">${project.projectNumber}</td>
                                <td class="custom9">${project.hash}</td>
                                <td class="custom9">${project.projectName}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
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
