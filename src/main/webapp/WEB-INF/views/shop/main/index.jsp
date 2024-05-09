<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="btn-toolbar justify-content-center mb-3 bg-light" role="toolbar"
                 aria-label="Toolbar with button groups">

                <c:forEach items="${categories}" var="item" varStatus="status">

                    <div class="btn-group me-2" role="group" aria-label="First group">

                        <button type="button" class="btn btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown"
                                aria-expanded="false">
                                ${item.getParentName()}
                        </button>

                        <ul class="dropdown-menu">
                            <c:forEach items="${item.getCategories()}" var="child" varStatus="status1">
                                <li><a class="dropdown-item" href="index.do?category=${child.getName()}">${child.getName()}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <%--최근 본 상품 --%>
    <div class="btn-group me-2" role="group" aria-label="Recently Viewed Products">
        <button type="button" class="btn btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown"
                aria-expanded="false">
            최근 본 상품
        </button>

        <!-- 최근 본 상품 목록 출력 -->
        <ul class="dropdown-menu">
            <c:forEach items="${recentProducts}" var="product" varStatus="status">
                <li>
                    <a class="dropdown-item" href="productDetails.do?id=${product.getId()}">
                        <div class="bg-success p-2 text-white bg-opacity-75">${product.getName()}</div>
                        <p class="card-text">${item.getName()}</p>
                        <img class="justify-content-center" src="/loadImage.do?id=${product.getId()}" width="180px" height="140px" onerror="this.onerror=null; this.src='/resources/no-image.png';">
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>

</div>

<br>

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
    <c:forEach var="item" items="${pageItem.getContent()}" varStatus="status" begin="0" end="11">
        <div class="col">
            <div class="card shadow-sm">

                <img src="/loadImage.do?id=${item.getId()}" height="300px" onerror="this.onerror=null; this.src='/resources/no-image.png';">

                <div class="card-body">
                    <p class="card-text">${item.getName()}</p>
                    <div class="d-flex justify-content-between align-items-center">
                        <div class="btn-group">
                            <button type="button" class="btn btn-sm btn-outline-secondary" onclick="window.open('get.do?id=${item.getId()}', '_blank');">View</button>
                        </div>
                        <small class="text-muted">${item.getPrice()}원</small>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<br><br><br><br>

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <c:choose>
            <c:when test="${page eq '1'}">
                <li class="page-item disabled">
                    <a class="page-link" href="index.do?page=${page-1}">Previous</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <a class="page-link" href="index.do?page=${page-1}">Previous</a>
                </li>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${pageItem.getTotalCount() % 12 == 0}">
                <c:set var="pageCount" value="${pageItem.getTotalCount() / 12}"/>
            </c:when>
            <c:otherwise>
                <c:set var="pageCount" value="${pageItem.getTotalCount() / 12 + 1}"/>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="1" end="${pageCount}" step="1">
            <li class="page-item"><a class="page-link <c:if test="${page eq i}">bg-info</c:if> " href="index.do?page=${i}">${i}</a></li>
        </c:forEach>

        <c:choose>
        <c:when test="${pageItem.getContent().size() lt 12}">
        <li class="page-item disabled">
            </c:when>
            <c:otherwise>
        <li class="page-item">
            </c:otherwise>
            </c:choose>
            <a class="page-link" href="index.do?page=${page+1}">Next</a>
        </li>
    </ul>
</nav>


