package com.miniproject.service.hboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.model.BoardDetailInfo;
import com.miniproject.model.BoardUpFileStatus;
import com.miniproject.model.BoardUpFilesVODTO;
import com.miniproject.model.HBoardDTO;
import com.miniproject.model.HBoardVO;
import com.miniproject.model.HboardReplyDTO;
import com.miniproject.model.PagingInfo;
import com.miniproject.model.PagingInfoDTO;
import com.miniproject.model.PointLogDTO;
import com.miniproject.model.SearchCriteriaDTO;
import com.miniproject.persistence.CBoardDAO;
import com.miniproject.persistence.HBoardDAO;
import com.miniproject.persistence.MemberDAO;
import com.miniproject.persistence.PointLogDAO;
import com.mysql.cj.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CBoardServiceImpl implements CBoardService {

	private final CBoardDAO cDao;
	private final PointLogDAO pDao;
	private final MemberDAO mDao;
	private final HBoardDAO hDao;

	@Override
	public List<HBoardVO> getAllBoard() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public boolean saveBoard(HBoardDTO newBoardDTO) throws Exception {
		boolean result = false;

		// 1) newBoard를 DAO단을 통해서 insert(유저가 입력한 텍스트)한다.
		if (cDao.insertNewBoard(newBoardDTO) == 1) { // 글 저장

			// 2) 1)에서 insert가 성공하면, pointlog에 저장
			if (pDao.insertPointLog(new PointLogDTO(newBoardDTO.getWriter(), "글작성")) == 1) {
				// 3) 작성자의 userPoint값을 update한다.
				if (mDao.updateUserPoint(newBoardDTO.getWriter()) == 1) {
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public HashMap<String, Object> viewBoardByBoardNo(int boardNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HBoardDTO testResultMap(int boardNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public BoardDetailInfo read(int boardNo, String ipAddr) throws Exception {
		BoardDetailInfo boardInfo = cDao.selectBoardDetailByBoardNo(boardNo);

		// 조회수 증가
		// dateDiff = 날짜차이를 계산한 결과
		int dateDiff = hDao.selectDateDiff(ipAddr, boardNo);
		System.out.println("접근 ip : " + ipAddr);
		System.out.println("조회한 게시글 번호 : " + boardNo);
		System.out.println("날짜차이 : " + dateDiff);

		if (dateDiff == -1) { // 해당 아이피 주소가 boardNo번 글을 최초로 조회
//					아이피주소가 boardNo번 글을 읽은 시간을 테이블에 저장 (insert)
			if (hDao.insertBoardReadLog(ipAddr, boardNo) == 1) {
//						조회수 증가 (+1)
				updateReadCount(boardNo, boardInfo);
			}
		} else if (dateDiff >= 1) { // 해당 아이피 주소가 boardNo번 글을 다시 조회
//					ipAddr가 boardNo번 글을 읽은 시간을 테이블에 수정 (update)
			hDao.updateReadwhen(ipAddr, boardNo);
//					조회수 증가 (+1)
			updateReadCount(boardNo, boardInfo);
		}

		return boardInfo;
	}

	private void updateReadCount(int boardNo, BoardDetailInfo boardInfo) {
		if (hDao.updateReadCount(boardNo) == 1) {
			boardInfo.setReadCount(boardInfo.getReadCount() + 1);
		}
	}

	@Override
	public boolean saveReply(HboardReplyDTO replyBoard) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BoardUpFilesVODTO> removeBoard(int boardNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardDetailInfo readBoard(int boardNo) throws Exception {
		BoardDetailInfo boardInfo = cDao.selectBoardDetailByBoardNo(boardNo);
		return boardInfo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean modifyBoard(HBoardDTO modifyBoard) throws Exception {
		boolean result = false;

		System.out.println(modifyBoard.toString());
		System.out.println("씨다오 업데이트보드바이보드노 : "+cDao.updateBoardByBoardNo(modifyBoard));

		// 1) 순수게시글 update
		if (cDao.updateBoardByBoardNo(modifyBoard) == 1) {
			
			result = true;
		}

		return result;
	}

	@Override
	public Map<String, Object> getAllBoard(PagingInfoDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAllBoard(PagingInfoDTO dto, SearchCriteriaDTO searchCriteriaDTO) throws Exception {
		PagingInfo pi = makePagingInfo(dto, searchCriteriaDTO);
		List<HBoardVO> lst = null;

		if (StringUtils.isNullOrEmpty(searchCriteriaDTO.getSearchType())
				&& StringUtils.isNullOrEmpty(searchCriteriaDTO.getSearchWord())) {
			lst = cDao.selectAllBoard(pi);
		} else {
			lst = cDao.selectAllBoard(pi, searchCriteriaDTO);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pagingInfo", pi);
		resultMap.put("boardList", lst);

		return resultMap;
	}

	private PagingInfo makePagingInfo(PagingInfoDTO dto, SearchCriteriaDTO searchCriteriaDTO) throws Exception {
		PagingInfo pi = new PagingInfo(dto);

		// Setter 호출
		log.info("검색된 글의 갯수 : " + cDao.getTotalPostCnt(searchCriteriaDTO));
		pi.setTotalPostCnt(cDao.getTotalPostCnt(searchCriteriaDTO)); // 전체 글(데이터)의 수

		pi.setTotalPageCnt(); // 전체 페이지 수 세팅
		pi.setStartRowIndex(); // 현재 페이지에서 보여주기 시작할 글의 index번호

		// 페이징 블럭
		pi.setPageBlockNoCurPage();
		pi.setStartPageNoCurBlock();
		pi.setEndPageNoCurBlock();

		log.info(pi.toString());

		return pi;
	}
}
