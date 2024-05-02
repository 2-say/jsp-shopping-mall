<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="userID" value="${user_ID}"/>
<c:set var="userAUTH" value="${user_AUTH}"/>

<!doctype html>
<html lang="ko">
<head>
    <!-- Required meta tags -->
    <link href="admin/css/style.css" rel="stylesheet"/>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Dashboard - SB Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <title>nhn아카데미 shopping mall</title>

    <style>
        /* Add your custom styles here */
        body {
            background-color: #f8f9fa;
        }

        .card {
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card-header {
            background-color: #007bff;
            color: white;
            border-radius: 15px 15px 0 0;
        }

        .card-title {
            font-size: 24px;
        }

        .form-group label {
            font-weight: bold;
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
    </style>

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
                        <li><a href="/mypage.do" class="nav-link px-2 text-white">마이페이지</a></li>
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


</body>
</html>
