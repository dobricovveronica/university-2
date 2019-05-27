<%@ page import="java.util.Base64" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title>Student Management</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css"/>
    <script>
        var checkList;

        function checkAll(source) {
            var checkboxes = document.querySelectorAll('input[type="checkbox"]');
            for (var i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i] != source)
                    checkboxes[i].checked = source.checked;
            }
        }

        function activateInput(actionName) {
            var el1 = document.getElementById("search");
            var el2 = document.getElementById("reset");
            var el3 = document.getElementById("delete");
            el1.disabled = true;
            el2.disabled = true;
            el3.disabled = true;
            if (actionName == "search") {
                el1.disabled = false;
            }
            if (actionName == "reset") {
                el2.disabled = false;
            }
            if (actionName == "delete") {
                el3.disabled = false;
            }
        }

        function confSubmit(actionName) {
            if (confirm("Are you want to delete student(s)?")) {
                activateInput(actionName);
            }
        }

        function secondSubmit(i) {
            var filterForm = document.getElementById("searchForm");
            filterForm.action = '/';
            filterForm.method = 'post';
            document.getElementById("page").setAttribute('value', i);

            filterForm.submit();
            console.log(i, "succes submit");
        }


    </script>
</head>

<body>
<h1>List Students</h1>

<form:form modelAttribute="studentFilter" id="searchForm">
    <input type="hidden" id="page" name="page">
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label"> Name:</label>
        </div>

        <div class="col-2">
            <form:input path="studentName" placeholder="Partial name" class="form-control"></form:input>
        </div>
        <div class="col-1">
            <label class="col-3 col-form-label">Date:</label>
        </div>
        <div class="col-2">
            <form:input type="date" path="startDate" placeholder="Start Date" class="form-control"></form:input>
        </div>
        <div class="col-2">
            <form:input type="date" path="endDate" placeholder="End Date" class="form-control"></form:input>
        </div>
    </div>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Address:</label>
        </div>
        <div class="col-2">
            <form:input path="studentAddress" placeholder="Partial address" class="form-control"></form:input>
        </div>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Discipline:</label>
        </div>
        <div class="col-2">
            <form:select path="disciplineId" class="form-control">
                <option selected value="">
                    Select discipline
                </option>
                <c:forEach items="${disciplines}" var="discipline">
                    <option value="${discipline.id}"
                            <c:if test="${studentFilter.disciplineId == discipline.id}">selected</c:if>>${discipline.title}</option>
                </c:forEach>
            </form:select>
        </div>
        <div class="col-2">
            <form:input path="disciplineTitle" placeholder="Discipline name" class="form-control"></form:input>
        </div>
    </div>
    <br>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Group:</label>
        </div>
        <div class="col-2">
            <form:select path="groupId" class="form-control">
                <option selected value="">
                    Select group
                </option>
                <c:forEach items="${groups}" var="group">
                    <option value="${group.id}"
                            <c:if test="${studentFilter.groupId == group.id}">selected</c:if>>${group.name}</option>
                </c:forEach>
            </form:select>
        </div>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Average:</label>
        </div>
        <div class="col-2">
            <form:input path="average" placeholder="Student average" class="form-control"></form:input>
        </div>
    </div>
    <br>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Gender:</label>
        </div>
        <div class="col-2">
            <div class="form-check form-check-inline">
                <form:radiobutton class="form-check-input col-4" path="gender" value="M"/>Male<br>
                <form:radiobutton class="form-check-input col-5" path="gender" value="F"/>Female<br>
                <form:radiobutton class="form-check-input col-3" path="gender" value="T"/>All<br>
            </div>
        </div>
        <div class="col-3"></div>
        <div class="col-1">
            <form:button type="submit" class="btn btn-secondary btm-sm">Search</form:button>
        </div>
        <div class="col">

            <button class="btn btn-secondary btm-sm" value="/RESET">
                <a target="_parent" style="color: white; text-decoration: none" href="/RESET">
                    Reset
                </a>
            </button>

        </div>
    </div>

