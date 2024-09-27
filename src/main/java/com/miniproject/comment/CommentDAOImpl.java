package com.miniproject.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.model.CommentDTO;
import com.miniproject.model.CommentVO;
import com.miniproject.model.PagingInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CommentDAOImpl implements CommentDAO{

	private static final String NS = "com.miniproject.mappers.commentMapper.";
	private final SqlSession ses;
	
	@Override
	public List<CommentVO> getAllComments(int boardNo) throws Exception {
		System.out.println("댓글조회 select문 실행 : "+ NS + "getComments, boardNo=" + boardNo);
		return ses.selectList(NS + "getComments", boardNo);
	}

	@Override
	public int insertNewComment(CommentDTO newComment) throws Exception {
		return ses.insert(NS+"saveComment", newComment);
	}
	
	// 조회한 게시글의 총 댓글 갯수를 int로 반환
	@Override
	public int getTotalPostCnt(int boardNo) throws Exception {
		return ses.selectOne(NS + "getCommentCount", boardNo);
	}
	
	// 조회한 게시글의 댓글과 댓글작성자의 userImg를 CommentVO로 반환
	@Override
	public List<CommentVO> getAllComments(int boardNo, PagingInfo pi) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("boardNo", boardNo);
		param.put("startRowIndex", pi.getStartRowIndex());
		param.put("viewPostCntPerPage", pi.getViewPostCntperPage());
		return ses.selectList(NS + "getComments", param);
	}

}
