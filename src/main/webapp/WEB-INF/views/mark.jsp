<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Mark</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css"/>

    <script>
        window.onload = function (ev) {
            var today = new Date();
            var oldday = new Date();
            var dd = today.getDate();
            var mm = today.getMonth()+1; //January is 0!
            var yyyy = today.getFullYear();
            if(dd<10){
                dd='0'+dd
            }
            if(mm<10){
                mm='0'+mm
            }

            today = yyyy +'-'+ mm +'-'+ dd;
            oldday = yyyy - 1+'-'+ mm +'-'+ dd;
            document.getElementById("dateOfMark").setAttribute("max", today);
            document.getElementById("dateOfMark").setAttribute("value", today);
            document.getElementById("dateOfMark").setAttribute("min", oldday);
        }
    </script>
</head>
<body>
<h2>Add Mark to student <br> ${student.firstName} ${student.lastName}
    (Group: ${student.group.name})</h2>
<form:form modelAttribute="mark" action="/ADD_MARK" method="post" id="addMark">
    <div class="form-group row">
        <form:hidden path="student.id" value="${student.id}"/>
        <div class="col-2">
            <label class="col-sm-2 col-form-label">Discipline:</label>
        </div>
        <div class="col-3">
            <form:select path="discipline.id" id="discipline" class="form-control" onclick="teacherSelect(this)" >
                <form:option value="-1">Select discipline</form:option>
                <form:options items="${student.disciplines}" itemValue="id" itemLabel="title" ></form:options>
            </form:select>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-2">
            <label class="col-sm-2 col-form-label">Profesor:</label>
        </div>
        <div class="col-3">
            <form:select path="teacher.id" id="teacher" class="form-control" itemValue="Teacher">
                <script>
                    function teacherSelect(discipline) {
                        var dis = String(discipline.value);
                        var teach = document.getElementById("teacher");
                        <c:forEach items="${student.disciplines}" var="discipline">
                            if (dis == ${discipline.id})
                            teach.options[0] = new Option("${discipline.teacher.firstName} "+" ${discipline.teacher.lastName}", ${discipline.teacher.id});
                        </c:forEach>
                    }
                </script>
            </form:select>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-2">
            <label class="col-sm-2 col-form-label">Mark:</label>
        </div>
        <div class="col-3">
            <form:input type="text" class="form-control"  path="value" placeholder="Mark value"/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-2">
            <label class="col-sm-2 col-form-label">Mark:</label>
        </div>
        <div class="col-3">
            <form:input type="date" class="form-control" id="dateOfMark" path="createData"/>
        </div>
    </div>
    <div class="form-row">
        <div class="col-2"></div>
        <div class="col-2">
            <button type="submit" class="btn btn-secondary btm-sm">Save</button>
            <button type="button" onclick="window.close()" class="btn btn-secondary btm-sm">Cancel</button>
        </div>
    </div>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
    <script>
        $(document).ready(function () {
            $('#addMark').ajaxForm(function () {
                window.opener.location.reload();
                window.self.close();
            });
        });
    </script>
</form:form>
</body>
</html>
