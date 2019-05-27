<%@ page import="java.sql.Date" %><%--<%@ page import="java.util.Base64" %>--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add / Edit Student</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="//code.jquery.com/jquery-1.9.1.js"></script>
    <%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>--%>
    <%--    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>--%>

    <style>
        .error {
            color: #ff0000;
            font-style: italic;
            font-weight: bold;
        }
    </style>
    <script>
        function duplicate(onePhone) {
            var c = $(onePhone).parent(this).parent(this).clone().appendTo(".allphones");
            c.find('input[type="text"]').val('');
            c.find('input[type="hidden"]').val('');
            reIndex();
        }

        function deleteBlock(onePhone) {
            $(onePhone).parent(this).parent(this).remove();
            reIndex();
        }

        function reIndex() {
            $('div[name="phoneRow"]').each(function (index) {
                $(this).find($('div[name="typeDiv"]')).find("select").attr('name', 'phones[' + index + '].phoneType.id');
                $(this).find($('div[name="phone"]')).find('input[type="hidden"]').attr("name", 'phones[' + index + '].id');
                $(this).find($('div[name="phone"]')).find('input[type="text"]').attr("name", 'phones[' + index + '].value');

            })
        }

        function previewFile() {
            var preview = document.querySelector('img');
            var el = document.querySelector('input[type=file]');
            var file;
            var reader = new FileReader();
            if (el.value != '') {
                file = el.files[0];
            }
            reader.addEventListener("load", function () {
                preview.src = reader.result;
            }, false);

            if (file) {
                reader.readAsDataURL(file);
            }
        }

        window.onload = function (ev) {
            var today = new Date();
            var oldday = new Date();
            var dd = today.getDate();
            var mm = today.getMonth() + 1; //January is 0!
            var yyyy = today.getFullYear();
            if (dd < 10) {
                dd = '0' + dd
            }
            if (mm < 10) {
                mm = '0' + mm
            }

            today = yyyy + '-' + mm + '-' + dd;
            oldday = yyyy - 100 + '-' + mm + '-' + dd;
            document.getElementById("dateOfBirth").setAttribute("max", today);
            document.getElementById("dateOfBirth").setAttribute("min", oldday);
        }

    </script>
