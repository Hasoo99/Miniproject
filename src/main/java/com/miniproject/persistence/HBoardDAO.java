package com.miniproject.persistence;

import java.util.List;

import com.miniproject.model.HBoardDTO;
import com.miniproject.model.HBoardVO;

public interface HBoardDAO {
	// 게시판 전체 목록 가져오는 메서드
	List<HBoardVO> selectAllBoard() throws Exception;

	// 게시글 저장
	int insertNewBoard(HBoardDTO newBoard) throws Exception;
}
