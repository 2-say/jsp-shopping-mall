<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<script>
    window.onload = function () {
        var errorAttribute = "${error}";
        if (errorAttribute && errorAttribute !== "null") {
            alert(errorAttribute);
        }
    };
</script>

<c:set var="product" value="${item}" />

<section class="testimonial py-5" id="testimonial">
    <div class="container">
        <div class="row ">
            <!-- Left column unchanged -->
            <div class="col-md-4 py-5 bg-primary text-white text-center ">
                <div class=" ">
                    <div class="card-body">
                        <img src="http://www.ansonika.com/mavia/img/registration_bg.svg" style="width:30%">
                        <h2 class="py-3">상품 등록</h2>
                        <p>상품은 정책에 따라 등록해야합니다.
                        </p>
                    </div>
                </div>
            </div>
            <!-- Right column with form -->
            <div class="col-md-8 py-5 border d-grid gap-3">
                <h4 class="pb-4">Please fill with your details</h4>
                <form action="/admin/product.do" method="post" enctype="multipart/form-data">
                    <input type="hidden" id="productId" name="productId" value="${product.getId()}">
                    <div class="form-row">
                        <div class="form-group col-md-6 p-3">
                            <input id="product_name" name="product_name" placeholder="Name" class="form-control" type="text"
                                   value="${product.getName()}">
                            <c:if test="${not empty validate}">
                                <!-- Validation errors handling -->
                            </c:if>
                        </div>
                        <div class="form-group col-md-6 p-3">
                            <input id="product_price" name="product_price" placeholder="Price" class="form-control" type="text"
                                   value="${product.getPrice()}">
                            <c:if test="${not empty validate}">
                                <!-- Validation errors handling -->
                            </c:if>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12 p-3">
                            <textarea id="description" name="description" cols="40" rows="5" class="form-control"
                                      placeholder="Description">${product.getDescription()}</textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6 p-3">
                            <input id="productField" name="productField" placeholder="Product Field"
                                   class="form-control" type="number" value="${product.getProductField()}">
                            <c:if test="${not empty validate}">
                                <!-- Validation errors handling -->
                            </c:if>
                        </div>
                        <div class="form-group col-md-6 p-3">
                            <select id="category" name="category" class="form-control">
                                <c:forEach items="${categories}" var="item" varStatus="status">
                                    <optgroup label="${item.getParentName()}">
                                        <c:forEach items="${item.getCategories()}" var="child" varStatus="status1">
                                            <option value="${child.getId()}"
                                                    <c:if test="${child.getId() eq product.getCategory()}">selected</c:if>>
                                                    ${child.getName()}
                                            </option>
                                        </c:forEach>
                                    </optgroup>
                                </c:forEach>
                            </select>
                            <c:if test="${not empty validate}">
                                <!-- Validation errors handling -->
                            </c:if>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="mb-3">
                            <input type="file" id="files" name="files" multiple>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12 p-3">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="" id="invalidCheck2" required>
                                <label class="form-check-label" for="invalidCheck2">
                                    <small>By clicking Submit, you agree to our Terms & Conditions, Visitor Agreement
                                        and Privacy Policy.</small>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <c:choose>
                            <c:when test="${not empty product}">
                                <button type="submit" class="btn btn-danger mt-3">수정완료</button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="btn btn-danger mt-3">Submit</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
