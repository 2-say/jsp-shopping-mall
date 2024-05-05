<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="userID" value="${user_ID}"/>
<c:set var="userAUTH" value="${user_AUTH}"/>

<!doctype html>
<html lang="ko">
<head>
    <!-- Required meta tags -->
    <link href="${pageContext.request.contextPath}/resources/static/css/style.css" rel="stylesheet"/>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <title>nhn아카데미 shopping mall</title>

</head>
<body>
<div class="mainContainer">
    <header class="p-3 bg-dark text-white">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

                <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                    <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                        <use xlink:href="#bootstrap"></use>
                    </svg>
                </a>

                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="/index.do" class="nav-link px-2 text-secondary">Home</a></li>

                    <c:if test="${not empty userID}">
                        <li><a href="/mypage.do" class="nav-link px-2 text  -white">마이페이지</a></li>
                    </c:if>
                    <c:if test="${userAUTH eq 'ROLE_ADMIN'}">
                        <div class="d-md-flex">
                            <a class="btn btn-outline-light me-2" href="/admin/index.do">관리자 페이지</a>
                        </div>
                    </c:if>
                </ul>

                <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                    <input type="search" class="form-control form-control-dark" placeholder="Search..."
                           aria-label="Search">
                </form>

                <div class="text-end">
                    <c:choose>
                        <c:when test="${not empty userID}">
                            <span class="text-white me-2">Welcome ${userID}</span>
                            <a class="btn btn-outline-light me-2" href="/logout.do">로그아웃</a>

                            <a href="/cart.do" target="_blank">
                                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="50" fill="currentColor"
                                     class="bi bi-cart3" viewBox="0 0 16 16" id="cart-icon" data-bs-toggle="tooltip"
                                     data-bs-placement="bottom" title="Tooltip on bottom">
                                    <path id="cart"
                                          d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .49.598l-1 5a.5.5 0 0 1-.465.401l-9.397.472L4.415 11H13a.5.5 0 0 1 0 1H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M3.102 4l.84 4.479 9.144-.459L13.89 4zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2m7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2"/>
                                </svg>
                            </a>

                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-outline-light me-2" href="/login.do">로그인</a>
                            <a class="btn btn-warning" href="signup.do">회원가입</a>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
        </div>
    </header>

    <main>
        <div class="album py-5 bg-light">
            <div class="container">
                <jsp:include page="${layout_content_holder}"/>
            </div>
        </div>
    </main>

    <footer class="text-muted py-5">
        <div class="container">
            <p class="float-end mb-1">
                <a href="#">Back to top</a>
            </p>
            <p class="mb-1">shoppingmall example is © nhnacademy.com</p>
        </div>
    </footer>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/resources/static/js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/resources/static/assets/demo/chart-area-demo.js"></script>
<script src="${pageContext.request.contextPath}/resources/static/assets/demo/chart-bar-demo.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/resources/static/js/datatables-simple-demo.js"></script>

</body>
</html>
