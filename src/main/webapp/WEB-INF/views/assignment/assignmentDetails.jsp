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
    <title>${currentAssignment.assignmentName}</title>
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
                <div class="custom4">
                    <h1>
                        <c:out value="${currentAssignment.assignmentName}"/>
                    </h1>
                </div>
            </div>
        </div>

        <div class="custom5">
            <p class="custom6 p-t-10 p-b-10">Data rozpoczęcia: <b><c:out value="${currentAssignment.assignmentStart}"/></b></p>
            <p class="custom6 p-t-10 p-b-10">Data zakończenia: <b><c:out value="${currentAssignment.assignmentStop}"/></b></p>
            <p class="custom6 p-t-10 p-b-10">Projekt: <b><a href="/project/details/${currentAssignment.project.id}" class="custom11"><c:out value="${currentAssignment.project.projectNumber} ${currentAssignment.project.projectName}"/></a></b></p>
            <p class="custom6 p-t-10 p-b-10">Osoby przypisane do zadania:<br>
                <b>
                    <c:choose>
                        <c:when test="${empty currentAssignmentPeople}">
                            <p class="custom6">Brak osób przypisanych do zadania</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${currentAssignmentPeople}" var="person">
                                <a href="/person/details/${person.id}" class="custom11"><c:out value="${person.firstName} ${person.sureName}"/></a><br>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </b>
            </p>
            <p class="custom6 p-t-10 p-b-10">Opis zadania: <br>
                <b>
                    <c:out value="${currentAssignment.description}"/>
                </b>
            </p>
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
