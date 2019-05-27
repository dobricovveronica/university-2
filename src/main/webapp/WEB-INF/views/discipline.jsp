<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Discipline</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css"/>
    <script>
        function duplicate(oneDiscipline) {
            var c = $(oneDiscipline).parent(this).parent(this).clone().appendTo(".alldisciplines");
            c.find($('div[name="discipline"]')).find('select').val('');
            c.find($('div[name="teacher"]')).find('select').val('');
            // c.find('input[type="hidden"]').val('');
            // reIndex();
        }

        function deleteBlock(oneDiscipline) {
            $(oneDiscipline).parent(this).parent(this).remove();
            reIndex();
        }

        function reIndex() {
            $('div[name="disciplineRow"]').each(function (index) {
                $(this).find($('div[name="disciplineDiv"]')).find("select").attr('name', 'phones[' + index + '].phoneType.id');
                $(this).find($('div[name="discipline"]')).find('input[type="hidden"]').attr("name", 'phones[' + index + '].id');
                $(this).find($('div[name="discipline"]')).find('input[type="text"]').attr("name", 'phones[' + index + '].value');

            })
        }


        <%--function teacherSelect(discipline) {--%>
        <%--    var dis = String(discipline.value);--%>
        <%--    var teach = document.getElementsByName("teacher");--%>
        <%--    <c:forEach items="${student.disciplines}" var="discipline">--%>
        <%--    if (dis == ${discipline.id})--%>
        <%--        teach.options[0] = new Option("${discipline.teacher.firstName} "+" ${discipline.teacher.lastName}", ${discipline.teacher.id});--%>
        <%--    </c:forEach>--%>
        <%--}--%>

    </script>

</head>
<body>
<h2>Add Disciplines to student <br> ${student.firstName} ${student.lastName}
    (Group: ${student.group.name})</h2>
<form:form modelAttribute="disciplines" action="/ADD_DISCIPLINE" method="post" id="addDiscipline">

<div class="form-group row">
        <%--        <form:hidden path="student.id" value="${student.id}"/>--%>
    <div class="col-2">

        <label class="col-sm-2 col-form-label">Discipline:</label>
    </div>
</div>
    <div class="alldisciplines">
        <c:if test="${student.disciplines.size() > 0}">
            <c:forEach items="${student.disciplines}" var="discipline">
                <div class="form-row" name="disciplineRow">
                    <div class="col-1"></div>
                    <div class="col-2" name="discipline">
                        <select class="form-control" >
                            <option value="">Select discipline</option>
                            <c:forEach items="${disciplines}" var="disciplineAll">
                                <option value="${disciplineAll.id}"
                                        <c:if test="${discipline.id == disciplineAll.id}">selected</c:if>
                                > ${disciplineAll.title}</option>
                            </c:forEach>

                        </select>
                    </div>
                    <div class="col-3" name="teacher">
                        <select  name="teacherId" class="form-control">
                            <option value="${discipline.teacher.id}" selected>${discipline.teacher.firstName} ${discipline.teacher.lastName}</option>
                        </select>
                    </div>
                    <div class="col-1.5" name="buttons">
                        <button type="button" class="btn btn-success" id="add" value="add" onclick="duplicate(this)">Add
                        </button>
                        <button type="button" class="btn btn-danger" onclick="deleteBlock(this)">Delete</button>
                    </div>
                </div>

            </c:forEach>
        </c:if>
        <c:if test="${student.disciplines.size() == 0}">
            <div class="form-row" name="disciplineRow">
                <div class="col-1"></div>
                <div class="col-2" name="discipline">
                    <select class="form-control" onclick="teacherSelect(this)">
                        <option value="">Select discipline</option>
                        <c:forEach items="${disciplines}" var="disciplineAll">
                            <option value="${disciplineAll.id}"> ${disciplineAll.title}</option>
                        </c:forEach>

                    </select>
                </div>
                <div class="col-3" name="teacher">
                    <select  id="teacherId" class="form-control" itemValue="Teacher" disabled>
                        <script>
                            function teacherSelect(discipline) {
                                var dis = String(discipline.value);
                                var teach = document.getElementById("teacherId");
                                <c:forEach items="${discipline}" var="discipline">
                                if (dis == ${discipline.id})
                                    teach.options[0] = new Option("${discipline.teacher.firstName} "+" ${discipline.teacher.lastName}", ${discipline.teacher.id});
                                </c:forEach>
                            }
                        </script>
                    </select>
                </div>
                <div class="col-1.5" name="buttons">
                    <button type="button" class="btn btn-success" id="add" value="add" onclick="duplicate(this)">Add
                    </button>
                    <button type="button" class="btn btn-danger" onclick="deleteBlock(this)">Delete</button>
                </div>
            </div>


        </c:if>
    </div>
    <br>

    <div class="form-row">
        <div class="col-2"></div>
        <div class="col-2">
            <button class="btn btn-secondary btm-sm" type="submit" name="save" id="" onclick=""
                    value="'ADD_DISCIPLINE?discipline='+ ${disciplines}"
            >Save
            </button>
                <%--            <button type="submit" class="btn btn-secondary btm-sm">Save</button>--%>
            <button type="button" onclick="window.close()" class="btn btn-secondary btm-sm">Cancel</button>
        </div>
    </div>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
    <script>
        $(document).ready(function () {
            $('#addDiscipline').ajaxForm(function () {
                window.opener.location.reload();
                window.self.close();
            });
        });
    </script>
</form:form>
</body>
</html>
