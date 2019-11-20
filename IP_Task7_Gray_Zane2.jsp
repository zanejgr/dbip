<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>Movie Nights</title>
</head>

<body>
  <%@page import="jsp_azure_test.DataHandler"%>
  <%@page import="java.sql.ResultSet"%>
  <%
    // We instantiate the data handler here, and get all the movies from the database
    final DataHandler handler = new DataHandler();
    String stLower = request.getParameter("lower");
    String stUpper = request.getParameter("upper");
    if(stLower==null||stUpper==null||stLower==""||stUpper==""){
      response.sendRedirect("select_customer.jsp");
    }else{
      int lower = Integer.parseInt(stLower);
      int upper = Integer.parseInt(stUpper);
      final ResultSet movies = handler.getAllMovies(lower, upper);
    %>
  <!-- The table for displaying all the movie records -->
  <table cellspacing="2" cellpadding="2" border="1">
    <tr>
      <!-- The table headers row -->
      <td align="center">
        <h4>Name</h4>
      </td>
      <td align="center">
        <h4>Address</h4>
      </td>
      <td align="center">
        <h4>Category</h4>
      </td>
    </tr>
    <%
      while(movies.next()) { // For each movie_night record returned...
      // Extract the attribute values for every row returned
      final String name = movies.getString("name");
      final String address = movies.getString("address");
      final String category = movies.getString("category");
       out.println("<tr>"); // Start printing out the new table row
        out.println( // Print each attribute value
        "<td align=\"center\">" + name +
          "</td><td align=\"center\"> " + address +
          "</td><td align=\"center\"> " + category +
          "</td>");
        out.println("</tr>");
      }
    }
  %>
  </table>
</body>

</html>