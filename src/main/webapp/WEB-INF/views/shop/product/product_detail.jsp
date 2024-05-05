<%@ page import="com.nhnacademy.shoppingmall.common.util.FileUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<section class="py-5">
    <div class="container px-4 px-lg-5 my-5">
        <div class="row gx-4 gx-lg-5 align-items-center">

            <div class="col-md-6">

                <div id="carouselExampleIndicators" class="carousel carousel-dark slide" data-bs-ride="carousel">
                    <div class="carousel-indicators">
                        <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                        <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1" aria-label="Slide 2"></button>
                        <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2" aria-label="Slide 3"></button>
                    </div>
                    <div class="carousel-inner">
                        <c:forEach var="image" items="${imageList}">
                            <div class="carousel-item active">
                                <img src="/loadImage.do?image=${image}" class="d-block w-100" width="500px" height="500px"
                                     onerror="this.onerror=null; this.src='/resources/no-image.png';">
                            </div>
                        </c:forEach>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators"
                            data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators"
                            data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>

            </div>


            <div class="col-md-6">
                <h1 class="display-5 fw-bolder">${item.getName()}</h1>
                <div class="fs-5 mb-5">
                    <input type="hidden" value="${item.getPrice()}" id="price" name="price">
                    <span class="text-decoration-none">${item.getPrice()}</span>원
                </div>
                <hr class="my-4">

                <div class="input-group fs-5 mb-5">
                    <div class="input-group-prepend">
                        <input type="hidden" value="${item.getProductField()}" id="${item.getProductField()}"
                               name="${item.getProductField()}">
                        <span class="input-group-text">주문 수량</span>
                    </div>
                    <input class="form-control text-center me-3" id="count" name="count" type="number" value="1"
                           style=" max-width: 5rem"/>
                </div>


                <div class="container bg-light fs-5 mb-5">
                    <h6>총 상품 금액</h6>
                    <h4 name="totalPrice" id="totalPrice" class="font-weight-bold"></h4>
                </div>

                <div class="container bg-light fs-5 mb-5">
                    <h6>상품 설명</h6>
                    <h7><p class="mb-0">${item.getDescription()}</p></h7>
                </div>


                <div class="d-flex">
                    <form action="@{/logout}" class="d-flex" method="post">
                        <button class="btn btn-outline-dark"
                                type="submit">
                            바로 구매하기
                        </button>
                    </form>
                    &nbsp
                    <button class="btn btn-outline-dark flex-shrink-0" type="button" onclick="addToCart()">
                        <i class="bi-cart-fill me-1"></i>
                        장바구니 담기
                    </button>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Related items section-->
<section class="py-5 bg-light">
    <div class="container">
        <p class="lead" id="description" style="text-align: center">이런 상품은 어때요?</p>
    </div>
    <hr class="my-4">
    <div class="container px-4 px-lg-5 mt-5">
        <div class="text-center">
            <img class="card-img-top rounded mb-5 mb-md-0" style="padding-bottom: 50px; width: 350px; height:300px"
                 src="/resources/product/common/raccoon_pedro.gif">
        </div>
    </div>
</section>

<script>
    // 페이지가 로드될 때 초기 상품 가격 설정
    document.addEventListener('DOMContentLoaded', function () {
        updateTotalPrice();
    });

    // 수량이 변경될 때마다 호출되는 함수
    document.getElementById('count').addEventListener('input', function () {
        updateTotalPrice();
    });

    // 총 상품 가격을 업데이트하는 함수
    function updateTotalPrice() {
        // 수량과 상품 가격 가져오기
        var count = parseInt(document.getElementById('count').value);
        var price = parseInt(document.getElementById('price').value);

        // 총 상품 가격 계산
        var totalPrice = count * price;

        // 총 상품 가격을 출력하는 요소 찾아서 업데이트
        document.getElementById('totalPrice').innerText = totalPrice.toLocaleString() + '원';
    }

    function addToCart() {
        // item id 가져오기
        var itemId = "${item.getId()}";

        // AJAX 요청
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/cartAdd.do", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // 요청 완료 시 실행할 코드
                alert("상품이 장바구니에 추가되었습니다.");
            }
        };
        xhr.send("itemId=" + encodeURIComponent(itemId));
    }
</script>