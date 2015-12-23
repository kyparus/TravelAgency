<%--
  Created by IntelliJ IDEA.
  User: yurii
  Date: 05.12.15
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="word" var="bundle"/>
<fmt:requestEncoding value="UTF-8"/>
<%
    String error = (String) session.getAttribute("msg");
%>
<head>
    <title><fmt:message key="error_label" bundle="${bundle}"/></title>
</head>
<body>
<form >
    <a href="index.jsp" style="padding-right: 55px"><fmt:message key="login_label_home" bundle="${bundle}"/></a>
</form>
<h2 align="center" style="padding-top: 240px">
    <%
        out.println(error);
    %>
</h2>
</body>
</html>
