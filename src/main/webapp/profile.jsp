<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="date" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<c:choose>
    <c:when test="${not empty requestScope.id}">
        <jsp:useBean id="id" type="model.User" scope="request"/>
        <c:set var="user" value="${id}"/>
    </c:when>
    <c:otherwise>
        <jsp:useBean id="user" type="model.User" scope="session"/>
        <c:set var="user" value="${user}"/>
    </c:otherwise>
</c:choose>

<ul>
    <li><c:out value="${user.firstName}"/></li>
    <li><c:out value="${user.lastName}"/></li>
    <li><c:out value="${user.country}"/></li>
    <li><date:localDate date="${user.birthDate}" pattern="dd-MM-yyyy"/> </li>
</ul>
<c:choose>
<c:when test="${not empty requestScope.id}">
    <a href="<c:url value="messages?id=${user.id}"/>" class="names">Write a message</a>
    <br>
    <c:choose>
        <c:when test="${requestScope.status == 0}">
            <c:choose>
                <c:when test="${user.id == requestScope.senderId}">
                    <form action="<c:url value="friends"/>" method="post">
                        <input type="hidden" name="status" value="accept">
                        <input type="hidden" name="id" value="${user.id}">
                        <input type="submit" value="Accept">
                    </form>
                    <form action="<c:url value="friends"/>" method="post">
                        <input type="hidden" name="status" value="decline">
                       <input type="hidden" name="id" value="${user.id}">
                       <input type="submit" value="Decline">
                    </form>
                </c:when>
            </c:choose>
        </c:when>
        <c:when test="${requestScope.status == 1}">
                    <form action="<c:url value="friends"/>" method="post">
                        <input type="hidden" name="status" value="delete">
                        <input type="hidden" name="id" value="${user.id}">
                        <input type="submit" value="Delete">
                    </form>
        </c:when>
        <c:otherwise>
                    <form action="<c:url value="friends"/>" method="post">
                        <input type="hidden" name="status" value="add">
                        <input type="hidden" name="id" value="${user.id}">
                        <input type="submit" value="Add">
                    </form>
        </c:otherwise>
    </c:choose>
</c:when>
<c:otherwise>
    <a href="<c:url value="people.jsp"/>" ></a>
</c:otherwise>
</c:choose>

<%@ include file="header.jsp"%>
</body>
</html>
