<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글형 게시판 목록</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<style>
.pagination {
	display: flex;
	justify-content: center;
}

.boardControl {
	width: 150px;
}

.noContent {
	display: flex;
	justify-content: center;
}
</style>
	<script>
	$(function() {
		let now = new Date();
//		alert('${status}');
//		alert('${msg}');
		console.log('${boardList }');
		timediffPostDate();
		
		
		// pagingSize
		/* let pagingSize = '${param.pagingSize}';
		if(pagingSize = '') {
			pagingSize = 10;
		} else {
			pagingSize = parseInt(pagingSize);
		}
		// 유저가 페이징사이즈를 선택하면
		 $(".pagingSize").change(function() {
			console.log($(this).val());
			
			let pageNo = '${param.pageNo}';
			
			
			if(pageNo == '') {
				pageNo = 1;
			} else {
				pageNo = parseInt(pageNo);				
			}
			location.href='/hboard/listAll?pagingSize=' + $(this).val() + "&pageNo=" + pageNo;
		});  */
		
		
		
//		replyIcon()
		showModal();
		$(".modalCloseBtn").click(function() {
			$("#myModal").hide(); // 모달창 닫기
		});
	});

	function showModal() {
		let status = '${param.status}'; // url주소창에서 status쿼리스트링의 값을 가져와 변수 저장
		console.log(status);
		
		if (status == 'success') {
			// 글 저장성공 모달창
			$(".modal-body").html('<h5>글 저장에 성공했습니다.</h5>');
			$("#myModal").show();
		} else if (status == 'fail') {
			// 글 저장실패 모달창
			$(".modal-body").html('<h5>글 저장에 실패하였습니다.</h5>');
			$("#myModal").show();
		} else if (status == "removesuccess") {
			$(".modal-body").html('<h5>글 삭제에 성공하였습니다.</h5>');
			$("#myModal").show();
		} else if (status == "removefail") {
			$(".modal-body").html('<h5>글 삭제에 실패했습니다.</h5>');
			$("#myModal").show();
		}
		
		
		
		// 게시글을 불러올 때 예외가 발생한 경우
		let except = '${exception}';
		console.log(except);
		/* console.log("{exceptiozn} : ",${exception}); */
		if (except == 'error') {
			$(".modal-body").html('<h2>문제가 발생해 데이터를 불러오지 못했습니다.</h2>');
			$("#myModal").show();
		}
	}
	

	// 게시글의 글작성일을 얻어와서 2시간 이내에 작성한 글이면 new.png 이미지를 제목 옆에 출력한다.
	function timediffPostDate() {
		$(".postDate").each(function(i, e) {
			//console.log(i + "번째 : " + $(e).html());
			
			let postDate = new Date($(e).html()); // 글 작성일 저장 (Date객체로 변환)
			let curDate = new Date(); // 현재 날짜 시간 객체 생성
			//console.log(curDate - postDate); // 밀리초
			
			let diff = (curDate - postDate) / 1000 / 60 / 60; // 시간단위
			//console.log(diff);
			
			let title = $(e).prev().prev().html();
			//console.log(title);
			
			if(diff < 2) { // 2시간 이내에 작성한 글이라면
				let output = "<span><img src='/resources/images/new.png' width='26px;'/></span>";
				$(e).prev().prev().html(title + output);
			}
			
			
		});
	}
	
	function replyIcon() {
		$(".step").each(function(i, e) {
			//console.log(i + "번쨰 " + $(e).val());
			//console.log(parseInt($(e).val()));
			let output = "<span>";
			for(let r = 0; r < parseInt($(e).val()); r++) {
				console.log("답글아이콘 넣기 반복문 동작");
				output += "<img src='/resources/images/replyIcon.png' width='20px;'/>";
			}
			output += "</span>";
			$(e).prev().prev().prev().prev().prepend(output);
		});
	}
	
	function pagingSizeChange(obj) {
		//console.log(obj.value);
		let pagingSize = obj.value;
		location.href="/cboard/listAll?pageNo=" + "1" + "&pagingSize=" + pagingSize + "&searchWord=" + "${param.searchWord}" + "&searchType=" + "${param.searchType}"
	}
	
	
