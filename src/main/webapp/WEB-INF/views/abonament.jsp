<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Library abonament</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css"/>
    <script>
        function inactive() {
            var el = document.getElementById("statusId").value;
            var sD = document.getElementById("startDate");
            var eD = document.getElementById("endDate");
            console.log(el);
            if (el == "None") {
                sD.disabled = true;
                eD.disabled = true;
                sD.value = '';
                eD.value = '';
            } else {
                sD.disabled = false;
                eD.disabled = false;
            }
        }

    </script>
</head>
<body>
<form:form action="/updateLibraryAbonament" modelAttribute="libraryAbonament" method="post" id="updateLibraryAbonament">
    <br>
    <form:hidden path="id" value="${libraryAbonament.id}"/>
    <h1> ${student.firstName} ${student.lastName} <br> student abonament</h1>
    <br><br>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Status:</label>
        </div>
        <div class="col-2">
            <form:select path="status" id="statusId" class="form-control" onchange="inactive(this)">
                <form:option value="None"/>
                <form:option value="Active"/>
                <form:option value="Suspended"/>
            </form:select>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">StartDate:</label>
        </div>
        <div class="col-2">
<%--            <input type="date" class="form-control" name="start_date" id="startDate" value="${libraryAbonament.startDate}">--%>
            <form:input path="startDate" type="date" class="form-control" id="startDate"/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">EndDate:</label>
        </div>
        <div class="col-2">
            <form:input path="endDate" type="date" class="form-control" id="endDate"/>
        </div>
    </div>
    <div class="form-row">
        <div class="col-1"></div>
        <div class="col-2">
            <button type="submit" class="btn btn-secondary btm-sm">Save</button>

            <button type="button" onclick="window.close()" class="btn btn-secondary btm-sm">Cancel</button>
        </div>
    </div>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
    <script>
        $(document).ready(function () {
            $('#updateLibraryAbonament').ajaxForm(function () {
                // alert("Thank you for your comment!");
                window.opener.location.reload();
                window.self.close();
            });
        });
    </script>
</form:form>
</body>
</html>
