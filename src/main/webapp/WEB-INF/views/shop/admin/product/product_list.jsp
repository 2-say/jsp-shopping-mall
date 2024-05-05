<%@ page import="com.nhnacademy.shoppingmall.category.CategorysDao" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container-fluid">
    <div style="margin: 0 auto; width: 100%;"> <!-- 가운데 정렬을 위한 스타일 추가 -->
        <h1 class="mt-4 text-center">Board</h1> <!-- 가운데 정렬 -->

        <div class="card mb-8 ">
            <div class="card-header">
                <c:forEach items="${categories}" var="item" varStatus="status">

                    <div class="btn-group me-2 " role="group" aria-label="First group">

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

                <a class="btn btn-primary float-end" href="register">
                    <i class="fas fa-edit"></i> 상품 추가
                </a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover table-striped">
                        <thead>
                        <tr>
                            <th>글번호</th>
                            <th>이름</th>
                            <th>가격</th>
                            <th>수량</th>
                            <th>카테고리</th>
                            <th>수정</th>
                            <th>삭제</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageItem.getContent()}" var="item" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td><a href="get.do?id=${item.getId()}">${item.getName()}</a></td>
                                <td>${item.getPrice()}</td>
                                <td>${item.getProductField()}</td>

                                <td>
                                    <%CategorysDao dao = new CategorysDao(); %>
                                    <c:set var="categoryId" value="${item.getCategory()}"/>
                                    <% String categoryName = dao.findById(String.valueOf(pageContext.getAttribute("categoryId"))); %>
                                    <%= categoryName != null ? categoryName : "Category Not Found" %>
                                </td>

                                <td>
                                    <a href="/admin/productEdit.do?productId=${item.getId()}" class="btn btn-info btn-sm">
                                        <i class="fas fa-edit"></i> 수정
                                    </a>
                                </td>

                                <td>
                                    <a href="/admin/productDelete.do?productId=${item.getId()}" class="btn btn-danger btn-sm">
                                        <i class="fas fa-trash-alt"></i> 삭제
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<br><br><br><br>

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <c:choose>
            <c:when test="${page eq '1'}">
                <li class="page-item disabled">
                    <a class="page-link" href="productList.do?page=${page-1}">Previous</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <a class="page-link" href="productList.do?page=${page-1}">Previous</a>
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
        <c:forEach var="i" begin="1" end="${pageCount + 1}" step="1">
            <li class="page-item"><a class="page-link <c:if test="${page eq i}">bg-info</c:if> "
                                     href="productList.do?page=${i}">${i}</a></li>
        </c:forEach>

        <c:choose>
        <c:when test="${pageItem.getContent().size() lt 12}">
        <li class="page-item disabled">
            </c:when>
            <c:otherwise>
        <li class="page-item">
            </c:otherwise>
            </c:choose>
            <a class="page-link" href="productList.do?page=${page+1}">Next</a>
        </li>
    </ul>
</nav>