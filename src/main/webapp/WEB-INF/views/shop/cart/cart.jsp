<%@ page import="com.nhnacademy.shoppingmall.product.entity.Category" %>
<%@ page import="com.nhnacademy.shoppingmall.category.CategorysDao" %>
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
                            <th scope="col">category</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Price</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="entry" items="${cartMap}" varStatus="status">
                            <c:set var="product" value="${entry.key}"/>
                            <c:set var="quantity" value="${entry.value}"/>
                            <tr>
                                <th scope="row">
                                    <div class="d-flex align-items-center">
                                        <img src="/loadImage.do?id=${product.getId()}" class="img-fluid rounded-3"`
                                             style="width: 120px;" alt="Book">
                                        <div class="flex-column ms-4">
                                            <p class="mb-2">${product.getName()}</p>
                                        </div>
                                    </div>
                                </th>
                                <td class="align-middle">
                                    <p class="mb-0" style="font-weight: 500;">
                                        <%CategorysDao dao = new CategorysDao(); %>
                                        <c:set var="categoryId" value="${product.getCategory()}"/>
                                        <% String categoryName = dao.findById(String.valueOf(pageContext.getAttribute("categoryId"))); %>
                                        <%= categoryName != null ? categoryName : "Category Not Found" %>
                                    </p>
                                </td>
                                <td class="align-middle">
                                    <div class="d-flex flex-row">

                                        <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2" id="downButton${status.index}"
                                                onclick="decrease('form${status.index}', 'hiddenPrice${status.index}' , 'totalPrice${status.index}')">
                                            <i class="fas fa-minus"></i>
                                        </button>

                                        <input id="form${status.index}" min="0" name="quantity" value="${quantity}" type="number"
                                               class="form-control form-control-sm" style="width: 50px;"/>

                                        <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2" id="upButton${status.index}"
                                                onclick="increase('form${status.index}', 'hiddenPrice${status.index}'  , 'totalPrice${status.index}')">
                                            <i class="fas fa-plus"></i>
                                        </button>

                                    </div>
                                </td>
                                <td class="align-middle">
                                    <input type="hidden" id="hiddenPrice${status.index}" value="${product.getPrice()}">
                                    <p class="mb-0" id="totalPrice${status.index}" style="font-weight: 500;">${product.getPrice() * quantity} 원</p>
                                </td>
                                <td class="align-middle">
                                    <a onclick="deleteToCart(${product.getId()})">
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
                            <div class="col-md-6 col-lg-4 col-xl-3 mb-4 mb-md-0">
                                <form>
                                    <div class="d-flex flex-row pb-3">
                                        <div class="d-flex align-items-center pe-2">
                                            <input class="form-check-input" type="radio" name="radioNoLabel"
                                                   id="radioNoLabel1v"
                                                   value="" aria-label="..." checked/>
                                        </div>
                                        <div class="rounded border w-100 p-3">
                                            <p class="d-flex align-items-center mb-0">
                                                <i class="fab fa-cc-mastercard fa-2x text-dark pe-2"></i>Credit
                                                Card
                                            </p>
                                        </div>
                                    </div>
                                    <div class="d-flex flex-row pb-3">
                                        <div class="d-flex align-items-center pe-2">
                                            <input class="form-check-input" type="radio" name="radioNoLabel"
                                                   id="radioNoLabel2v"
                                                   value="" aria-label="..."/>
                                        </div>
                                        <div class="rounded border w-100 p-3">
                                            <p class="d-flex align-items-center mb-0">
                                                <i class="fab fa-cc-visa fa-2x fa-lg text-dark pe-2"></i>Debit Card
                                            </p>
                                        </div>
                                    </div>
                                    <div class="d-flex flex-row">
                                        <div class="d-flex align-items-center pe-2">
                                            <input class="form-check-input" type="radio" name="radioNoLabel"
                                                   id="radioNoLabel3v"
                                                   value="" aria-label="..."/>
                                        </div>
                                        <div class="rounded border w-100 p-3">
                                            <p class="d-flex align-items-center mb-0">
                                                <i class="fab fa-cc-paypal fa-2x fa-lg text-dark pe-2"></i>PayPal
                                            </p>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-6 col-lg-4 col-xl-6">
                                <div class="row">
                                    <div class="col-12 col-xl-6">
                                        <div data-mdb-input-init class="form-outline mb-4 mb-xl-5">
                                            <input type="text" id="typeName" class="form-control form-control-lg"
                                                   siez="17"
                                                   placeholder="John Smith"/>
                                            <label class="form-label" for="typeName">Name on card</label>
                                        </div>

                                        <div data-mdb-input-init class="form-outline mb-4 mb-xl-5">
                                            <input type="text" id="typeExp" class="form-control form-control-lg"
                                                   placeholder="MM/YY"
                                                   size="7" id="exp" minlength="7" maxlength="7"/>
                                            <label class="form-label" for="typeExp">Expiration</label>
                                        </div>
                                    </div>
                                    <div class="col-12 col-xl-6">
                                        <div data-mdb-input-init class="form-outline mb-4 mb-xl-5">
                                            <input type="text" id="typeText1" class="form-control form-control-lg"
                                                   siez="17"
                                                   placeholder="1111 2222 3333 4444" minlength="19" maxlength="19"/>
                                            <label class="form-label" for="typeText">Card Number</label>
                                        </div>

                                        <div data-mdb-input-init class="form-outline mb-4 mb-xl-5">
                                            <input type="password" id="typeText" class="form-control form-control-lg"
                                                   placeholder="&#9679;&#9679;&#9679;" size="1" minlength="3"
                                                   maxlength="3"/>
                                            <label class="form-label" for="typeText">Cvv</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-xl-3">
                                <div class="d-flex justify-content-between" style="font-weight: 500;">
                                    <p class="mb-2">Subtotal</p>
                                    <p class="mb-2">$23.49</p>
                                </div>

                                <div class="d-flex justify-content-between" style="font-weight: 500;">
                                    <p class="mb-0">Shipping</p>
                                    <p class="mb-0">$2.99</p>
                                </div>

                                <hr class="my-4">

                                <div class="d-flex justify-content-between mb-4" style="font-weight: 500;">
                                    <p class="mb-2">Total (tax included)</p>
                                    <p class="mb-2">$26.48</p>
                                </div>

                                <button type="button" data-mdb-button-init data-mdb-ripple-init
                                        class="btn btn-primary btn-block btn-lg">
                                    <div class="d-flex justify-content-between">
                                        <span>Checkout</span>
                                        <span>$26.48</span>
                                    </div>
                                </button>

                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    </div>
</section>

<script>
    function increase(inputId, itemPriceId, TotalPriceId) {
        var inputElement = document.getElementById(inputId);
        inputElement.stepUp();
        updateTotalPrice(inputElement, itemPriceId, TotalPriceId);
    }

    function decrease(inputId, itemPriceId, TotalPriceId) {
        var inputElement = document.getElementById(inputId);
        inputElement.stepDown();
        updateTotalPrice(inputElement, itemPriceId, TotalPriceId);
    }

    function updateTotalPrice(inputElement, itemPriceId, TotalPriceId) {
        var quantity = parseInt(inputElement.value); //개수 가져옴
        var itemPriceValue = parseInt(document.getElementById(itemPriceId).value); //아이템 가격 가져옴
        var totalPrice = itemPriceValue * quantity;

        document.getElementById(TotalPriceId).innerText = totalPrice + ' 원';
    }
    function deleteToCart(itemId) {
        // item id 가져오기
        console.log(itemId);
        // AJAX 요청
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/cartDelete.do", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                location.reload();
            }
        };
        xhr.send("itemId=" + encodeURIComponent(itemId));
    }
</script>