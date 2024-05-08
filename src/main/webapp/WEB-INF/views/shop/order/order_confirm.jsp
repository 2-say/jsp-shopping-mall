<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="cart" items="${cartViewList}" varStatus="status">
                            <tr>
                                <th scope="row">
                                    <div class="d-flex align-items-center">
                                        <img src="/loadImage.do?id=${cart.getProduct().getId()}"
                                             class="img-fluid rounded-3" `
                                             style="width: 120px;" alt="Book">
                                        <div class="flex-column ms-4">
                                            <p class="mb-2">${cart.getProduct().getName()}</p>
                                        </div>
                                    </div>
                                </th>
                                <td class="align-middle">
                                    <p class="mb-0" style="font-weight: 500;"> ${cart.getCategoryName()}</p>
                                </td>
                                <td class="align-middle">
                                    <div class="d-flex flex-row">${cart.getProductQuantity()}</div>
                                </td>
                                <td class="align-middle">
                                    <input type="hidden" id="hiddenPrice${status.index}"
                                           value="${cart.getProduct().getPrice()}">
                                    <p class="mb-0" id="totalPrice${status.index}"
                                       style="font-weight: 500;">${cart.getProduct().getPrice() * cart.getProductQuantity()}
                                        원</p>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- 총 결제 금액 표시 -->
                    <div class="text-center">
                        <p class="h5">총 결제 금액: ${totalPrice} 원</p>
                    </div>

                    <div class="text-center">
                        <button class="btn btn-primary btn-lg" onclick="location='/order.do'">결제하기</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</section>
