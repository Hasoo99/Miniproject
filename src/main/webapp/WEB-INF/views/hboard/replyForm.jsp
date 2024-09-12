<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 작성</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
<script type="text/javascript">
</script>
<style type="text/css">
</style>
</head>
<body>
	<jsp:include page="./../header.jsp"></jsp:include>
	<div class="container">
		<h1>${param.boardNo }번에 대한 답글 작성 페이지</h1>
		<form action="saveReply" method="post">
			<div class="input-group mb-3">
				<span class="input-group-text">글 제목</span> <input type="text"
					class="form-control" name="title" id="title"
					placeholder="글 제목을 입력하세요...">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">작성자</span> <input type="text"
					class="form-control" name="writer" placeholder="작성자를 입력하세요...">
			</div>

			<div class="mb-3">
				<label for="comment">내용:</label>
				<textarea class="form-control" rows="5" id="content" name="content"
					placeholder="내용을 입력하세요..."></textarea>
			</div>
			
			<div>
				<input type="hidden" name="ref" value="${param.ref }">
				<input type="hidden" name="step" value="${param.step }">
				<input type="hidden" name="refOrder" value="${param.refOrder }">
			</div>

			<button class="btn btn-primary" type="submit"
				onclick="">답글저장</button>
			<button class="btn btn-danger" type="button" onclick="">취소</button>
		</form>
	</div>
	<jsp:include page="./../footer.jsp"></jsp:include>
</body>
</html>