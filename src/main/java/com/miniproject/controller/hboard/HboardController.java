package com.miniproject.controller.hboard;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miniproject.controller.HomeController;
import com.miniproject.model.HBoardVO;
import com.miniproject.service.hboard.HBoardService;

// Controller단에서 해야 할 일
// 1) URI 매핑 (어떤 URI가 어떤방식 (GET/POST)으로 호출되었을 때 어떤 메서드에 매핑 시킬 것이냐)
// 2) 있다면 view단에서 보내준 매개 변수 수집
// 3) 데이터베이스에 대한 CRUD 를 수행하기 위해 service단의 해당 메서드를 호출. 
// 		service단에서 return 값을 view으로 바인딩 + view단 호출
// 4) 부가적으로 컨트롤러단은 Servlet에 의해 동작되는 모듈이기 때문에, HttpServletRequest, HttpServletResponse,
//		HttpSession 등의 Servlet 객체를 이용할 수 있다.
//		그 기능은 컨트롤러단에서 구현한다.


@Controller // 아래의 클래스가 컨트롤러 객체임을 명시
@RequestMapping("/hboard") // 해당 이름으로 들어오는 요청을 수행
public class HboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject // service 주입
	private HBoardService service;
	
	@RequestMapping("/listAll") // "/hboard/listAll"
	public void listAll(Model model) {
		logger.info("HboardController.listAll()...............");
		
		List<HBoardVO> lst = service.getAllBoard(); // 서비스 메서드 호출
		
		for (HBoardVO b : lst) {
			System.out.println(b.toString());
		}
		model.addAttribute("boardList", lst);
		
		
	}
}
