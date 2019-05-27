<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Send an e-mail</title>
    <link rel="stylesheet" href="/resources/core/css/bootstrap.min.css"/>
</head>
<body>
<form action="/EmailSendingServlet " method="post" id="email">
    <h1>Send New E-mail</h1>
    <div class="form-group row">
        <div class="col-1"></div>
        <div class="col-1">
            <label class="col-sm-3 col-form-label" path="firstName">To</label>
        </div>
        <div class="col-3">
            <input type="text" class="form-control" />

        </div>
    </div>
    <div class="form-group row">
        <div class="col-1"></div>
        <div class="col-1">
            <label class="col-sm-3 col-form-label" >Subject</label>
        </div>
        <div class="col-3">
            <input type="text" class="form-control" />

        </div>
    </div>
    <div class="form-group row">
        <div class="col-1"></div>
        <div class="col-1">
            <label class="col-sm-3 col-form-label" >Content</label>
        </div>
        <div class="col-3">
            <TEXTAREA type="text" class="form-control"> </TEXTAREA>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-1"></div>
        <div class="col-1"></div>
        <div class="col-3">
            <button type="submit" class="btn btn-secondary btm-sm">Send</button>

        </div>
    </div>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
    <script>
        $(document).ready(function () {
            $('#email').ajaxForm(function () {
                window.opener.location.reload();
                window.self.close();
            });
        });
    </script>
</form>
</body>
</html>