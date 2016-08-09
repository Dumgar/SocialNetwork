<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h1 align="center">Login Page</h1>
<form name="login"
      action="login"
      method="post">
    <ul>
        <li><label>Email</label>
            <input type="email" name="email" placeholder="yourname@email.com" required></li>
        <li><label>Password</label>
            <input type="password" name="password" placeholder="password" required>
            <input type="submit" name="login" value="Login"></li>
    </ul>
</form>
<form name="registration"
      action="login"
      method="post">
<li><label>Don't have an account?</label>
    <a href="registration.jsp">Register Now!</a></li>
</form>
</body>
</html>