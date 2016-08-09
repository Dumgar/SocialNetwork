<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="m" uri="/WEB-INF/taglib.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Friends</title>
</head>
<body>
    <c:if test="${not empty requestScope.statusbutton}">
        <form action="<c:url value="status"/>" method="get">
            <input type="submit" value="Status">
        </form>
    </c:if>
    <br>

    <c:if test="${empty requestScope.friends}">
        ${"You have no friends yet"}
    </c:if>
    <m:show list="${friends}"/>

    <%@ include file="header.jsp"%>
</body>
</html>
