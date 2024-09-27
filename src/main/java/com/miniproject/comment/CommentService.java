package com.miniproject.comment;

import java.util.List;

import com.miniproject.model.CommentDTO;
import com.miniproject.model.CommentVO;

public interface CommentService {
	// 해당 게시글에 대한 전체 댓글 조회
	List<CommentVO> getAllComments(int boardNo) throws Exception;

	// 해당 게시글에 대한 댓글 저장
	boolean saveComment(CommentDTO newComment)throws Exception;
}
