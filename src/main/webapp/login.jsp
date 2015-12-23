<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="word" var="bundle"/>
<fmt:requestEncoding value="UTF-8"/>
<html>
<head>
    <title><fmt:message key="login_label_head" bundle="${bundle}"/></title>
</head>
<body>


<form >
    <a href="index.jsp" style="padding-right: 55px"><fmt:message key="login_label_home" bundle="${bundle}"/></a>
</form>

<form name="inputForm" action="handle" method="post">
    <div align="center">
        <p>
            <fmt:message key="login_label_username" bundle="${bundle}"/>:
            <label>
                <input type="text" name="login" value=""/>
            </label>
        </p>

        <p>
            <fmt:message key="login_label_password" bundle="${bundle}"/>:
            <label>
                <input type="password" name="password" value=""/>
            </label>
        </p>
    </div>

    <p align="center">
        <input type="submit" name="submitLogin" value="<fmt:message key="index_button_signin" bundle="${bundle}"/>"/>
    </p>
</form>


</body>
</html>
