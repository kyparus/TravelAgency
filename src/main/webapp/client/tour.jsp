<%@ page import="kyparus.Tour.Tour" %>
<%@ page import="kyparus.user.Client" %>
<%@ page import="kyparus.TravelAgency" %>
<%--
  Created by IntelliJ IDEA.
  User: yurii
  Date: 06.12.15
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="word" var="bundle"/>
<fmt:requestEncoding value="UTF-8"/>
<%
    Client client = (Client) session.getAttribute("client");
    Tour tour = (Tour) session.getAttribute("tour");
%>
<html>
<head>
    <title><fmt:message key="label_head_tour" bundle="${bundle}"/></title>
</head>
<body>

<h2 align="center">
    <%
        String msg = (String) session.getAttribute("msg");
        if (msg != null)
            out.println(msg);
        session.removeAttribute("msg");
    %>

</h2>

<form>
    <a href="../index.jsp" style="padding-right: 55px"><fmt:message key="label_exit" bundle="${bundle}"/></a>
</form>

<form>
    <a href="/clientHome" style="padding-right: 55px"><fmt:message key="login_label_home" bundle="${bundle}"/></a>
</form>

<form>
    <p style="padding-left: 75%">
        <fmt:message key="label_client" bundle="${bundle}"/> <br>
        <fmt:message key="label_first_name" bundle="${bundle}"/> <%= client.getFirstName() %> <br>
        <fmt:message key="label_last_name" bundle="${bundle}"/> <%= client.getLastName() %> <br>
        <fmt:message key="label_nickname" bundle="${bundle}"/> <%= client.getNickName() %> <br>
        <fmt:message key="label_birth" bundle="${bundle}"/> <%= client.getBirth() %> <br>
        <fmt:message key="label_usage" bundle="${bundle}"/> <%= client.getUsageTimes() %> <br>
    </p>
</form>

<form name="inputBooking" action="handle" method="post">
    <p align="right">
        <input type="submit" name="order" value="<fmt:message key="button_order" bundle="${bundle}"/>"/>
    </p>
</form>

<h2 align="center">
    <%
        out.println(tour.getName());
    %>
</h2>

<table align="center" style="background: border-box">
    <tr>
        <td><fmt:message key="label_to" bundle="${bundle}"/></td>
        <td>
            <% out.println(tour.getTransToLocation()); %>
        </td>
    </tr>

    <tr>
        <td><fmt:message key="label_from" bundle="${bundle}"/></td>
        <td><% out.println(tour.getTransFromLocation()); %></td>
    </tr>

    <tr>
        <td><fmt:message key="label_hours_to" bundle="${bundle}"/><% out.println(tour.getTransToLocation()); %></td>
        <td><% out.println(tour.getDurationTo()); %></td>
    </tr>

    <tr>
        <td><fmt:message key="label_return_to" bundle="${bundle}"/> <% out.println(tour.getTransFromLocation()); %></td>
        <td><% out.println(tour.getDurationFrom()); %></td>
    </tr>

    <tr>
        <td><fmt:message key="label_departure" bundle="${bundle}"/></td>
        <td><% out.println(tour.getDeparture()); %></td>
    </tr>

    <tr>
        <td><fmt:message key="label_arrival" bundle="${bundle}"/></td>
        <td><% out.println(tour.getArrival()); %></td>
    </tr>

    <tr>
        <td><fmt:message key="label_price" bundle="${bundle}"/></td>
        <td><% out.println(tour.getPrice()); %></td>
    </tr>

    <tr>
        <td><fmt:message key="label_hot" bundle="${bundle}"/></td>
        <td><%out.println((tour.isHot() ? "+" : "-")); %></td>
    </tr>

    <%
        if (client.isRegular()) {
    %>
    <tr>
        <td><fmt:message key="label_discount" bundle="${bundle}"/></td>
        <td><% out.println(TravelAgency.countDiscountPrice(tour.getPrice())); %></td>
    </tr>
    <%
        }
    %>

    <tr>
        <td><fmt:message key="label_specification" bundle="${bundle}"/></td>
        <td><%out.println(tour.getInfo()); %></td>
    </tr>

</table>

</body>
</html>
