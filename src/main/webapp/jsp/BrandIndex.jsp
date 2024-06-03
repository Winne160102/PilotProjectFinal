<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Brands</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1 class="my-4">All Brands</h1>
    <a href="brands/add" class="btn btn-primary mb-3">Add Brand</a>
    <table class="table table-bordered table-hover">
        <thead class="thead-dark">
        <tr>
            <th>Brand ID</th>
            <th>Brand Name</th>
            <th>Logo</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${brands}" var="brand">
            <tr>
                <td>${brand.brandId}</td>
                <td>${brand.brandName}</td>
                <td>
                    <img src="images/${brand.logo}" alt="${brand.brandName}" class="img-thumbnail" width="50" height="50">
                </td>
                <td>${brand.description}</td>
                <td>
                    <a href="brands/edit/${brand.brandId}" class="btn btn-secondary">Edit</a>
                    <a href="brands/delete/${brand.brandId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this brand?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
