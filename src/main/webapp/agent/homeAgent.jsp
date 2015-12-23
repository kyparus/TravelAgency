<%@ page import="kyparus.user.Agent" %>
<%@ page import="kyparus.Tour.Tour" %>
<%@ page import="kyparus.Tour.ShoppingTour" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="kyparus.Tour.ExcursionTour" %>
<%@ page import="kyparus.Tour.RestTour" %>
<%@ page import="kyparus.TravelAgency" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="word" var="bundle"/>
<fmt:requestEncoding value="UTF-8"/>
<%--
  Created by IntelliJ IDEA.
  User: yurii
  Date: 04.12.15
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Agent agent = (Agent) session.getAttribute("agent");
    LinkedList<Tour> tours =(LinkedList<Tour>) session.getAttribute("tours");

%>
<html>
<head>
    <title><fmt:message key="login_label_home" bundle="${bundle}"/></title>
</head>
<body>
<form>
    <a href="../index.jsp" style="padding-right: 55px"><fmt:message key="label_exit" bundle="${bundle}"/></a>
</form>

<form>
    <p style="padding-left: 75%">

        <fmt:message key="label_agent" bundle="${bundle}"/> <br>
        <fmt:message key="label_first_name" bundle="${bundle}"/>: <%= agent.getFirstName() %> <br>
        <fmt:message key="label_last_name" bundle="${bundle}"/>: <%= agent.getLastName() %> <br>
        <fmt:message key="label_nickname" bundle="${bundle}"/>: <%= agent.getNickName() %> <br>
        <fmt:message key="label_birth" bundle="${bundle}"/>: <%= agent.getBirth() %> <br>
        <fmt:message key="label_salary" bundle="${bundle}"/>: <%= agent.getSalary() %> <br>
        <fmt:message key="label_position" bundle="${bundle}"/>: <%= agent.getPosition() %> <br>
    </p>
    <br>

    <p style="padding-left: 75%">
        <fmt:message key="label_discount_for" bundle="${bundle}"/> :
        <%= TravelAgency.getDiscountForRegClients()%>
    </p>
</form>

<form name="inputForm" action="handle" style="padding-left: 20%" method="post">
    <p>
        <fmt:message key="label_tourID" bundle="${bundle}"/>
        <label>
            <input type="text" name="tourID" value=""/>
        </label>
    </p>

    <p>
        <input type="submit" name="submitID" value="<fmt:message key="button_mark_hot" bundle="${bundle}"/>"/>
    </p>

</form>

<form name="discountForm" action="handle" style="padding-right: 10%" method="post">
    <p style="padding-left: 85%">
        <label>
            <input type="text" name="disVal" value=""/>
        </label>
        <fmt:message key="label_percent" bundle="${bundle}"/>
    </p>

    <p style="padding-left: 85%">
        <input type="submit" name="submitDiscount"
               value="<fmt:message key="button_update_discount" bundle="${bundle}"/>"/>
    </p>

</form>


<form name="toursType" action="handle" method="post">
    <p align="center">
        <input type="submit" name="openRestTours" value="<fmt:message key="button_rest" bundle="${bundle}"/>"/>
        <input type="submit" name="openExTours" value="<fmt:message key="button_ex" bundle="${bundle}"/>"/>
        <input type="submit" name="openShopTours" value="<fmt:message key="button_shop" bundle="${bundle}"/>"/>
    </p>
</form>

<table border="2" align="center" style="background: chocolate">
    <tr>
        <td><fmt:message key="label_departure" bundle="${bundle}"/></td>
        <td><fmt:message key="label_arrival" bundle="${bundle}"/></td>
        <td><fmt:message key="label_to" bundle="${bundle}"/></td>
        <td><fmt:message key="label_from" bundle="${bundle}"/></td>
        <td><fmt:message key="label_price" bundle="${bundle}"/></td>
        <td><fmt:message key="label_hot" bundle="${bundle}"/></td>
    </tr>
    <%
        for (Tour tour : tours) {
    %>
    <tr>
        <td><% out.println(tour.getDeparture()); %></td>
        <td><% out.println(tour.getArrival()); %></td>
        <td><% out.println(tour.getTransToLocation()); %></td>
        <td><% out.println(tour.getTransFromLocation()); %></td>
        <td><% out.println(tour.getPrice());%></td>
        <td><% out.println((tour.isHot() ? "+" : "-")); %></td>
        <td><% out.println(tour.getID()); %></td>
    </tr>
    <%
        }
    %>

</table>

</body>
</html>
