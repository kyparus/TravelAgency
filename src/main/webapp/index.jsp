<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="mytag" uri="http://mycompany.com" %>
<html>
<head>
    <title>Dream Travel</title>
</head>
<body>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="word" var="bundle"/>
<fmt:requestEncoding value="UTF-8"/>

<br>

<div>
    <a href="/enen"><mytag:lang>en</mytag:lang></a>
    <a href="/uaua"><mytag:lang>ua</mytag:lang></a>
</div>

<br>
<br>

<h1 align="center">Dream Travel</h1>

<h3 align="center"><fmt:message key="index_label_slogan1" bundle="${bundle}"/></h3>

<p align="center"><fmt:message key="index_label_slogan2" bundle="${bundle}"/></p>

<form name="inputForm" action="logOrReg" method="get" style="align-content: center">
    <p align="center">
        <input type="submit" name="submitLogin" value="<fmt:message key="index_button_signin" bundle="${bundle}"/>"/>
    </p>

    <p align="center">
        <input type="submit" name="submitRegister" value="<fmt:message key="index_button_signup" bundle="${bundle}"/>"/>
    </p>
</form>
</body>
</html>
