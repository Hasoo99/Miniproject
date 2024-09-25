package com.miniproject.controller.hboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miniproject.controller.HomeController;
import com.miniproject.model.BoardDetailInfo;
import com.miniproject.model.BoardUpFileStatus;
import com.miniproject.model.BoardUpFilesVODTO;
import com.miniproject.model.HBoardDTO;
import com.miniproject.model.HBoardVO;
import com.miniproject.model.MyResponseWithoutData;
import com.miniproject.model.PagingInfo;
import com.miniproject.model.PagingInfoDTO;
import com.miniproject.model.SearchCriteriaDTO;
import com.miniproject.model.HboardReplyDTO;
import com.miniproject.service.hboard.CBoardService;
import com.miniproject.service.hboard.HBoardService;
import com.miniproject.util.FileProcess;
import com.miniproject.util.GetClientIPAddr;

// Controller단에서 해야 할 일
// 1) URI 매핑 (어떤 URI가 어떤방식 (GET/POST)으로 호출되었을 때 어떤 메서드에 매핑 시킬 것이냐)
// 2) 있다면 view단에서 보내준 매개 변수 수집
// 3) 데이터베이스에 대한 CRUD 를 수행하기 위해 service단의 해당 메서드를 호출. 
// 		service단에서 return 값을 view으로 바인딩 + view단 호출
// 4) 부가적으로 컨트롤러단은 Servlet에 의해 동작되는 모듈이기 때문에, HttpServletRequest, HttpServletResponse,
//		HttpSession 등의 Servlet 객체를 이용할 수 있다.
//		그 기능은 컨트롤러단에서 구현한다.

@Controller // 아래의 클래스가 컨트롤러 객체임을 명시
@RequestMapping("/cboard") // 해당 이름으로 들어오는 요청을 수행
public class CboardController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Inject // service 객체 주입
	private CBoardService service;

	@RequestMapping("/listAll") // "/hboard/listAll"
	public void listAll(Model model, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "pagingSize", defaultValue = "10") int pagingSize,
			SearchCriteriaDTO searchCriteriaDTO) {
		logger.info(pageNo + "번 페이지 출력...");

		logger.info(searchCriteriaDTO.toString());

		PagingInfoDTO dto = PagingInfoDTO.builder().pageNo(pageNo).pagingSize(pagingSize).build();

		List<HBoardVO> lst = null;
		try {
			Map<String, Object> resultMap = service.getAllBoard(dto, searchCriteriaDTO);
			lst = (List<HBoardVO>) resultMap.get("boardList");
			PagingInfo pi = (PagingInfo) resultMap.get("pagingInfo");
//			lst = (List<HBoardVO>) result.get("boardList");
//			PagingInfo pi = (PagingInfo) result.get("pagingInfo");
			model.addAttribute("status", "success");
			model.addAttribute("boardList", lst); // model 객체에 데이터 바인딩
			model.addAttribute("pagingInfo", pi);
			model.addAttribute("search", searchCriteriaDTO);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", "error");

		} // 서비스 메서드 호출

//		for (HBoardVO b : lst) {
//			System.out.println(b.toString());
//		}

	}

	@RequestMapping(value = "/showSaveBoardForm", method = RequestMethod.GET)
	public void showSaveBoardForm() { // 게시판 글 저장페이지를 출력하는 메서드
	}

	@RequestMapping(value = "/saveBoard", method = RequestMethod.POST)
	public String saveBoard(HBoardDTO boardDTO, RedirectAttributes rttr) {
		System.out.println("글 저장하러 가자" + boardDTO.toString());

		try {
			if (service.saveBoard(boardDTO)) {
				System.out.println("게시글저장성공");
				rttr.addAttribute("status", "success");
			}

		} catch (Exception e) {
			e.printStackTrace();
			rttr.addAttribute("status", "fail");
		}

		return "redirect:/cboard/listAll"; // 게시글 전체 목록 페이지로 돌아감.
	}

	// 아래의 viewBoard()는
//	/viewBoard(게시글상세보기), 
//	/modifyBoard(게시글 수정하기 위애 게시글을 불러오기)
	@GetMapping(value = { "/viewBoard", "/modifyBoard" })
	public String viewBoard(@RequestParam(value = "boardNo", defaultValue = "-1") int boardNo, Model model,
			HttpServletRequest request) {
//		logger.info(boardNo + "번 글을 조회하자~");
		BoardDetailInfo boardDetailInfo = null;
		String ipAddr = GetClientIPAddr.getClientIp(request);
		String returnViewPage = "";

		System.out.println(request.getRemoteAddr() + "가 " + boardNo + "번 글을 조회한다!");
		logger.info(ipAddr + "가 " + boardNo + "번 글을 조회한다!");
		System.out.println("URI" + request.getRequestURI());

		if (boardNo == -1) {
			return "redirect:/cboard/listAll";
		} else {
			try {

				if (request.getRequestURI().equals("/cboard/viewBoard")) {
					System.out.println("게시글 상세보기 호출");
					boardDetailInfo = service.read(boardNo, ipAddr);
					returnViewPage = "/cboard/viewBoard";
				} else if (request.getRequestURI().equals("/cboard/modifyBoard")) {
					System.out.println("게시글 수정하기 호출");
					returnViewPage = "/cboard/modifyBoard";
					boardDetailInfo = service.readBoard(boardNo);

				}

			} catch (Exception e) {
				e.printStackTrace();
				returnViewPage = "redirect:/cboard/listAll?status=fail";
			}
			model.addAttribute("board", boardDetailInfo);
		}
		return returnViewPage;
	}

	// 게시글 삭제

	@RequestMapping(value = "/removeBoard")
	public String removeBoard(@RequestParam("boardNo") int boardNo, HttpServletRequest request,
			RedirectAttributes rttr) {
		System.out.println(boardNo + "번 글을 삭제하자~");

		return "redirect:/hboard/listAll";
	}

	@RequestMapping(value = "/modifyBoardSave", method = RequestMethod.POST)
	public String modifyBoardSave(HBoardDTO modifyBoard, HttpServletRequest request, RedirectAttributes rttr) {
		System.out.println(modifyBoard.toString() + "를 수정 하자");

		try {

			if (service.modifyBoard(modifyBoard)) {
				System.out.println("게시글 수정 완료");
				rttr.addAttribute("status", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rttr.addAttribute("status", "fail");
		}

		return "redirect:/cboard/viewBoard?boardNo=" + modifyBoard.getBoardNo();
	}
}
