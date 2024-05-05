<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<section class="py-5">
    <div class="container px-4 px-lg-5 my-5" th:object="${item}">
        <div class="row gx-4 gx-lg-5 align-items-center" >
            <div class="col-md-6"><img class="card-img-top mb-5 mb-md-0" th:src="|/images/${item.getItemImageListDto().get(0).getStoreName()}|" alt="..." /></div>
            <div class="col-md-6">
                <h1 class="display-5 fw-bolder" th:text="${item.getName()}">Shop item template</h1>
                <div class="fs-5 mb-5">
                    <input type="hidden" th:value="${item.price}" id="price" name="price">
                    <span class="text-decoration-none" th:text="${item.getPrice()}"></span>원
                </div>
                <hr class="my-4">

                <div class="input-group fs-5 mb-5">
                    <div class="input-group-prepend">
                        <input type="hidden" th:value="${item.stockQuantity}" id="stockQuantity" name="stockQuantity">
                        <span class="input-group-text">주문 수량</span>
                    </div>
                    <input class="form-control text-center me-3" id="count" name="count" type="number" value="1" style=" max-width: 5rem" />
                </div>


                <div class="container bg-light fs-5 mb-5">
                    <h6>총 상품 금액</h6>
                    <h4 name="totalPrice" id="totalPrice" class="font-weight-bold" ></h4>
                </div>

                <div class="container bg-light fs-5 mb-5">
                    <h6>상품 설명</h6>
                    <h7><p th:text="${item.getDescription()}" class="mb-0">test description</p></h7>
                </div>


                <div class="d-flex">
                    <form th:action="@{/logout}" class="d-flex" method="post">
                        <button class="btn btn-outline-dark"
                                type="submit">
                            바로 구매하기
                        </button>
                    </form>
                    &nbsp
                    <button class="btn btn-outline-dark flex-shrink-0" type="button">
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
        <p class="lead" id="description" style="text-align: center" th:text="${item.getDescription()}">Lorem ipsum dolor sit amet consectetur adipisicing?</p>
    </div>
    <hr class="my-4">
    <div class="container px-4 px-lg-5 mt-5"  >
        <div th:each="itemImage : ${item.getItemImageListDto()}" class="text-center">
            <img class="card-img-top rounded mb-5 mb-md-0" style="padding-bottom: 50px; width: 550px; height:700px" th:src="|/images/${itemImage.getStoreName()}|" >
        </div>
    </div>
</section>