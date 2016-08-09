<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="/WEB-INF/taglib.tld" %>
<html>
<head>
    <title>People</title>
</head>
<body>
    <form name="peoplesearch"
          action="peoplesearch"
          method="post">
        <input type="search" name="peoplesearch" placeholder="Search">
    </form>

<m:show list="${users}"/>

    <%@ include file="header.jsp"%>
</body>
</html>
