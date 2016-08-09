<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h1 align="center">Registration Page</h1>
<form name="registration"
      action="registration"
      method="post">
    <input type="text" name="firstName" placeholder="First Name" required><br>
    <input type="text" name="lastName" placeholder="Last Name" required><br>
    <input type="email" name="email" placeholder="yourname@email.com" required><br>
    <input type="password" name="password" placeholder="Password" required><br>
    <input type="date" name="birthDate" max="2000-01-01" min="1900-01-01" placeholder="Date of Birth" required><br>
    <input type="submit" value="Register Me!">
<br><br>
   <strong> <a href="login.jsp">Login!</a></li> </strong>
</form>
</body>
</html>
