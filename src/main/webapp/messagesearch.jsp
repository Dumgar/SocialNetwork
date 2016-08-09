<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="/WEB-INF/taglib.tld" %>
<html>
<head>
    <title>Message Search</title>
</head>
<body>
<form name="messagesearch"
      action="messagesearch"
      method="post">
    <input type="search" name="messagesearch" placeholder="Search">
</form>

<c:forEach items="${requestScope.messages}" var="entry" varStatus="loop">
    <c:choose>
        <c:when test="${entry.sender == sessionScope.user.id}">
            ${"Me"}:
        </c:when>
        <c:otherwise>
            ${requestScope.firstname} ${requestScope.lastname}:
        </c:otherwise>
    </c:choose>
    ${entry.message}
    ${!loop.last ? '<hr>' : ''}
</c:forEach>

<%@ include file="header.jsp"%>
</body>
</html>
