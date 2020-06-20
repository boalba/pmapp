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
    <title>Edycja zespołu</title>
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

        <div class="w3-teal1">
            <button class="avatar" onclick="w3_open()">
                <img src="../css/images/logo.jpg" alt="LOGO">
            </button>
        </div>
        <div class="custom2">

                <span class="login100-form-title p-b-25">
						Edytuj zespół
                </span>

    <form:form method="post" modelAttribute="team" enctype="multipart/form-data">
        <div class="form-group">
        </div>
        <div class="form-group">
            <label for="exampleInputEmail1">Nazwa zespołu</label>
            <form:input path="teamName" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Podaj nazwę zespołu"/>
            <form:errors path="teamName" class="error-message"/>
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Zdjęcie zespołu</label>
            <input type="file" name="fileTeam" class="form-control" id="exampleInputPassword1"/>
        </div>
        <div class="form-group">
            <label for="exampleFormControlSelect1">Kierownik zespołu</label>
            <form:select path="teamLeader" multiple="true" class="form-control" id="exampleFormControlSelect1">
                <form:option value="${currentTeamLeader.id}">${currentTeamLeader.firstName} ${currentTeamLeader.sureName}</form:option>
                <c:forEach items="${peopleOfCurrentTeam}" var="person">
                    <form:option value="${person.id}">${person.firstName} ${person.sureName}</form:option>
                </c:forEach>
                <c:forEach items="${peopleWithoutTeam}" var="person">
                    <form:option value="${person.id}">${person.firstName} ${person.sureName}</form:option>
                </c:forEach>
            </form:select>
            <form:errors path="teamLeader" class="error-message"/>
        </div>
        <div class="form-group">
            <label for="exampleFormControlSelect1">Członkowie zespołu</label>
            <form:select path="users" multiple="true" class="form-control" id="exampleFormControlSelect1">
                <form:option value="${currentTeamLeader.id}">${currentTeamLeader.firstName} ${currentTeamLeader.sureName}</form:option>
                <c:forEach items="${peopleOfCurrentTeam}" var="person">
                    <form:option value="${person.id}">${person.firstName} ${person.sureName}</form:option>
                </c:forEach>
                <c:forEach items="${peopleWithoutTeam}" var="person">
                    <form:option value="${person.id}">${person.firstName} ${person.sureName}</form:option>
                </c:forEach>
            </form:select>
            <form:errors path="users" class="error-message"/>
        </div>
        <div class="form-group">
            <label for="exampleFormControlSelect1">Projekty zespołu</label>
            <form:select path="projects" multiple="true" class="form-control" id="exampleFormControlSelect1">
                <c:forEach items="${projectsOfCurrentTeam}" var="project">
                    <form:option value="${project.id}">${project.projectNumber} ${project.projectName}</form:option>
                </c:forEach>
                <c:forEach items="${projectsWithoutTeams}" var="project">
                    <form:option value="${project.id}">${project.projectNumber} ${project.projectName}</form:option>
                </c:forEach>
            </form:select>
            <form:errors path="projects" class="error-message"/>
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
