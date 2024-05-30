<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <meta charset="UTF-8">
    <title>All Brands</title>
</head>
<body>
<h1>All Brands</h1>
<table border="1">
    <thead>
    <tr>
        <th>Brand ID</th>
        <th>Brand Name</th>
        <th>Logo</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${brands}" var="brand">
        <tr>
            <td>${brand.brandId}</td>
            <td>${brand.brandName}</td>
            <td>
                <img src="images/${brand.logo}" alt="${brand.brandName}" width="50" height="50" name="logoFiles" >
            </td>
            <td>${brand.description}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
