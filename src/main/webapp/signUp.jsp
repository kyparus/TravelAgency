<%--
  Created by IntelliJ IDEA.
  User: yurii
  Date: 05.12.15
  Time: 17:37
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

<head>
    <title><fmt:message key="index_button_signup" bundle="${bundle}"/></title>
</head>
<body>
<form>
    <a href="index.jsp" style="padding-right: 55px"><fmt:message key="login_label_home" bundle="${bundle}"/></a>
</form>

<form name="inputForm" action="handle" method="post">
    <div style="padding-left: 40%">
        <p>
            <fmt:message key="label_first_name" bundle="${bundle}"/>
            <label>
                <input type="text" name="firstName" value=""/>
            </label>
        </p>

        <p>
            <fmt:message key="label_last_name" bundle="${bundle}"/>
            <label>
                <input type="text" name="secondName" value=""/>
            </label>
        </p>

        <p>
            <fmt:message key="label_birth" bundle="${bundle}"/>
            <label>
                <input type="date" name="birth" value=""/>
            </label>
        </p>

        <p>
            <fmt:message key="label_nickname" bundle="${bundle}"/>
            <label>
                <input type="text" name="nickname" value=""/>
            </label>
        </p>

        <p>
            <fmt:message key="label_password" bundle="${bundle}"/>
            <label>
                <input type="password" name="password" value=""/>
            </label>
        </p>

    </div>

    <p style="padding-left: 40%">
        <input type="submit" name="submitRegister" value="<fmt:message key="index_button_signup" bundle="${bundle}"/>"/>
    </p>
</form>

</body>
</html>
