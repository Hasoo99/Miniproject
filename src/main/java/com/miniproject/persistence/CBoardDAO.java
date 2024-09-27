package com.miniproject.persistence;

import java.util.List;

import com.miniproject.model.BoardDetailInfo;
import com.miniproject.model.BoardUpFilesVODTO;
import com.miniproject.model.HBoardDTO;
import com.miniproject.model.HBoardVO;
import com.miniproject.model.HboardReplyDTO;
import com.miniproject.model.PagingInfo;
import com.miniproject.model.SearchCriteriaDTO;


public interface CBoardDAO {
	// 게시판 전체 목록 가져오는 메서드
	List<HBoardVO> selectAllBoard() throws Exception;

	// 게시글 저장
	int insertNewBoard(HBoardDTO newBoard) throws Exception;
	
	// 최근 저장된 글의 글번호를 얻어오는 메서드
	int selectMaxBoardNo() throws Exception ;

	// 업로드된 첨부파일을 저장하는 메서드
	int insertBoardUpFile(BoardUpFilesVODTO file) throws Exception;

	// 유저가 입력한 텍스트만 hboard에서 조회
	HBoardVO selectBoardByBoardNo(int boardNo) throws Exception;

	// 업로드한 파일만 조회
	List<BoardUpFilesVODTO> selecyBoardUpfileByBoardNo(int boardNo) throws Exception;
	
	
	// resultMap 테스트
	HBoardDTO testResultMap(int boardNo) throws Exception;
	
	// 게시글 상세정보
	BoardDetailInfo selectBoardDetailByBoardNo(int boardNo) throws Exception ;

	// 조회하는 게시글을 해당아이피에서 조회했던 기록이 있는지 확인하는 메서드
	int selectDateDiff(String ipAddr, int boardNo) throws Exception;

	// 아이피주소가 boardNo번 글을 읽은 시간을 테이블에 저장 (insert)
	int insertBoardReadLog(String ipAddr, int boardNo) throws Exception;

	// 아이피 주소가 boardNo번 글을 읽은 시간을 테이블에 수정 (update)
	void updateReadwhen(String ipAddr, int boardNo) throws Exception;

	// 조회수 증가 (+1)
	int updateReadCount(int boardNo);

	// 저장된 게시글의 글 번호를 ref컬럼에 update
	void updateBoardRef(int newBoardNo) throws Exception;

	// refOrder 업데이트 (답글처리 : 자리확보)
	void updateRefOrder(int ref, int refOrder) throws Exception;

	// 답글 저장
	int insertReplyBoard(HboardReplyDTO replyBoard) throws Exception;

	// 게시글 삭제 (isDelete컬럼값을 'Y'로 업데이트 처리)
	int updateIsDelete(int boardNo) throws Exception;

	// 첨부파일 삭제 (boardupfiles에서 삭제된 게시글의 첨부파일을 삭제)
	int deleteBoardupfiles(int boardNo)throws Exception;

	// 게시글 수정
	int updateBoardByBoardNo(HBoardDTO modifyBoard) throws Exception;

	// 게시글 수정 -- 첨부파일 삭제
	void deleteBoardUpFile(int boardUpFileNo) throws Exception;

	// 전체 글(데이터)의 수
	int getTotalPostCnt()throws Exception;

	// 게시글 목록 조회 -- 페이징
	List<HBoardVO> selectAllBoard(PagingInfo pi)throws Exception;

	// 게시글 목록 조회 -- 검색어
	List<HBoardVO> selectAllBoard(SearchCriteriaDTO searchCriteriaDTO)throws Exception;

	// 검색된 게시글 수
	int getTotalPostCnt(SearchCriteriaDTO searchCriteriaDTO)throws Exception;

	// 게시글 목록 조회 -- 검색어 + 페이징
	List<HBoardVO> selectAllBoard(PagingInfo pi, SearchCriteriaDTO searchCriteriaDTO)throws Exception;

	// 좋아요
	int likeBoard(int boardNo, String who)throws Exception;

	// 좋아요 수 업데이트
	int updateBoardLikeCount(int i, int boardNo)throws Exception;

	// 좋아요 취소하기
	int disLikeBoard(int boardNo, String who)throws Exception;
	
}
