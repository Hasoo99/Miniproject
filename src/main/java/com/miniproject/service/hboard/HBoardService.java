package com.miniproject.service.hboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.miniproject.model.BoardDetailInfo;
import com.miniproject.model.BoardUpFilesVODTO;
import com.miniproject.model.HBoardDTO;
import com.miniproject.model.HBoardVO;
import com.miniproject.model.HboardReplyDTO;
import com.miniproject.model.PagingInfoDTO;

public interface HBoardService {
	
	
	// 게시판 전체 리스트 조회
	List<HBoardVO> getAllBoard() throws Exception;

	// 게시글 저장
	boolean saveBoard(HBoardDTO boardDTO)  throws Exception;

	//
	HashMap<String, Object> viewBoardByBoardNo(int boardNo) throws Exception;
	
	// resultMap 테스트
	HBoardDTO testResultMap(int boardNo) throws Exception;
	
	// 게시글 상세조회
	List<BoardDetailInfo> read(int boardNo, String ipAddr) throws Exception ;

	// 답글 저장
	boolean saveReply(HboardReplyDTO replyBoard) throws Exception;

	// 게시글 삭제처리(isDelete컬럼값을 'Y'로 변경)
	List<BoardUpFilesVODTO> removeBoard(int boardNo) throws Exception;

	// boardNo로 게시글정보 조회(조회수 작업 안함)
	// 게시글 조회(수정)
	List<BoardDetailInfo> readBoard(int boardNo) throws Exception;

	// 게시글 수정
	boolean modifyBoard(HBoardDTO modifyBoard) throws Exception;

	// 게시글 조회 -- 페이징
	Map<String, Object> getAllBoard(PagingInfoDTO dto) throws Exception;
}
