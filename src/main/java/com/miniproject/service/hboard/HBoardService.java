package com.miniproject.service.hboard;

import java.util.List;

import com.miniproject.model.HBoardDTO;
import com.miniproject.model.HBoardVO;

public interface HBoardService {
	
	
	// 게시판 전체 리스트 조회
	List<HBoardVO> getAllBoard() throws Exception;

	// 게시글 저장
	boolean saveBoard(HBoardDTO boardDTO)  throws Exception;
}
