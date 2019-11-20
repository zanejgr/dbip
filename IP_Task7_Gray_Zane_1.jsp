<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>Choose customer categories</title>
</head>

<body>
  <h2>Choose customer categories</h2>
  <!--
          Form for collecting user input for the new movie_night record.
          Upon form submission, add_movie.jsp file will be invoked.
          -->
  <form action="select_customer.jsp">
    <!-- The form organized in an HTML table for better clarity. -->
    <table border=1>
      <tr>
        <th colspan="2">Enter the customer category range (inclusive):</th>
      </tr>
      <tr>
        <td>Lower bound:</td>
        <td>
          <div style="text-align: center;">
            <input type=text name=lower>
          </div>
        </td>
      </tr>
      <tr>
        <td>Upper bound:</td>
        <td>
          <div style="text-align: center;">
            <input type=text name=upper>
          </div>
        </td>
      </tr>
      <tr>
        <td>
          <div style="text-align: center;">
            <input type=reset value=Clear>
          </div>
        </td>
        <td>
          <div style="text-align: center;">
            <input type=submit value=Select>
          </div>
        </td>
      </tr>
    </table>
  </form>
</body>

</html>