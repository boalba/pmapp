<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 11.06.2020
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="w3-sidebar w3-bar-block w3-border-right" style="display:none" id="mySidebar">
    <button onclick="w3_close()" class="w3-bar-item w3-large">Zamknij &times;</button>
    <a href="/" class="w3-bar-item w3-button">Główna</a>
    <sec:authorize access="hasAuthority('SUPERADMIN')">
        <a href="/user/register" class="w3-bar-item w3-button">Nowy użytkownik</a>
        <a href="/person/register" class="w3-bar-item w3-button">Nowy pracownik</a>
        <a href="/project/register" class="w3-bar-item w3-button">Nowy projekt</a>
        <a href="/team/register" class="w3-bar-item w3-button">Nowy zespół</a>
    </sec:authorize>
    <a href="/project/allProjects" class="w3-bar-item w3-button">Projekty</a>
    <a href="/person/allPeople" class="w3-bar-item w3-button">Pracownicy</a>
    <a href="/team/allTeams" class="w3-bar-item w3-button">Zespoły</a>
    <a href="/logout" class="w3-bar-item w3-button">Wyloguj</a>
</div>
</body>
</html>
