<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="h-100 h-custom">
    <div class="container h-100 py-5">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col">

                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col" class="h5">Shopping Bag</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Price</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="item" items="${cartViewList.getProducts()}" varStatus="status">
                            <tr>
                                <th scope="row">
                                    <div class="d-flex align-items-center">
                                        <img src="/loadImage.do?productId=${item.getProduct().getId()}"
                                             class="img-fluid rounded-3" `
                                             style="width: 120px;" alt="no Image">
                                        <div class="flex-column ms-4">
                                            <p class="mb-2">${item.getProduct().getName()}</p>
                                        </div>
                                    </div>
                                </th>

                                <td class="align-middle">
                                    <div class="d-flex flex-row">

                                        <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2"
                                                id="downButton${status.index}"
                                                onclick="decrease('form${status.index}', 'hiddenPrice${status.index}' , 'totalPrice${status.index}', ${cartViewList.getCartId()}, ${item.getProduct().getId()})">
                                            <i class="fas fa-minus"></i>
                                        </button>

                                        <input id="form${status.index}" min="1" name="quantity"
                                               value="${item.getSelectQuantity()}"
                                               type="number"
                                               class="form-control form-control-sm" style="width: 50px;"/>

                                        <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2"
                                                id="upButton${status.index}"
                                                onclick="increase('form${status.index}', 'hiddenPrice${status.index}'  , 'totalPrice${status.index}', ${cartViewList.getCartId()} , ${item.getProduct().getId()})">
                                            <i class="fas fa-plus"></i>
                                        </button>

                                    </div>
                                </td>
                                <td class="align-middle">
                                    <input type="hidden" id="hiddenPrice${status.index}"
                                           value="${item.getProduct().getPrice()}">
                                    <p class="mb-0" id="totalPrice${status.index}"
                                       style="font-weight: 500;">${item.getProduct().getPrice() * item.getSelectQuantity()}
                                        원</p>
                                </td>
                                <td class="align-middle">
                                    <a onclick="deleteToCart(${cartViewList.getCartId()})">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30"
                                             fill="currentColor"
                                             class="bi bi-trash3" viewBox="0 0 16 16" id="cart-delete">
                                            <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5M11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47M8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5"/>
                                        </svg>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>

                <div class="card shadow-2-strong mb-5 mb-lg-0" style="border-radius: 16px;">
                    <div class="card-body p-4">
                        <div class="row">
                            <form id="checkoutForm" action="/orderSave.do" method="post">
                                <div class="d-flex flex-column flex-xl-row">
                                    <div class="col-md col-lg-4 col-xl-6 mb-4 mb-xl-0">
                                        <div class="row">
                                            <div class="col-6 col-xl-6">
                                                <div data-mdb-input-init class="form-outline mb-4 mb-xl-5">
                                                    <input type="text" id="addressee" name="addressee" class="form-control form-control-lg" placeholder="홍길동"/>
                                                    <label class="form-label" for="addressee">받는 사람</label>
                                                    <c:if test="${not empty validate}">
                                                        <c:forEach var="error" items="${validate}">
                                                            <c:if test="${error.propertyPath == 'addressee'}">
                                                                <span style="color: red;">${error.message}</span>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                </div>

                                                <div data-mdb-input-init class="form-outline mb-4 mb-xl-5">
                                                    <input type="text" id="address" name="address" class="form-control form-control-lg" placeholder="xx도 xx시" id="exp" minlength="2"/>
                                                    <label class="form-label" for="address">배송지</label>
                                                    <c:if test="${not empty validate}">
                                                        <c:forEach var="error" items="${validate}">
                                                            <c:if test="${error.propertyPath == 'address'}">
                                                                <span style="color: red;">${error.message}</span>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                </div>
                                            </div>
                                            <div class="col-6 col-xl-6">
                                                <div data-mdb-input-init class="form-outline mb-4 mb-xl-5">
                                                    <input type="text" id="phone" name="phone" class="form-control form-control-lg" placeholder="010-xxxx-xxxx" minlength="3" maxlength="20"/>
                                                    <label class="form-label" for="phone">받는 분 전화번호</label>
                                                    <c:if test="${not empty validate}">
                                                        <c:forEach var="error" items="${validate}">
                                                            <c:if test="${error.propertyPath == 'phone'}">
                                                                <span style="color: red;">${error.message}</span>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                </div>

                                                <div data-mdb-input-init class="form-outline mb-4 mb-xl-5">
                                                    <input type="text" id="comment" name="comment" class="form-control form-control-lg" />
                                                    <label class="form-label" for="comment">요청 사항</label>
                                                    <c:if test="${not empty validate}">
                                                        <c:forEach var="error" items="${validate}">
                                                            <c:if test="${error.propertyPath == 'comment'}">
                                                                <span style="color: red;">${error.message}</span>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-4 col-xl-3"></div>

                                    <div class="col-lg-4 col-xl-3  justify-content-end">
                                        <div class="d-flex justify-content-between mb-4" style="font-weight: 500;">
                                            <p class="mb-2">원가</p>
                                            <p class="mb-2">$23.49 원</p>
                                        </div>

                                        <div class="d-flex justify-content-between mb-4" style="font-weight: 500;">
                                            <p class="mb-0">포인트 할인</p>
                                            <p class="mb-0">-2.99 원</p>
                                        </div>

                                        <hr class="my-4">

                                        <div class="d-flex justify-content-between mb-4" style="font-weight: 500;">
                                            <p class="mb-2">Total </p>
                                            <p class="mb-2">26.48 원</p>
                                        </div>

                                        <button type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block btn-lg">
                                            <div class="d-flex justify-content-between">
                                                <span>결제하기: </span>
                                                <span>$26.48</span>
                                            </div>
                                        </button>
                                    </div>
                                </div>

                            </form>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    </div>
</section>

<script>
    function increase(inputId, itemPriceId, TotalPriceId, cartId, productId) {
        var inputElement = document.getElementById(inputId);
        inputElement.stepUp();
        updateTotalPrice(inputElement, itemPriceId, TotalPriceId);

        //ajax로 DB 1 수량 1 증가
        addToCart(cartId, parseInt(inputElement.value), productId);
    }

    function decrease(inputId, itemPriceId, TotalPriceId, cartId, productId) {
        var inputElement = document.getElementById(inputId);
        inputElement.stepDown();
        updateTotalPrice(inputElement, itemPriceId, TotalPriceId);

        //ajax로 DB 1 수량 1 증가
        addToCart(cartId, parseInt(inputElement.value), productId);
    }

    function updateTotalPrice(inputElement, itemPriceId, TotalPriceId) {
        var quantity = parseInt(inputElement.value); //개수 가져옴
        var itemPriceValue = parseInt(document.getElementById(itemPriceId).value); //아이템 가격 가져옴
        var totalPrice = itemPriceValue * quantity;

        document.getElementById(TotalPriceId).innerText = totalPrice + ' 원';
    }

    function deleteToCart(cartId) {
        // AJAX 요청
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/cartDelete.do", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                location.reload();
            }
        };
        xhr.send("cartId=" + encodeURIComponent(cartId));
    }

    function addToCart(cartId, productField, productId) {
        // AJAX 요청
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/cartUpdate.do", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        // 요청 데이터 설정
        var requestData = "productId=" + encodeURIComponent(productId) + "&cartId=" + encodeURIComponent(cartId) + "&productField=" + encodeURIComponent(productField);
        xhr.send(requestData);
    }


</script>
