package com.miniproject.comment;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.model.CommentDTO;
import com.miniproject.model.CommentVO;
import com.miniproject.model.PointLogDTO;
import com.miniproject.persistence.MemberDAO;
import com.miniproject.persistence.PointLogDAO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

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
		if(cDao.insertNewComment(newComment) == 1) {			
			// 2) 포인트 부여 insert
			if(pDao.insertPointLog(new PointLogDTO(newComment.getCommenter(), "댓글작성"))==1) {
				// 3) 멤버의 포인트 update
				if(mDao.updateUserPointComment(newComment.getCommenter()) == 1) {
					// memberMapper에서 updateUserPointComment 쿼리문 실행
					result = true;
				}
			}
		}
		return result;
	}

}
