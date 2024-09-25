<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 조회</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	$(function() {
		$(".modalCloseBtn").click(function() {
			$("#myModal").hide();
		});

	});

	function showRemoveModal() {
		let boardNo = $("#boardNo").val();
		$(".modal-body").html("정말로 " + boardNo + "번 게시글을 삭제하시겠습니까?");
		$("#myModal").show(500); // 0.5초동안 천천히 보여줌
	}
</script>
</head>
<body>
	<jsp:include page="./../header.jsp"></jsp:include>
	<div class="container">
		<div>${board }</div>

		<h1>댓글 조회</h1>

		<div class="boardInfo">
			<div class="input-group mb-3">
				<span class="input-group-text">글 번호</span> <input type="text"
					class="form-control" name="boardNo" id="boardNo"
					value="${board.boardNo }" readonly />
			</div>

			<div class="input-group mb-3">
				<span class="input-group-text">글 제목</span> <input type="text"
					class="form-control" name="title" id="title"
					value="${board.title }" readonly />
			</div>

			<div class="input-group mb-3">
				<span class="input-group-text">작성자(이메일)</span> <input type="text"
					class="form-control" name="writer"
					value="${board.writer }(${board.email})" readonly />
			</div>

			<div class="input-group mb-3">
				<span class="input-group-text">작성일</span> <input type="text"
					class="form-control" id="postDate" value="${board.postDate }"
					readonly />
			</div>

			<div class="input-group mb-3">
				<span class="input-group-text">조회수</span> <input type="text"
					class="form-control" id="readCount" value="${board.readCount }"
					readonly />
			</div>

			<div class="mb-3">
				<label for="content">내용:</label>
				<div class="form-control" rows="5" id="content" name="content">${board.content }</div>
			</div>
		</div>
		<div class="btns">
			<button type="button" class="btn btn-primary"
				onclick="location.href='/cboard/modifyBoard?boardNo=${board.boardNo}'">수정</button>
			<button type="button" class="btn btn-warning"
				onclick="showRemoveModal();">삭제</button>
			<button type="button" class="btn btn-secondary"
				onclick="location.href='/cboard/listAll;'">목록보기</button>
		</div>


	</div>
	<jsp:include page="./../footer.jsp"></jsp:include>
	<!-- The Modal -->
	<div class="modal" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Modal Heading</h4>
					<button type="button" class="btn-close modalCloseBtn"
						data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">Modal body..</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal"
						onclick="location.href='/hboard/removeBoard?boardNo=${param.boardNo}'">삭제</button>
					<button type="button" class="btn btn-primary modalCloseBtn"
						data-bs-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div>
</body>
</html>