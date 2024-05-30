<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
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
    <c:forEach items="${brandList}" var="brand">
        <tr>
            <td>"test value"</td>
            <td>"test value"</td>
            <td>"test value"</td>
            <td>"test value"</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>