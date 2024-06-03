<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${brand.brandId == null ? 'Add Brand' : 'Edit Brand'}</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
    </style>
</head>
<body>
<div class="container">
    <h1 class="my-4">${brand.brandId == null ? 'Add Brand' : 'Edit Brand'}</h1>
    <%--@elvariable id="brand" type=""--%>
    <form:form method="post" modelAttribute="brand" cssClass="needs-validation" novalidate="novalidate">
        <div class="form-group">
            <label for="brandName">Brand Name:</label>
            <form:input path="brandName" cssClass="form-control" id="brandName" required="true"/>
            <div class="invalid-feedback">
                Please provide a brand name.
            </div>
        </div>
        <div class="form-group">
            <label for="logo">Logo:</label>
            <form:input path="logo" cssClass="form-control" id="logo" required="true"/>
            <div class="invalid-feedback">
                Please provide a logo.
            </div>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <form:textarea path="description" cssClass="form-control" id="description" rows="3" required="true"/>
            <div class="invalid-feedback">
                Please provide a description.
            </div>
        </div>
        <button type="submit" class="btn btn-primary">${brand.brandId == null ? 'Add' : 'Update'}</button>
        <a href="/brands" class="btn btn-secondary">Back to Brand List</a>
    </form:form>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    (function() {
        'use strict';
        window.addEventListener('load', function() {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function(form) {
                form.addEventListener('submit', function(event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>
</body>
</html>
