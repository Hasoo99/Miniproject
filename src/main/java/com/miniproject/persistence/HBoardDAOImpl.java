package com.miniproject.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.model.BoardDetailInfo;
import com.miniproject.model.BoardUpFilesVODTO;
import com.miniproject.model.HBoardDTO;
import com.miniproject.model.HBoardVO;
import com.miniproject.model.HboardReplyDTO;
import com.miniproject.model.PagingInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository // 아래의 클래스가 DAO객체임을 명시
public class HBoardDAOImpl implements HBoardDAO {

	//@Inject
	//private HBoardDAO bDao;
	
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.miniproject.mappers.hboardMapper.";
	
	@Override
	public List<HBoardVO> selectAllBoard() throws Exception{
		
		log.info("Here is HBoardDAOImpl....");
		
		return ses.selectList(ns + "getAllHBoard"); // select 쿼리태그의 id를 잘못 준 경우
	}

	@Override
	public int insertNewBoard(HBoardDTO newBoard) throws Exception {
		
		return ses.insert(ns + "saveNewBoard", newBoard);
		
	}

	@Override
	public int selectMaxBoardNo() throws Exception {
		return ses.selectOne(ns + "getMaxNo");
		
	}

	@Override
	public int insertBoardUpFile(BoardUpFilesVODTO file) throws Exception {
		return ses.insert(ns + "saveUpFile", file);
	}

	@Override
	public HBoardVO selectBoardByBoardNo(int boardNo) throws Exception {
		System.out.println();
		return ses.selectOne(ns + "selectBoardByBoardNo", boardNo);
	}

	@Override
	public List<BoardUpFilesVODTO> selecyBoardUpfileByBoardNo(int boardNo) throws Exception {
		System.out.println();
		return ses.selectList(ns + "selecyBoardUpfileByBoardNo", boardNo);
	}

	@Override
	public HBoardDTO testResultMap(int boardNo) throws Exception {
		return ses.selectOne(ns + "selectResultmapTest", boardNo);
	}

	@Override
	public List<BoardDetailInfo> selectBoardDetailByBoardNo(int boardNo) throws Exception {
		return ses.selectList(ns + "selectBoardDetailInfoByBoardNo", boardNo);
	}

	@Override
	public int selectDateDiff(String ipAddr, int boardNo) throws Exception{
		// 넘겨줘야할 파라메터가 2개 이상이면, Map을 이용하여 파라메터를 매핑하여 넘겨준다
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("readWho", ipAddr);
		map.put("boardNo", boardNo);
		return ses.selectOne(ns + "selectBoardDateDiff", map);
	}

	@Override
	public int insertBoardReadLog(String ipAddr, int boardNo) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("readWho", ipAddr);
		map.put("boardNo", boardNo);
		return ses.insert(ns + "saveBoardReadLog", map);
	}

	@Override
	public void updateReadwhen(String ipAddr, int boardNo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("readWho", ipAddr);
		map.put("boardNo", boardNo);
		ses.update(ns + "updateBoardReadLog", map);
	}

	@Override
	public int updateReadCount(int boardNo) {
		return ses.update(ns + "updateReadCount", boardNo);
	}

	@Override
	public void updateBoardRef(int newBoardNo) throws Exception {
		ses.update(ns + "updateBoardRef", newBoardNo);
		
	}

	@Override
	public void updateRefOrder(int ref, int refOrder) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ref", ref);
		map.put("refOrder", refOrder);
		ses.update(ns + "updateBoardRefOrder", map);
		
	}

	@Override
	public int insertReplyBoard(HboardReplyDTO replyBoard) throws Exception {
		return ses.insert(ns + "insertReplyBoard", replyBoard);
	}

	@Override
	public int updateIsDelete(int boardNo) throws Exception {
		// isDelete컬럼 update
		return ses.update(ns + "updateIsDelete", boardNo);
	}

	@Override
	public int deleteBoardupfiles(int boardNo) throws Exception {
		// Boardupfiles테이블에서 삭제한 게시글의 첨부파일 삭제
		return ses.delete(ns + "deleteBoardupfiles", boardNo);
	}

	@Override
	public int updateBoardByBoardNo(HBoardDTO modifyBoard) throws Exception {
		return ses.update(ns + "updateBoardByBoardNo", modifyBoard);
	}

	@Override
	public void deleteBoardUpFile(int boardUpFileNo) throws Exception {
		ses.delete(ns + "deleteBoardUpFileByPK", boardUpFileNo);
	}

	@Override
	public int getTotalPostCnt() throws Exception {
		return ses.selectOne(ns + "selectTotalCount");
	}

	@Override
	public List<HBoardVO> selectAllBoard(PagingInfo pi) throws Exception {
		return ses.selectList(ns + "getAllHBoard", pi);
	}

}
