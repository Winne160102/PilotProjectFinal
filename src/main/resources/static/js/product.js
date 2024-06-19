$(document).ready(function() {
    // Load initial product data
    loadProductData(1);

    // Fetch product data from server
    function loadProductData(pageNumber) {
        $.ajax({
            url: "/product/api/findAll/" + pageNumber,
            type: "GET",
            dataType: "json",
            success: function(response) {
                console.log("API response: ", response);
                if (response && response.data) {
                    renderTable(response.data.productsList);
                    renderPagination(response.data.paginationInfo);
                    totalItem(response.data.totalItem);
                } else {
                    console.log("No products found");
                }
            },
            error: function(xhr, status, error) {
                console.error("Error fetching product data: ", error);
            }
        });
    }

    // Function to render the product table
    function renderTable(productList) {
        var rowHTML = "";
        var format = new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND',
            minimumFractionDigits: 0,
        });

        $("#productInfoTable tbody").empty();
        $.each(productList, function(key, value) {
            rowHTML = "<tr>"
                + "<td>" + value.productName + "</td>"
                + "<td class='text-center'>" + value.quantity + "</td>"
                + "<td class='text-center'>" + format.format(value.price) + "</td>"
                + "<td class='text-center'>" + formatDate(value.saleDate) + "</td>"
                + "<td class='text-center'>" + value.brandEntity.brandName + "</td>"
                + "<td class='text-center'><a href='" + value.image + "' data-toggle='lightbox' data-max-width='1000'><img class='img-fluid' src='" + value.image + "'></a></td>"
                + "<td>" + value.description + "</td>"
                + "<td class='action-btns'>"
                + "<a class='edit-btn' data-id='" + value.productId + "'><i class='fas fa-edit'></i></a> | "
                + "<a class='delete-btn' data-name='" + value.productName + "' data-id='" + value.productId + "'><i class='fas fa-trash-alt'></i></a>"
                + "</td>"
                + "</tr>";
            $("#productInfoTable tbody").append(rowHTML);
        });
    }

    // Function to render pagination
    function renderPagination(paginationInfo) {
        var paginationInnerHtml = "";
        if (paginationInfo.pageNumberList.length > 0) {
            $("ul.pagination").empty();
            paginationInnerHtml += '<li class="page-item"><a class="page-link' + (paginationInfo.firstPage == 0 ? ' disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.firstPage + '">First Page</a></li>';
            paginationInnerHtml += '<li class="page-item"><a class="page-link' + (paginationInfo.previousPage == 0 ? ' disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.previousPage + '"> < </a></li>';
            $.each(paginationInfo.pageNumberList, function(key, value) {
                paginationInnerHtml += '<li class="page-item"><a class="page-link ' + (value == paginationInfo.currentPage ? 'active' : '') + '" href="javascript:void(0)" data-index="' + value +'">' + value + '</a></li>';
            });
            paginationInnerHtml += '<li class="page-item"><a class="page-link ' + (paginationInfo.nextPage == 0 ? ' disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.nextPage + '"> > </a></li>';
            paginationInnerHtml += '<li class="page-item"><a class="page-link ' + (paginationInfo.lastPage == 0 ? ' disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.lastPage + '">Last Page</a></li>';
            $("ul.pagination").append(paginationInnerHtml);
        }
    }

    // Function to show the total number of items
    function totalItem(item) {
        $(".total").empty();
        $(".total").append(item);
    }

    // Show add modal
    $('#addModal').on('click', function() {
        resetForm($('#productInfoForm'));
        showModalWithCustomizedTitle($('#productInfoModal'), "Add Product");
        $('#imagePreview img').attr('src', '/images/image-demo.png');
        $('#productId').closest(".form-group").addClass("d-none");
    });

    // Action save product
    $('#saveProductBtn').on('click', function(event) {
        event.preventDefault();
        var formData = new FormData($('#productInfoForm')[0]);
        var productId = formData.get("productId");
        var isAddAction = productId == undefined || productId == "";

        $('#productInfoForm').validate({
            ignore: [],
            rules: {
                productName: {
                    required: true,
                    maxlength: 100
                },
                price: {
                    required: true,
                    number: true
                },
                imageFile: {
                    required: isAddAction,
                }
            },
            messages: {
                productName: {
                    required: "Please input Product Name",
                    maxlength: "The Product Name must be less than 100 characters",
                },
                price: {
                    required: "Please input Product Price",
                    number: "Please enter a valid price"
                },
                imageFile: {
                    required: "Please upload Product Image",
                }
            },
            errorElement: "div",
            errorClass: "error-message-invalid"
        });

        if ($('#productInfoForm').valid()) {
            // POST data to server-side by AJAX
            $.ajax({
                url: "/product/api/" + (isAddAction ? "add" : "update"),
                type: 'POST',
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false,
                timeout: 10000,
                data: formData,
                success: function(responseData) {
                    if (responseData.responseCode == 100) {
                        $('#productInfoModal').modal('hide');
                        loadProductData(1);
                        showNotification(true, responseData.responseMsg);
                    } else {
                        showMsgOnField($('#productInfoForm').find("#productName"), responseData.responseMsg);
                    }
                }
            });
        }
    });

    // Show update product modal
    $("#productInfoTable").on('click', '.edit-btn', function() {
        // Get product info by product ID
        $.ajax({
            url: "/product/api/findById/" + $(this).data("id"),
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function(responseData) {
                if (responseData.responseCode == 100) {
                    var productInfo = responseData.data;
                    resetForm($('#productInfoForm'));
                    showModalWithCustomizedTitle($('#productInfoModal'), "Edit Product");

                    $('#productId').val(productInfo.productId);
                    $('#productName').val(productInfo.productName);
                    $('#description').val(productInfo.description);
                    $('#price').val(productInfo.price);
                    $('#quantity').val(productInfo.quantity);
                    $('#brandId').val(productInfo.brandEntity.brandId);

                    var productImage = productInfo.image;
                    if (productImage == null || productImage == "") {
                        productImage = "/images/image-demo.png";
                    }
                    $("#imagePreview img").attr("src", productImage);
                    $("#image").val(productImage);
                    $('#productId').closest(".form-group").removeClass("d-none");
                }
            }
        });
    });

    // Submit delete product
    $("#productInfoTable").on('click', '.delete-btn', function() {
        $("#deletedProductName").text($(this).data("name"));
        $("#deleteSubmitBtn").attr("data-id", $(this).data("id"));
        $('#confirmDeleteModal').modal('show');
    });

    $("#deleteSubmitBtn").on('click', function() {
        $.ajax({
            url: "/product/api/delete/" + $(this).attr("data-id"),
            type: 'DELETE',
            dataType: 'json',
            contentType: 'application/json',
            success: function(responseData) {
                $('#confirmDeleteModal').modal('hide');
                showNotification(responseData.responseCode == 100, responseData.responseMsg);
                loadProductData(1);
            }
        });
    });

    // Search product by name
    $('.search-btn').on('click', function() {
        var keyword = $('.search-product').val().toLowerCase();
        findProductByName(keyword, 1);
    });

    function findProductByName(keyword, pageNumber) {
        $.ajax({
            url: "/product/api/search/" + keyword + "/" + pageNumber,
            type: 'GET',
            dataType: 'json',
            contentType: "application/json",
            success: function(responseData) {
                if (responseData.responseCode == 100) {
                    renderTable(responseData.data.productsList);
                    renderPagination(responseData.data.paginationInfo);
                    if (responseData.data.paginationInfo.pageNumberList.length < 2) {
                        $('.pagination').addClass("d-none");
                    } else {
                        $('.pagination').removeClass("d-none");
                    }
                    totalItem(responseData.data.totalItem);
                }
            }
        });
    }

    // Pagination click event
    $('.pagination').on('click', '.page-link', function() {
        var pageNumber = $(this).attr("data-index");
        var keyword = $('.search-product').val();
        if (keyword != "") {
            findProductByName(keyword, pageNumber);
        } else {
            loadProductData(pageNumber);
        }
    });

    // Utility functions
    function resetForm($form) {
        $form[0].reset();
        $form.validate().resetForm();
        $form.find(".form-group").removeClass("has-error");
    }

    function showModalWithCustomizedTitle($modal, title) {
        $modal.find(".modal-title").text(title);
        $modal.modal('show');
    }

    function showNotification(success, message) {
        var alertType = success ? 'alert-success' : 'alert-danger';
        var alertHtml = '<div class="alert ' + alertType + ' alert-dismissible fade show" role="alert">'
            + message
            + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>'
            + '</div>';
        $("#notificationArea").html(alertHtml);
        setTimeout(function() {
            $("#notificationArea").html('');
        }, 5000);
    }

    function showMsgOnField($field, message) {
        var errorHtml = '<div class="error-message-invalid">' + message + '</div>';
        $field.closest(".form-group").append(errorHtml);
    }

    function formatDate(dateString) {
        var options = { year: 'numeric', month: 'long', day: 'numeric' };
        var date = new Date(dateString);
        return date.toLocaleDateString('vi-VN', options);
    }
});
