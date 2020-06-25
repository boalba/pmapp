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
    <title>${currentTeam.teamName}</title>
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
                <img class="avatar" src="<c:url value="data:image/jpg;base64,${currentTeam.image}"/>" alt="LOGO">
                <div class="custom4">
                    <h1>
                        Zespół <c:out value="${currentTeam.teamName}"/>
                    </h1>
                </div>
            </div>
        </div>

        <div class="custom5">
            <p class="custom6 p-t-10 p-b-10">Nazwa zespołu: <b><c:out value="${currentTeam.teamName}"/></b></p>
            <p class="custom6 p-t-10 p-b-10">Kierownik zespołu:
                <b>
                    <c:choose>
                        <c:when test="${empty currentTeam.teamLeader}">
                            <p class="custom6">Brak kierownika zespołu</p>
                        </c:when>
                        <c:otherwise>
                            <a href="/person/details/${currentTeam.teamLeader.id}" class="custom11"><c:out value="${personDetailsOfTeamLeader.firstName} ${personDetailsOfTeamLeader.sureName}"/></a>
                        </c:otherwise>
                    </c:choose>
                </b>
            </p>
            <p class="custom6 p-t-10 p-b-10">Członkowie zespołu:<br>
                <b>
                    <c:choose>
                        <c:when test="${empty currentTeam.users}">
                            <p class="custom6">Brak członków zespołu</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${personDetailsOfTeam}" var="member">
                            <a href="/person/details/${member.id}" class="custom11"><c:out value="${member.firstName} ${member.sureName}"/></a><br>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </b>
            </p>
        </div>
        <div class="custom5">
            <div class="custom7">
                <p class="custom6 p-t-10 p-b-10"> Zespół <c:out value="${currentTeam.teamName}"/> uczestniczy w następujących projektach:</p>
            </div>
            <c:choose>
                <c:when test="${empty currentTeamProjects}">
                    <p class="custom6">Brak projektów</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <c:forEach items="${currentTeamProjects}" var="project">
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
