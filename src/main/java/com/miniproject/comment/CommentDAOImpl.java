package com.miniproject.comment;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.model.CommentDTO;
import com.miniproject.model.CommentVO;

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

}
