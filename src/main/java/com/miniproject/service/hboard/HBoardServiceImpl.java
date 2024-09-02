package com.miniproject.service.hboard;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

import com.miniproject.model.HBoardDTO;
import com.miniproject.model.HBoardVO;
import com.miniproject.model.PointLogDTO;
import com.miniproject.persistence.HBoardDAO;
import com.miniproject.persistence.MemberDAO;
import com.miniproject.persistence.PointLogDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service // 아래의 클래스가 서비스 객체임을 컴파일러 공지
public class HBoardServiceImpl implements HBoardService {

	@Inject
	private HBoardDAO bDao;
	
	@Inject
	private PointLogDAO pDao;
	
	@Inject
	private MemberDAO mDao;
	
	@Override
	public List<HBoardVO> getAllBoard() throws Exception{
		System.out.println("HBoardServiceImpl......");
		log.info("HBoardServiceImpl......");
		
		List<HBoardVO> lst = bDao.selectAllBoard(); // DAO 메서드 호출
		
		return lst;
	}


	@Override
	public boolean saveBoard(HBoardDTO newBoard) throws Exception {
		boolean result = false;
		// 1) newBoard를 DAO단을 통해서 insert한다.
		if(bDao.insertNewBoard(newBoard) == 1) {			
			// 2) 1)에서 insert가 성공하면, pointlog에 저장
			if(pDao.insertPointLog(new PointLogDTO(newBoard.getWriter(), "글작성")) == 1) {				
				// 3) 작성자의 userPoint값을 update한다.
				if(mDao.updateUserPoint(newBoard.getWriter()) == 1) {
					result= true;
				}
			}
		}
		return result;
	}

}
