<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<div class="container mt-5">
    <div class="card">
        <div class="card-header">
            <h5 class="card-title text-center">마이페이지</h5>
        </div>
        <div class="card-body">
            <form method="post" action="/userUpdate.do">
                <div class="form-group row mb-4">
                    <label for="user_id" class="col-sm-3 col-form-label">사용자 아이디</label>
                    <div class="col-sm-9">
                        <input type="text" readonly class="form-control-plaintext" name="user_id" id="user_id" value="${user.getUserId()}">
                    </div>
                </div>
                <div class="form-group row mb-4">
                    <label for="user_name" class="col-sm-3 col-form-label">사용자 이름</label>
                    <div class="col-sm-9">
                        <input type="text" readonly class="form-control-plaintext" name="user_name" id="user_name" value="${user.getUserName()}">
                    </div>
                </div>
                <div class="form-group row mb-4">
                    <label for="user_password" class="col-sm-3 col-form-label">사용자 비밀번호</label>
                    <div class="col-sm-9">
                        <input type="password" class="form-control" name="user_password" id="user_password" value="${user.getUserPassword()}">
                    </div>
                </div>
                <div class="form-group row mb-4">
                    <label for="birthdate" class="col-sm-3 col-form-label">생년월일</label>
                    <div class="col-sm-9">
                        <input type="date" class="form-control" name="birthdate" id="birthdate" value="${user.getParsingUserBirth()}">
                    </div>
                </div>
                <div class="form-group row mb-4">
                    <label for="user_point" class="col-sm-3 col-form-label">적립된 포인트</label>
                    <div class="col-sm-9">
                        <input type="text" readonly class="form-control-plaintext" name="user_point" id="user_point" value="${user.getUserPoint()}">
                    </div>
                </div>
                <div class="form-group row mb-4">
                    <label for="latest_login_at" class="col-sm-3 col-form-label">마지막 로그인 시간</label>
                    <div class="col-sm-9">
                        <input type="text" readonly class="form-control-plaintext" name="latest_login_at" id="latest_login_at" value="${user.getLatestLoginAt().toString()}">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-9 offset-sm-3">
                        <button type="button" class="btn btn-secondary mr-3" onclick="history.back()" >취소</button>
                        <button type="submit" class="btn btn-primary">제출</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
