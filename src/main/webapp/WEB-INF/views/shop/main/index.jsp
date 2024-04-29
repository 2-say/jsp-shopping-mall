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
                                <li><a class="dropdown-item" href="#">${child.getName()}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<br>

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
    <c:forEach var="item" items="${page.getContent()}" varStatus="status">
        <div class="col">
            <div class="card shadow-sm">
                <img src="${item.getFilePath()}${item.getFileName()}" height="300px">
                <div class="card-body">
                    <p class="card-text">${item.getDescription()}</p>
                    <div class="d-flex justify-content-between align-items-center">
                        <div class="btn-group">
                            <button type="button" class="btn btn-sm btn-outline-secondary">View</button>
                            <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                        </div>
                        <small class="text-muted">9 mins</small>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<br><br><br><br>

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <li class="page-item disabled">
            <a class="page-link">Previous</a>
        </li>

        <c:choose>
            <c:when test="${page.getTotalCount() % 16 == 0}">
                <c:set var="pageCount" value="${page.getTotalCount() / 16}"/>
            </c:when>
            <c:otherwise>
                <c:set var="pageCount" value="${page.getTotalCount() / 16 + 1}"/>
            </c:otherwise>
        </c:choose>

        <c:forEach var="i" begin="1" end="${pageCount}" step="1">
            <li class="page-item"><a class="page-link" href="#">${i}</a></li>
        </c:forEach>

        <li class="page-item">
            <a class="page-link" href="#">Next</a>
        </li>
    </ul>
</nav>