</head>
<body>
<h1>Add / Edit Student</h1>
<form:form modelAttribute="student" action="/ADDEDIT" method="post" enctype="multipart/form-data" id="addEdit"
           name="addEditForm">
    <c:if test="${student.id != 0}">
        <form:hidden path="id"></form:hidden>
    </c:if>
    <div class="form-group row">
        <div class="col-1">
            <form:label class="col-sm-2 col-form-label" path="firstName">FirstName:</form:label>
        </div>
        <div class="col-3">
            <form:input type="text" class="form-control" placeholder="First name" path="firstName"
                        pattern="[A-Za-z]{3,}" title="Your name should be between 3 - 20 characters."/>
            <form:errors path="firstName" cssClass="error"/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">LastName:</label>
        </div>
        <div class="col-3">
            <form:input type="text" class="form-control" placeholder="Last name" path="lastName" pattern="[A-Za-z]{3,}"
                        title="Your last name should be between 3 - 20 characters."/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-3 col-form-label">Date:</label>
        </div>
        <div class="col-3">
            <form:input type="date" class="form-control" path="dateOfBirth" placeholder="Date of birth"
                        id="dateOfBirth"/>
        </div>
    </div>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Gender:</label>
        </div>
        <div class="col-2">
            <div class="form-check form-check-inline">
                <form:radiobutton class="form-check-input col-4" path="gender" value="M"/>Male<br>
                <form:radiobutton class="form-check-input col-4" path="gender" value="F"/>Female<br>
            </div>
        </div>
    </div>
    <div class="form-row">
        <form:hidden path="address.id" value="${student.address.id}"/>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Country:</label>
        </div>
        <div class="col-3">
            <form:input type="text" class="form-control" placeholder="Country" path="address.country"
                        pattern="[A-Za-z]{3,}" title="Please enter your country."/>
        </div>
        <div class="col-1"></div>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Picture:</label>
        </div>
        <div class="col-3">
            <c:set var="imageName" value="data:image/jpeg;base64,${student.getImageBase64(student)}"></c:set>
            <img src="${imageName}" class="rounded float-left"
                 style="position:absolute; bottom: 50px" height="200" width="178">
            <form:input path="picture" type="file" accept="image/*" onchange="previewFile()"/>
        </div>
    </div>
    <br>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">City:</label>
        </div>
        <div class="col-3">
            <form:input type="text" class="form-control" placeholder="City" path="address.city" pattern="[A-Za-z]{3,}"
                        title="Please enter your city."/>
        </div>
        <div class="col-1"></div>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Mail:</label>
        </div>
        <div class="col-3">
            <form:input type="email" class="form-control" placeholder="Mail" path="mail"
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"/>
        </div>
    </div>
    <br>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Address:</label>
        </div>
        <div class="col-3">
            <form:input type="text" class="form-control" placeholder="Address" path="address.address"
                        pattern="[A-Za-z0-9'\.\-\s\,]{3,}" title="Please enter your valid address."/>
        </div>
        <div class="col-1"></div>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Group:</label>
        </div>
        <div class="col-3">
            <form:select name="group" class="form-control" path="group.id">
                <option <c:if test="${groups == null}">selected</c:if>>Select group</option>
                <form:options items="${groups}" itemValue="id" itemLabel="name"></form:options>
            </form:select>
        </div>
    </div>
    <br>
    <div class="allphones" id="class-1">
        <c:if test="${student.phones.size() > 0}">
            <c:forEach begin="0" end="${student.phones.size()-1}" var="i">
                <div class="form-row" name="phoneRow">
                    <div class="col-1">
                        <label class="col-sm-2 col-form-label">Phone(s):</label>
                    </div>

                    <div class="col-1" name="typeDiv">
                        <form:select class="form-control" path="phones[${i}].phoneType.id">
                            <c:forEach items="${phoneTypes}" var="phoneType">
                                <option value="${phoneType.id}"
                                        <c:if test="${student.phones[i].phoneType.id == phoneType.id}">selected</c:if>>${phoneType.name}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="col-1.4" name="phone">
                        <form:input type="hidden" class="form-control" path="phones[${i}].id" id="phoneId-class-1"/>
                        <form:input type="text" class="form-control" placeholder="Phone number"
                                    path="phones[${i}].value"
                                    id="number-class-1" pattern="(\+373\)[0-9]{8}"
                                    title="Phone number with +373 and remaing 8 digit with 0-9"/>
                    </div>
                    <div class="col-1.5" name="buttons">
                        <button type="button" class="btn btn-success" id="add" value="add" onclick="duplicate(this)">Add
                        </button>
                        <button type="button" class="btn btn-danger" onclick="deleteBlock(this)">Delete</button>
                    </div>
                </div>

            </c:forEach>
        </c:if>
        <c:if test="${student.phones.size() == 0}">
            <div class="form-row" name="phoneRow">
                <div class="col-1" id="title-class-1">
                    <label class="col-sm-2 col-form-label">Phone(s):</label>
                </div>

                <div class="col-1" name="typeDiv">
                    <form:select class="form-control" path="phones[0].phoneType.id">
                        <option <c:if test="${student.phones.size() == 0}">selected</c:if>>Select</option>
                        <c:forEach items="${phoneTypes}" var="phoneType">
                            <option value="${phoneType.id}"
                                    <c:if test="${student.phones[i].phoneType.id == phoneType.id}">selected</c:if>>${phoneType.name}</option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="col-1.4" name="phone">
                    <form:input type="hidden" class="form-control" path="phones[0].id" id="phoneId-class-1"/>
                    <form:input type="text" class="form-control" placeholder="Phone number" path="phones[0].value"
                                id="number-class-1" pattern="(\+373\)[0-9]{8}"
                                title="Phone number with +373 and remaing 8 digit with 0-9"/>
                </div>
                <div class="col-1.5" name="buttons" id="buttons-class-1">
                    <button type="button" class="btn btn-success" value="add" onclick="duplicate(this)">Add</button>
                    <button type="button" class="btn btn-danger" onclick="deleteBlock(this)">Delete</button>
                </div>
            </div>
        </c:if>
    </div>

    <br>
    <div class="form-row">
        <div class="col-1"></div>
        <div class="col-3">
            <form:button type="submit" class="btn btn-secondary btm-sm">Save</form:button>
            <button type="button" onclick="window.close()" class="btn btn-secondary btm-sm">Cancel</button>
        </div>
        <div class="col-2"></div>
        <div class="col-2">
                <%--            <button type="submit" class="btn btn-primary"--%>
                <%--                    onclick="window.open('DISCIPLINE?id='+ ${student.id},'MyWindow' ,'width=1200, height=700'); return false;">--%>
                <%--                Add Disciplines--%>
                <%--            </button>--%>
        </div>
    </div>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
    <script>
        $(document).ready(function () {
            $('#addEdit').ajaxForm(function () {
                window.opener.location.reload();
                window.self.close();
            });
        });
    </script>
</form:form>
</body>
</html>
