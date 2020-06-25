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
        <c:when test="${empty loggedPerson}">
            <c:out value="Nowy Użytkowniku"></c:out>
        </c:when>
        <c:otherwise>
            <c:out value="${loggedPerson.firstName}"></c:out>
        </c:otherwise>
    </c:choose> w PM Application&reg
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

        <%@include file="/WEB-INF/includes/navigationBar.jsp" %>

        <!-- Page Content -->
        <div class="w3-teal1">
            <button class="avatar" onclick="w3_open()">
                <img src="../css/images/logo.jpg" alt="LOGO">
            </button>
            <button class="avatar float-right">
                <c:choose>
                    <c:when test="${empty loggedUserTeamByTeamLeader}">
                        <c:choose>
                            <c:when test="${empty loggedUserTeamByUser}">
                            </c:when>
                            <c:otherwise>
                                <a href="/team/details/${loggedUserTeamByUser.id}"><img class="avatar" src="<c:url value="data:image/jpg;base64,${loggedUserTeamByUser.image}"/>" alt="LOGO"></a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <a href="/team/details/${loggedUserTeamByTeamLeader.id}" ><img class="avatar" src="<c:url value="data:image/jpg;base64,${loggedUserTeamByTeamLeader.image}"/>" alt="LOGO"></a>
                    </c:otherwise>
                </c:choose>
            </button>
        </div>
        <div class="custom3">
            <c:choose>
                <c:when test="${empty loggedPerson}">
                    <img class="avatar" src="../css/images/somepicture.jpg" alt="LOGO">
                </c:when>
                <c:otherwise>
                    <img class="avatar" src="<c:url value="data:image/jpg;base64,${loggedPerson.image}"/>" alt="LOGO">
                </c:otherwise>
            </c:choose>
            <div class="custom4">
                <h1>Witaj, <c:choose>
                    <c:when test="${empty loggedPerson}">
                        <c:out value="Nowy Użytkowniku"></c:out>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${loggedPerson.firstName}"></c:out>
                    </c:otherwise>
                </c:choose> w PM Application&reg
                </h1>
            </div>
        </div>
        <div id="div">
        <div id="left" style="float: left; width: 35%" class="custom14 p-t-70">
            <div class="custom7">
                <p class="custom6">Masz do wykonania następujące zadania:</p>
            </div>
            <c:choose>
                <c:when test="${empty loggedAssignments}">
                    <p class="custom6">Brak zadań</p>
                </c:when>
                <c:otherwise>
                    <table class="table" style="width: 100%">
                        <c:forEach items="${loggedAssignments}" var="assignment" varStatus="count">
                            <tr>
                                <td style="height: 5px" class="custom9">${count.index + 1}.</td>
                                <td style="height: 5px" class="custom8"><a style="font-size: large; font-weight: bold" href="/assignment/details/${assignment.id}">${assignment.assignmentName}</a></td>
                                <td style="height: 5px" class="custom9">do: ${assignment.assignmentStop}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="right" style="display: inline-block; width: 45%" class="custom15 p-t-70">
            <div class="custom7">
                <p class="custom6">Obecnie uczestniczysz w następujących projektach:</p>
            </div>
            <c:choose>
                <c:when test="${empty loggedProjects}">
                    <p class="custom6">Brak projektów</p>
                </c:when>
                <c:otherwise>
                    <table class="table" style="width: 100%">
                        <c:forEach items="${loggedProjects}" var="project">
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
