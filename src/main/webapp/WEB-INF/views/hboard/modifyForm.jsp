<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
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
		<c:forEach var="board" items="${boardDetailInfo }">
			<h1>${board.boardNo }번글 수정</h1>
			<form action="saveModify" method="post">
				<div class="input-group mb-3">
					<span class="input-group-text">글 제목</span> <input type="text"
						class="form-control" name="title" id="title"
						value="${board.title }">
				</div>
				<div class="input-group mb-3">
					<span class="input-group-text">작성자</span> <input type="text"
						class="form-control" name="writer" readonly
						value="${board.writer }">
				</div>

				<div class="mb-3">
					<label for="comment">내용:</label>
					<textarea class="form-control" rows="5" id="content" name="content">${board.content }</textarea>
				</div>
				<button class="btn btn-primary" type="submit" onclick="">수정완료</button>
				<button class="btn btn-danger" type="button" onclick="">취소</button>
			</form>
		</c:forEach>
	</div>
	<jsp:include page="./../footer.jsp"></jsp:include>
</body>
</html>