</script>
	<jsp:include page="./../header.jsp"></jsp:include>
	<div class="container">
		<h1>댓글형 게시판 전체 목록</h1>

		<div class="boardControl">
			<select class="form-select pagingSize"
				onchange="pagingSizeChange(this);">
				<option value="${pagingInfo.viewPostCntperPage }">선택</option>
				<option value="10">10개씩 보기</option>
				<option value="20">20개씩 보기</option>
				<option value="40">40개씩 보기</option>
				<option value="80">80개씩 보기</option>
			</select> <input type="text" value="${pagingInfo.viewPostCntperPage }개씩 보기"
				disabled>
		</div>

		<table class="table table-hover">
			<thead>
				<c:if test="${pagingInfo.totalPostCnt != 0 }">
					<tr>
						<th>#</th>
						<th>글제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</c:if>
				<c:if test="${pagingInfo.totalPostCnt == 0 }">
					<div class="noContent">
						<img src="/resources/images/noContent.png">
					</div>
				</c:if>
			</thead>
			<tbody>
				<c:forEach var="board" items="${boardList }">
					<c:choose>
						<c:when test="${board.isDelete == 'N' }">
							<tr
								onclick="location.href='/cboard/viewBoard?boardNo=${board.boardNo}'">
								<td>${board.boardNo }</td>
								<td><c:if test='${board.step > 0 }'>
										<c:forEach var="i" begin="1" end="${board.step }"
											varStatus="status">
											<c:if test="${status.last }">
												<img src='/resources/images/replyIcon.png' width="20px"
													style="margin-left: calc(20px * ${i-1})" />
											</c:if>
										</c:forEach>
									</c:if> ${board.title }</td>
								<td>${board.writer }</td>
								<td class="postDate">${board.postDate }</td>
								<td>${board.readCount }</td>
								<input type="hidden" class="step" value="${board.step }">
							</tr>
						</c:when>
						<c:when test="${board.isDelete == 'Y' }">
							<tr>
								<td>${board.boardNo }</td>
								<td colspan="4" style="color: gray;">삭제된 글입니다.</td>
							</tr>
						</c:when>
					</c:choose>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<button type="button" class="btn btn-success"
				onclick="location.href='/cboard/showSaveBoardForm';">글쓰기</button>
		</div>

		<!-- 검색 -->

		<form action="/cboard/listAll" method="post">
			<select class="form-select" id="searchType" name="searchType">
				<option value="">--검색 타입--</option>
				<option value="title">제목</option>
				<option value="writer">작성자</option>
				<option value="content">내용</option>
			</select>
			<div class="input-group mb-3">
				<input type="text" class="form-control" placeholder="검색어를 입력하세요...."
					id="searchWord" name="searchWord">
				<button type="submit" class="btn btn-success">검색</button>
			</div>
		</form>
		<div>${boardList }</div>
		<!-- 페이지네이션 -->

		<div>${pagingInfo }</div>
		<div>${search }</div>
		<div class="paging">
			<ul class="pagination">
				<c:if test="${pagingInfo.pageNo == 1 }">
					<li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
				</c:if>
				<c:if test="${pagingInfo.pageNo > 1 }">
					<li class="page-item"><a class="page-link"
						href="/cboard/listAll?pageNo=${pagingInfo.pageNo - 1 }&pagingSize=${pagingInfo.viewPostCntperPage }&searchWord=${param.searchWord }&searchType=${param.searchType }">Previous</a></li>
				</c:if>

				<c:forEach var="i" begin='${pagingInfo.startPageNoCurBlock }'
					end='${pagingInfo.endPageNoCurBlock }'>
					<c:if test="${pagingInfo.pageNo == i}">
						<li class="page-item active"><a class="page-link"
							href="/cboard/listAll?pageNo=${i }&pagingSize=${pagingInfo.viewPostCntperPage }&searchWord=${param.searchWord }&searchType=${param.searchType }">${i }</a></li>
					</c:if>
					<c:if test="${pagingInfo.pageNo != i}">
						<li class="page-item"><a class="page-link"
							href="/cboard/listAll?pageNo=${i }&pagingSize=${pagingInfo.viewPostCntperPage }&searchWord=${param.searchWord }&searchType=${param.searchType }">${i }</a></li>
					</c:if>
				</c:forEach>

				<c:if test="${pagingInfo.pageNo < pagingInfo.totalPageCnt }">
					<li class="page-item"><a class="page-link"
						href="/cboard/listAll?pageNo=${pagingInfo.pageNo + 1 }&pagingSize=${pagingInfo.viewPostCntperPage }&searchWord=${param.searchWord }&searchType=${param.searchType }">Next</a></li>
				</c:if>
				<c:if test="${pagingInfo.pageNo == pagingInfo.totalPageCnt }">
					<li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
				</c:if>
			</ul>
		</div>

	</div>

	<!-- The Modal -->
	<div class="modal" id="myModal" style="display: none;">
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
					<button type="button" class="btn btn-danger modalCloseBtn"
						data-bs-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div>
	<!-- Base64로 이미지 출력하기 -->
	<div>
		<img
			src="data:image/jpeg;base64,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">
	</div>
	<jsp:include page="./../footer.jsp"></jsp:include>

</body>
</html>