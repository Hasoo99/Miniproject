package com.miniproject.comment;

import java.util.List;

import com.miniproject.model.CommentDTO;
import com.miniproject.model.CommentVO;
import com.miniproject.model.PagingInfo;

public interface CommentDAO {
	List<CommentVO> getAllComments(int boardNo) throws Exception;
	
	// 댓글 저장
	int insertNewComment(CommentDTO newComment) throws Exception;

	// 조회한 게시글의 총 댓글 갯수를 int로 반환
	int getTotalPostCnt(int boardNo)throws Exception;

	// 조회한 게시글의 댓글과 댓글작성자의 userImg를 CommentVO로 반환
	List<CommentVO> getAllComments(int boardNo, PagingInfo pi)throws Exception;

	
}
