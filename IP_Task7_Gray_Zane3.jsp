<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Query Result</title>
</head>

<body>
  <%@page import="jsp_azure_test.DataHandler"%>
  <%@page import="java.sql.ResultSet"%>
  <%@page import="java.sql.Array"%>
  <%
                           // The handler is the one in charge of establishing the connection.
                           DataHandler handler = new DataHandler();
                           // Get the attribute values passed from the input form.
                           String name = request.getParameter("name");
                           String address = request.getParameter("address");
                           String durationString = request.getParameter("category");
                           /*
                           * If the user hasn't filled out all the time, movie name and duration. This is very simple checking.
                           */
                           if (name==null||address==null||durationString==null||name.equals("") || address.equals("") || durationString.equals("")) {
                           response.sendRedirect("add_movie_form.jsp");
                           } else {
                           int category = Integer.parseInt(durationString);
                           // Now perform the query with the data from the form.
                           boolean success = handler.addMovie(name, address, category);
                           try{if (!success) { // Something went wrong
                           %>
  <h2>There was a problem inserting the customer</h2>
  <%
                           } else { // Confirm success to the user
                           %>
  <h2>The customer:</h2>
  <ul>
    <li>Name: <%=name%></li>
    <li>Address: <%=address%></li>
    <li>category: <%=durationString%></li>

  </ul>
  <h2>Was successfully inserted.</h2>
  <a href="get_all_movies.jsp">See all customers.</a>
  <%
                           }
                           }catch(Exception e){// Something went wrong
                           %>
  <h2>There was a problem inserting the customer</h2>
  <% } } %>
</body>

</html>