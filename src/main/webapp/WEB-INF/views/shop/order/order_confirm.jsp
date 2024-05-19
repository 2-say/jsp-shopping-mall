<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    window.onload = function () {
        var errorAttribute = "${error}";
        if (errorAttribute && errorAttribute !== "null") {
            alert(errorAttribute);
        }
    };
</script>

<section class="h-100 h-custom">
    <div class="container h-100 py-5">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col">
                <div class="text-center">
                    <h1>주문 완료</h1>
                </div>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col" class="h5">Shopping Bag</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Price</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${orderCompleteViewDTO.getProducts()}" varStatus="status">
                            <tr>
                                <th scope="row">
                                    <div class="d-flex align-items-center">
                                        <img src="/loadImage.do?productId=${item.getProduct().getId()}"
                                             class="img-fluid rounded-3" `
                                             style="width: 120px;" alt="Book">
                                        <div class="flex-column ms-4">
                                            <p class="mb-2">${item.getProduct().getName()}</p>
                                        </div>
                                    </div>
                                </th>
                                <td class="align-middle">
                                    <div class="d-flex flex-row">${item.getSelectQuantity()}</div>
                                </td>
                                <td class="align-middle">
                                    <input type="hidden" id="hiddenPrice${status.index}"
                                           value="${item.getProduct().getPrice()}">
                                    <p class="mb-0" id="totalPrice${status.index}"
                                       style="font-weight: 500;">${item.getProduct().getPrice() * item.getSelectQuantity()}
                                        원</p>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="container h-100 py-5">
                        <div class="row d-flex justify-content-center align-items-center h-100">
                            <div class="col">
                                <div class="table-responsive">
                                    <!-- Your table content here -->
                                </div>
                                <!-- Additional shipping information fields -->
                                <div class="mt-4">
                                    <div class="mb-3">
                                        <label class="form-label">배송자</label>
                                        <p class="form-control-static">${orderCompleteViewDTO.getOrderDetail().getAddressee()}</p>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">배송지</label>
                                        <p class="form-control-static">${orderCompleteViewDTO.getOrderDetail().getAddress()}</p>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">요청 메시지</label>
                                        <p class="form-control-static">${orderCompleteViewDTO.getOrderDetail().getComment()}</p>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">전화번호</label>
                                        <p class="form-control-static">${orderCompleteViewDTO.getOrderDetail().getPhone()}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 총 결제 금액 표시 -->
                    <div class="text-center">
                        <p class="h5">총 결제 금액: ${orderCompleteViewDTO.getTotalPrice()} 원</p>
                    </div>

                </div>
            </div>
        </div>
    </div>
</section>
