<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 수정</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- include summernote css/js -->
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#summernote').summernote({
			maximumImageFileSize : 1024 * 1024 * 10,
			placeholder : 'Hello Bootstrap 5',
			tabsize : 2,
			height : 100
		});
	});

	function cancelBoard() {
		// 취소 버튼을 클릭하면 업로드한 파일을 모두 삭제 해야 한다.
		// 서버에 저장한 해당 글작성시 업로드한 모든 파일을 지우고
		// view단에서 태그 삭제
		$.ajax({
			url : '/hboard/cancelBoard', // 데이터가 송수신될 서버의 주소
			type : "GET", // 통upfiles신 방식 (GET, POST, PUT, DELETE)
			dataType : "text", // 수신 받을 데이터 타입 (MIME TYPE)
			success : function(data) {
				// 통신이 성공하면 수행할 함수
				console.log(data);
				if (data == 'success') {
					// upfiles배열에서 삭제
					/* upfiles.splice(i, 1);
					console.log(upfiles);
					$(obj).parent().parent().remove(); // 미리보기 태그 삭제 */
					location.href = "/hboard/listAll"
				}
			},
			error : function() {
			},
			complete : function() {
			},
		});

	}

	function validBoard() {
		// 게시글의 제목 (not null) 유효성 검사
		let result = false;
		let title = $("#title").val();
		console.log(title === '');
		console.log(title.length < 1);
		console.log(title == null);

		if (title == '') {
			// 제목을 입력하지 않았을 때
			alert("제목은 반드시 입력하셔야 합니다.");
			$("#title").focus();
		} else {
			result = true;
		}

		return result;
	}
</script>
<style type="text/css">
.fileUploadArea {
	width: 100%;
	height: 200px;
	background-color: gray;
	text-align: center;
	line-height: 200px;
}
</style>
</head>
<body>
	<jsp:include page="./../header.jsp"></jsp:include>
	<div class="container">
		<h1>댓글 수정</h1>
		<form action="modifyBoardSave" method="post">
			<div class="input-group mb-3">
				<span class="input-group-text">글 제목</span> <input type="text"
					class="form-control" name="title" id="title" value="${board.title }">
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">작성자</span> <input type="text"
					class="form-control" name="writer" value="${loginMember.userId }" readonly>
			</div>

			<!-- 내용 -->

			<div>
				<textarea id="summernote" name="content">${board.content }</textarea>
			</div>


			<button class="btn btn-primary" type="submit"
				onclick="return validBoard();">저장</button>
			<button class="btn btn-danger" type="reset" onclick="location.href='/cboard/viewBoard?boardNo=${board.boardNo}';">취소</button>

		</form>
	</div>
	<jsp:include page="./../footer.jsp"></jsp:include>
</body>
</html>