<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
  <title>Product Management</title>
  <jsp:include page="../common/head.jsp" />
  <link rel="stylesheet" href="<c:url value='/css/product.css'/>">
</head>
<body>
<header>
  <div class="container">
    <div class="d-flex">
      <div class="main-logo">Pilot Project</div>
      <a class="ml-5 nav-link active" href="/product">Product</a>
      <a class="nav-link" href="/brand">Brand</a>
    </div>
  </div>
</header>
<div class="container">
  <div class="sub-header">
    <div class="float-left sub-title">Product Management</div>
    <div class="float-left main-search">
      <input class="search-product" type="text" placeholder="Search..."/>
      <button type="submit" class="search-btn"><i class="fa fa-search"></i></button>
    </div>
    <div class="float-right"><a class="btn btn-success add-btn" id="addModal"><i class="fas fa-plus-square"></i> Add Product</a></div>
  </div>
  <table class="table table-bordered" id="productInfoTable">
    <thead>
    <tr class="text-center">
      <th scope="col">Name</th>
      <th scope="col">Quantity</th>
      <th scope="col" class="price">Price</th>
      <th scope="col" class="sale-date">Sale Date</th>
      <th scope="col">Brand Name</th>
      <th scope="col">Image</th>
      <th scope="col">Description</th>
      <th scope="col">Actions</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
  </table>

  <div class="d-flex justify-content-center">
    <div class="total-items">
      <span>Total Items: </span>
    </div>&nbsp;&nbsp;
    <div class="total">

    </div>
    &nbsp;&nbsp;
    <ul class="pagination">
    </ul>
  </div>
</div>
<!-- Modal Add and Edit Product -->
<div class="modal fade" id="productInfoModal">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <form id="productInfoForm" role="form" enctype="multipart/form-data">
        <div class="modal-header">
          <h5 class="modal-title">Add/Edit Product</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <div class="form-group d-none">
            <label>Product ID</label>
            <input type="text" class="form-control" id="productId" name="productId" placeholder="Product Id" readonly>
          </div>
          <div class="form-group">
            <label for="productName">Product Name <span class="required-mask">(*)</span></label>
            <input type="text" class="form-control" id="productName" name="productName" placeholder="Product name">
          </div>
          <div class="form-group">
            <label for="image">Image <span class="required-mask">(*)</span></label>
            <div class="preview-image-upload" id="imageImg">
              <img src="<c:url value='/images/1.jpeg'/>" alt="image">
            </div>
            <input type="file" class="form-control upload-image" name="imageFiles" accept="image/*" />
            <input type="hidden" class="old-img" id="image" name="image">
          </div>
          <div class="form-group">
            <label for="description">Description</label>
            <textarea name="description" id="description" cols="30" rows="3" class="form-control" placeholder="Description"></textarea>
          </div>
          <div class="form-group">
            <label for="price">Price <span class="required-mask">(*)</span></label>
            <input type="text" class="form-control" id="price" name="price" placeholder="Price">
          </div>
          <div class="form-group">
            <label for="quantity">Quantity <span class="required-mask">(*)</span></label>
            <input type="text" class="form-control" id="quantity" name="quantity" placeholder="Quantity">
          </div>
          <div class="form-group">
            <label for="brandId">Brand ID <span class="required-mask">(*)</span></label>
            <input type="text" class="form-control" id="brandId" name="brandId" placeholder="Brand ID">
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
          <button type="button" class="btn btn-primary" id="saveProductBtn">Save</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Modal Confirm Delete -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="confirmDeleteModalLabel">Confirm Delete</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Are you sure you want to delete <span class="product-name font-weight-bold"></span>?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-danger" id="deleteSubmitBtn">Delete</button>
      </div>
    </div>
  </div>
</div>

<jsp:include page="../common/footer.jsp" />
<script src="<c:url value='/js/base.js'/>"></script>
<script src="<c:url value='/js/product.js'/>"></script>
</body>
</html>
