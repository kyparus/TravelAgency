<%--
  Created by IntelliJ IDEA.
  User: yurii
  Date: 30.11.15
  Time: 17:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="kyparus.user.Client" %>
<%@ page import="kyparus.Tour.RestTour" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="kyparus.Tour.Tour" %>
<%@ page import="kyparus.Tour.ExcursionTour" %>
<%@ page import="kyparus.Tour.ShoppingTour" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mytag" uri="http://mycompany.com" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="word" var="bundle"/>
<fmt:requestEncoding value="UTF-8"/>
<%
    Client client = (Client) session.getAttribute("client");
    if(client == null) client = new Client();
    LinkedList<Tour> tours = (LinkedList<Tour>) session.getAttribute("tours");
    //to go back from language filter
    session.setAttribute("page","/client/homeClient.jsp");

%>

<html>
<head>
    <title><fmt:message key="login_label_home" bundle="${bundle}"/></title>
    <br>
    <div>
        <a href="/enen"><mytag:lang>en</mytag:lang></a>
        <a href="/uaua"><mytag:lang>ua</mytag:lang></a>
    </div>
    <form>
        <a href="../index.jsp" style="padding-right: 55px"><fmt:message key="label_exit" bundle="${bundle}"/></a>
    </form>

    <form>
        <p style="padding-left: 75%">

            <fmt:message key="label_client" bundle="${bundle}"/> <br>
            <fmt:message key="label_first_name" bundle="${bundle}"/>: <%= client.getFirstName() %> <br>
            <fmt:message key="label_last_name" bundle="${bundle}"/>: <%= client.getLastName() %> <br>
            <fmt:message key="label_nickname" bundle="${bundle}"/>: <%= client.getNickName() %> <br>
            <fmt:message key="label_birth" bundle="${bundle}"/>: <%= client.getBirth() %> <br>
            <fmt:message key="label_usage" bundle="${bundle}"/>: <%= client.getUsageTimes() %> <br>
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
            <input type="submit" name="openTour" value="<fmt:message key="label_open_tour" bundle="${bundle}"/>"/>
        </p>

    </form>

    <form name="toursType" action="handle" method="post">
        <p align="center">
            <input type="submit" name="openRestTours" value="<fmt:message key="button_rest" bundle="${bundle}"/>"/>
            <input type="submit" name="openExTours" value="<fmt:message key="button_ex" bundle="${bundle}"/>"/>
            <input type="submit" name="openShopTours" value="<fmt:message key="button_shop" bundle="${bundle}"/>"/>
            <input type="submit" name="openClientTours" value="<fmt:message key="button_my_tours" bundle="${bundle}"/>"/>
        </p>
    </form>

    <table border="1" align="center">
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


</head>
<body>

</body>
</html>
