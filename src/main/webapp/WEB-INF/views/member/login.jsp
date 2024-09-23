<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>

	<div class="container">
		<h1>로그인</h1>
		<form action="/member/login" method="post">
			<div class="mb-3 mt-3">
				<label for="userId" class="form-label">아이디:</label><span></span> <input
					type="text" class="form-control" id="userId"
					placeholder="아이디를 입력하세요..." name="userId"> <input
					type="hidden" id="idValid" />
			</div>

			<div class="mb-3 mt-3">
				<label for="userPwd1" class="form-label">비밀번호:</label><span></span>
				<input type="password" class="form-control" id="userPwd1"
					placeholder="비밀번호를 입력하세요..." name="userPwd1">
			</div>
			<button type="submit" class="btn btn-success">로그인</button>
			<button type="reset" class="btn btn-secondary">취소</button>
		</form>
	</div>

	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>