</form:form>
<br>
<form:form method="get" action="/DELETE">

    <table id="ViewStudents" class="table table-hover">
        <thead>
        <tr>
            <td><input type="checkbox" onclick="checkAll(this)"
                       onchange="document.getElementById('deleteButton').disabled = false;"></td>
            <th scope="col">Photo</th>
            <th scope="col">Name</th>
            <th scope="col">Birth Day</th>
            <th scope="col">Gender</th>
            <th scope="col">Address</th>
            <th scope="col">Phone</th>
            <th scope="col">Email</th>
            <th scope="col">Library Abonament</th>
            <th scope="col">Group</th>
            <th scope="col">Disciplines</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${students}" var="student">

            <tr>
                <td>
                    <input type="checkbox" name="check" id="myCheck" value="${student.id}"
                           onchange="document.getElementById('deleteButton').disabled = false;">
                </td>
                <td><img src="data:image/jpeg;base64,${student.getImageBase64(student)}" width="50" height="50"
                         alt="image">
                <td>${student.firstName} ${student.lastName}</td>
                <td>${student.dateOfBirth}</td>
                <td>${student.gender}</td>
                <td>${student.address.country}, ${student.address.city}, <br>${student.address.address}</td>
                <td>
                    <c:forEach items="${student.convertListToSet(student.phones)}" var="phone">
                        ${phone.phoneType.name}: ${phone.value}<br>
                    </c:forEach>
                </td>
                <td>
                        ${student.mail}

                </td>
                <td>
                    <a href="#"
                       onclick="window.open('LIBRARY_ABONAMENT?id='+ ${student.id},'MyWindow', 'width=1200, height=500');
                               return false;">
                        Status: ${student.libraryAbonament.status}
                    </a><br>
                    <c:if test="${student.libraryAbonament.status.equals('Active')}">
                        From: ${student.libraryAbonament.startDate}<br>
                        To: ${student.libraryAbonament.endDate}
                    </c:if>
                </td>
                <td>${student.group.name}</td>
                <td>
                    <c:forEach items="${student.averages}" var="average">
                        ${average.discipline.title}: ${average.value}
                        <br>
                    </c:forEach>
                    <c:if test="${student.averages.size() != 0}">
                        <b>Total Average:</b> ${student.studentAverage()}
                    </c:if>
                </td>
                <td>
                    <button type="submit" class="btn btn-success"
                            onclick="window.open('EDIT?id='+ ${student.id},'MyWindow' ,'width=1200, height=700'); return false;">
                        Edit
                    </button>
                    <button type="submit" class="btn btn-primary"
                            onclick="window.open('MARK?id='+ ${student.id},'MyWindow' ,'width=1200, height=700'); return false;">
                        Add Mark
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-row">

        <div class="col-2">
            <button type="submit" class="btn btn-secondary btm-sm"
                    onclick="window.open('EDIT?id=0','MyWindow' ,'width=1200, height=700'); return false;">
                Add new
            </button>
            <button class="btn btn-secondary btm-sm" type="submit" name="delete" id="deleteButton"
                    onclick="confSubmit(name)"
                    value="'DELETE?check='+ ${check}"
                    disabled>Delete
            </button>
        </div>
        <div class="col-9"></div>
        <div class="col-1">
            <button class="btn btn-secondary btm-sm" value="pdf"><a target="_blank"
                                                                    style="color: white; text-decoration: none"
                                                                    href="/pdf">Download </a></button>
        </div>
    </div>
</form:form>


<%--    <div class="col-4"></div>--%>
<div class="d-flex justify-content-center">
    <c:forEach begin="1" end="${maxPages}" var="i">
        <button
                <c:if test="${page == i}">class="btn btn-primary"</c:if> class="btn btn-secondary btm-sm"
                id="pageNumber" onclick="secondSubmit(${i})">
                ${i}
        </button>
    </c:forEach>
</div>


</body>
</html>