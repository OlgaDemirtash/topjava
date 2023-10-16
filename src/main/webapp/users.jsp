<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
Current user: ${user}
<form action="users">
    <p><select size="3" name="user">
        <option disabled>Select user</option>
        <option value="1" ${user==1 ? " selected": ""}>1</option>
        <option value="2" ${user==2 ? " selected": ""}>2</option>
    </select></p>
    <p><input type="submit" value="Отправить"></p>
</form>
</body>
</html>