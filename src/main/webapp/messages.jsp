<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Messages</title>
</head>
<body>
<jsp:useBean id="user" type="model.User" scope="session"/>
<c:choose>
    <c:when test="${not empty requestScope.id}">
            <c:forEach items="${requestScope.messages}" var="entry" varStatus="loop">
                <c:choose>
                    <c:when test="${entry.sender == user.id}">
                        ${"Me"}:
                    </c:when>
                    <c:otherwise>
                       ${requestScope.firstname} ${requestScope.lastname}:
                    </c:otherwise>
                </c:choose>
                ${entry.message}
                ${!loop.last ? '<hr>' : ''}
            </c:forEach>
        <br>
            <form action="<c:url value="messages"/>" method="post">
                <textarea rows="3" cols="150" name="message" placeholder="${requestScope.message}"></textarea><br>
                <input type="hidden" name="status" value="send">
                <input type="hidden" name="id" value="${requestScope.id}">
                <input type="submit" class="footerButton" value="Send">
            </form>
    </c:when>
    <c:otherwise>
            <c:forEach items="${requestScope.dialogues}" var="entry" varStatus="loop">
                <a href="<c:url value="messages?id=${entry.id}"/>" class="names">${entry.firstName} ${entry.lastName}</a>
                <form action= "<c:url value="messages"/>" method="post">
                    <input type="hidden" name="status" value="delete">
                    <input type="hidden" name="id" value="${entry.id}">
                    <input type="submit" class="deleteDialogueButton" value="Delete">
                </form>
            </c:forEach>
    </c:otherwise>
</c:choose>

<form action="messagesearch"
      method="post">
    <input type="submit" value="Search">
</form>

<%@ include file="header.jsp"%>

</body>
</html>
