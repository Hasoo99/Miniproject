package com.miniproject.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.model.CommentDTO;
import com.miniproject.model.CommentVO;
import com.miniproject.model.PagingInfo;
import com.miniproject.model.PagingInfoDTO;
import com.miniproject.model.PointLogDTO;
import com.miniproject.persistence.MemberDAO;
import com.miniproject.persistence.PointLogDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

	private final CommentDAO cDao;
	private final PointLogDAO pDao;
	private final MemberDAO mDao;

	@Override
	public List<CommentVO> getAllComments(int boardNo) throws Exception {
		return cDao.getAllComments(boardNo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public boolean saveComment(CommentDTO newComment) throws Exception {
		boolean result = false;
		// 1) 댓글 저장 - insert
		if (cDao.insertNewComment(newComment) == 1) {
			// 2) 포인트 부여 insert
			if (pDao.insertPointLog(new PointLogDTO(newComment.getCommenter(), "댓글작성")) == 1) {
				// 3) 멤버의 포인트 update
				if (mDao.updateUserPointComment(newComment.getCommenter()) == 1) {
					// memberMapper에서 updateUserPointComment 쿼리문 실행
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> getAllComments(int boardNo, PagingInfoDTO pagingInfoDTO) throws Exception {
		PagingInfo pi = makePagingInfo(pagingInfoDTO, boardNo);
		List<CommentVO> list = cDao.getAllComments(boardNo, pi); // 조회한 게시글의 댓글리스트를 가져옴

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pagingInfo", pi);
		result.put("commentList", list);
		
		return result;
	}

	private PagingInfo makePagingInfo(PagingInfoDTO pagingInfoDTO, int boardNo) throws Exception {
		PagingInfo pi = new PagingInfo(pagingInfoDTO);

		// Setter 호출
		pi.setTotalPostCnt(cDao.getTotalPostCnt(boardNo)); // 전체 글(데이터)의 수

		pi.setTotalPageCnt(); // 전체 페이지 수 세팅
		pi.setStartRowIndex(); // 현재 페이지에서 보여주기 시작할 글의 index번호

		// 페이징 블럭
		pi.setPageBlockNoCurPage();
		pi.setStartPageNoCurBlock();
		pi.setEndPageNoCurBlock();

		log.info(pi.toString());

		return pi;
	}

}
