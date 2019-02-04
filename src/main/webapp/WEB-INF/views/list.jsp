<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Person Management</title>
</head>

<body>

<table border="1">
  <tr>
    <td>ID</td>
    <td>First Name</td>
    <td>Last Name</td>
  </tr>
  <c:forEach items="${persons}" var="person">
    <tr>
      <td>${person.id}</td>
      <td>${person.firstName}</td>
      <td>${person.lastName}</td>
    </tr>
  </c:forEach>
</table>
</body>
</html>