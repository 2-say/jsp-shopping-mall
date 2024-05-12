<%--
  Created by IntelliJ IDEA.
  User: isehui
  Date: 5/6/24
  Time: 7:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-5">
    <div class="alert alert-success" role="alert">
        <h4 class="alert-heading">주문 및 결제가 성공적으로 완료되었습니다!</h4>
        <p>고객님의 주문과 결제가 성공적으로 접수되었습니다.</p>
        <hr>
        <h5 class="mb-3">주문 내역:</h5>
        <ul class="list-group">
            <li class="list-group-item">상품 1: $50.00</li>
            <li class="list-group-item">상품 2: $30.00</li>
            <li class="list-group-item">배송비: $5.00</li>
        </ul>
        <hr>
        <p><strong>총 결제액: $85.00</strong></p>
        <p class="mb-0">문의사항이 있으시면 저희에게 연락 주시기 바랍니다.</p>
        <div class="mt-3">
            <a href="/index.do" class="btn btn-primary">쇼핑하러가기</a>
        </div>
    </div>
</div>