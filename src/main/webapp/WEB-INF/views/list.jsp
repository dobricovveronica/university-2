<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Student Management</title>
</head>

<body>
List Students
<br>
<form>
    <label>Name:</label>
    <input placeholder="Partial name">
    <label>Date of birth:</label>
    <input placeholder="Start date">
    <input placeholder="End date">
    <br><br>
    <label>Address:</label>
    <input placeholder="Partial address">
    <label>Discipline:</label>

<%--    <select placeholder="Discipline title">--%>
<%--        <c:forEach items="${disciplines}" var="discipline">--%>
<%--            <option>${discipline.title}</option>--%>
<%--        </c:forEach>--%>
<%--    </select>--%>
    <input placeholder="Discipline title">
    <br><br>
    <label>Group:</label>
    <input placeholder="Group number">
    <label>Total average:</label>
    <input placeholder="Student average">
    <br>
</form>
<br>
<form method="post">
    <table border="1">
        <tr>
            <td>ID</td>
            <td>
                <input type="checkbox">
            </td>
            <td>Name</td>
            <td>Date of Birth</td>
            <td>Gender</td>
            <td>Address</td>
            <td>Phone</td>
            <td>Library Abonament</td>
            <td>Group</td>
            <td>Discipline</td>
            <td>Action</td>
        </tr>
        <c:forEach items="${students}" var="student">
            <tr>
                <td>${student.id}</td>
                <td><input type="checkbox"></td>
                <td>${student.firstName} ${student.lastName}</td>
                <td>${student.dateOfBirth}</td>
                <td>${student.gender}</td>
                <td>${student.address.country}, ${student.address.city}, ${student.address.address}</td>
                <td>
                    <c:forEach items="${student.phones}" var="phone">
                        ${phone.phoneType.name}: ${phone.value}<br>
                    </c:forEach>
                </td>
                <td>
                    Status: ${student.libraryAbonament.status}<br>
                    <c:if test="${student.libraryAbonament.status.equals('Active')}">
                        From: ${student.libraryAbonament.startDate}<br>
                        To: ${student.libraryAbonament.endDate}
                    </c:if>
                </td>
                <td>${student.group.name}</td>
                <td>
                    <c:forEach items="${student.disciplines}" var="discipline">
                        ${discipline.title}<br>
                    </c:forEach>
                </td>
                <td>
                    <button type="submit" class="btn btn-success">Edit</button>
                    <button>Add Mark</button>
                </td>

            </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>