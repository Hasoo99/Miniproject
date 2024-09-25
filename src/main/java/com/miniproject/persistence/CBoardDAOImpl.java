package com.miniproject.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.model.BoardDetailInfo;
import com.miniproject.model.BoardUpFilesVODTO;
import com.miniproject.model.HBoardDTO;
import com.miniproject.model.HBoardVO;
import com.miniproject.model.HboardReplyDTO;
import com.miniproject.model.PagingInfo;
import com.miniproject.model.SearchCriteriaDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Repository
public class CBoardDAOImpl implements CBoardDAO{

	private static final String NS = "com.miniproject.mappers.cboardMapper.";
	private final SqlSession ses;
	
	@Override
	public List<HBoardVO> selectAllBoard() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertNewBoard(HBoardDTO newBoard) throws Exception {
		return ses.insert(NS + "saveNewBoard", newBoard);
	}

	@Override
	public int selectMaxBoardNo() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertBoardUpFile(BoardUpFilesVODTO file) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HBoardVO selectBoardByBoardNo(int boardNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoardUpFilesVODTO> selecyBoardUpfileByBoardNo(int boardNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HBoardDTO testResultMap(int boardNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardDetailInfo selectBoardDetailByBoardNo(int boardNo) throws Exception {
		return ses.selectOne(NS + "selectBoardDetailInfoByBoardNo", boardNo);
	}

	@Override
	public int selectDateDiff(String ipAddr, int boardNo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertBoardReadLog(String ipAddr, int boardNo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateReadwhen(String ipAddr, int boardNo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int updateReadCount(int boardNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateBoardRef(int newBoardNo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRefOrder(int ref, int refOrder) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int insertReplyBoard(HboardReplyDTO replyBoard) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateIsDelete(int boardNo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBoardupfiles(int boardNo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBoardByBoardNo(HBoardDTO modifyBoard) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteBoardUpFile(int boardUpFileNo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotalPostCnt() throws Exception {
		return ses.selectOne(NS + "selectTotalCount");
	}

	@Override
	public List<HBoardVO> selectAllBoard(PagingInfo pi) throws Exception {
		List<HBoardVO> lst = ses.selectList(NS + "getAllHBoard", pi);
		return lst;
	}

	@Override
	public List<HBoardVO> selectAllBoard(SearchCriteriaDTO searchCriteriaDTO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalPostCnt(SearchCriteriaDTO searchCriteriaDTO) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchType", searchCriteriaDTO.getSearchType());
		params.put("searchWord", "%" + searchCriteriaDTO.getSearchWord() + "%");
		return ses.selectOne(NS + "selectTotalCountWithSearchCriteria",params);
	}

	@Override
	public List<HBoardVO> selectAllBoard(PagingInfo pi, SearchCriteriaDTO searchCriteriaDTO) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("viewPostCntperPage", pi.getViewPostCntperPage());
		params.put("searchType", searchCriteriaDTO.getSearchType());
		params.put("searchWord", "%" + searchCriteriaDTO.getSearchWord() + "%");
		return ses.selectList(NS + "getSearchBoard", params);
	}

}
