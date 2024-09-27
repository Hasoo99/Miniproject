package com.miniproject.comment;

import java.util.List;

import com.miniproject.model.CommentDTO;
import com.miniproject.model.CommentVO;

public interface CommentDAO {
	List<CommentVO> getAllComments(int boardNo) throws Exception;
	
	// 댓글 저장
	int insertNewComment(CommentDTO newComment) throws Exception;

	
}
