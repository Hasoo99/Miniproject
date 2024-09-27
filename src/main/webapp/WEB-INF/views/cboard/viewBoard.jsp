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
		
		getAllComments();
		console.log("${param.boardNo}");
		
		
		$(".modalCloseBtn").click(function() {
			$("#myModal").hide();
		});

	});

	function getAllComments() {
		$.ajax({
	          url: "/comment/all/${param.boardNo}", // 데이터가 송수신될 서버의 주소
	          type: "GET", // 통신 방식 (GET, POST, PUT, DELETE)
	          dataType: "json", // 수신 받을 데이터 타입 (MIME TYPE)
	          success: function (data) {
	            // 통신이 성공하면 수행할 함수
	            console.log(data);
	            displayAllComments(data);
	          },
	          error: function () {},
	          complete: function () {},
	        });
	}
	
	function displayAllComments(json) {
		let output = '<ul class="list-group">';
		if (json.length > 0) {
			$.each(json, function(i, elt) {
				output += `<li class="list-group-item">`;
				output += `<div>\${elt.content}</div>`;
				output += `<div><span>\${elt.commenter}</span></div>`;	
				let elapsedTime = processPostDate(elt.regDate);
				output += `<span>\${elapsedTime}</span>`;
				output += `</li>`;
			});
		}
		output += '</ul>';
		$(".commentList").html(output);
	}
	
	function processPostDate(postDate) {
		// 댓글 작성일시 방금전, 몇분전, 몇시간전 .... 의 형식으로 출력
		/* let postDate = parseInt(regDate);
		let now = new Date();
		let milliSecond = parseInt(now.getTime());
		console.log(now);
		console.log("글 등록시간 : ", postDate/1000/60);
		console.log("현재 시간 : ", milliSecond/1000/60); */
		
		//console.log(postDate);
		
		let regDate = new Date(postDate); //댓글 등록 시간(밀리초)
		let now = new Date(); // 현재 시간(밀리초)
		
		let diff = (now - regDate) / 1000; //초(second)단위
		
		//diff = 9;
		//diff = 60 * 60 * 4 + 2;
		
		let times = [
			{name : "일", time : 24 * 60 * 60},
			{name : "시간", time : 60 * 60},
			{name : "분", time : 60}
		];
		
		for (let val of times) {
			let elapsedTime = Math.floor(diff / val.time);
			//console.log(diff, elapsedTime);
			if(elapsedTime > 0 && val.name != "일"){ // 하루보다 크지 않다.
				return elapsedTime + val.name + "전";
			} else if(elapsedTime > 0 && val.name == "일") { // 하루보다 크다
				return regDate.toLocaleString();
			}
		}
		return "방금 전";
	}
	
	function saveComment() {
		// 댓글 저장
		let boardNo = $("#boardNo").val();
		let content = $("#commentContent").val();
		//let commenter = '${loginMember.userId}'; // EL session에서 읽어옴
		let commenter = preAuth();
		
		
		//console.log(boardNo, content, commenter);
		
		let newComment = {
				'boardNo' : boardNo,
				'content' : content,
				'commenter' : commenter
		}
		
		if(content.length < 1) { // 댓글 내용이 없을경우
			alert("댓글 내용을 입력하세요.");
			return;
		} else if(content.length >= 1 && commenter != null) { // 댓글작성을 했고, 로그인되어있을때
			$.ajax({
		         url: "/comment/" + boardNo, // 데이터가 송수신될 서버의 주소
		         type: "POST", // 통신 방식 (GET, POST, PUT, DELETE)
				 data: JSON.stringify(newComment), // 보내는 데이터
				 headers : {
					 "Content-Type" : "application/json"
				 }, // 송신할 데이터가 json임을 백엔드에 알려줌
		         dataType: "json", // 수신 받을 데이터 타입 (MIME TYPE)
		         success: function (data) {
		          // 통신이 성공하면 수행할 함수
		         console.log(data);
		          getAllComments();
		          },
		          error: function () {},
		          complete: function () {},
		        });
		}
	}
	
	function preAuth() {
		let commenter = '${loginMember.userId}'; // EL session에서 읽어옴
		if(commenter == '') { // 로그인을 안했다 --> 로그인 페이지로 이동
			location.href='/member/login?redirectUrl=viewBoard&boardNo=${param.boardNo}';
			//쿼리스트링에 원래있던페이지 정보를 담아서 로그인페이지로 보냄.
		} else {
			return '${loginMember.userId}';
		}
	}
	
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

		<div class="commentInputArea">
			<input type="text" class="form-control" id="commentContent"
				placeholder="댓글 입력"> <img
				src="/resources/images/comment.png" onclick="saveComment();">
		</div>

		<div class="commentList"></div>


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