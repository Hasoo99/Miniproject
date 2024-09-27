package com.miniproject.comment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.model.CommentDTO;
import com.miniproject.model.CommentVO;
import com.miniproject.model.MyResponseWithoutData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
	
	private final CommentService cService;
	
	
	
	@GetMapping("all/{boardNo}")
	public List<CommentVO> getAllCommentsByBoardNo(@PathVariable("boardNo") int boardNo) {
		log.info(boardNo + "번의 모든 댓글 조회하자");
		
		List<CommentVO> lst = null;
		
		try {
			lst = cService.getAllComments(boardNo);
			System.out.println("lst : " + lst.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("캐치");
			e.printStackTrace();
		}
		
		return lst;
	}
	
	@RequestMapping(value="/{boardNo}", method = RequestMethod.POST)
	public ResponseEntity<MyResponseWithoutData> saveComment(@PathVariable("boardNo")int boardNo, @RequestBody CommentDTO newComment) {
		log.info(boardNo + "번 게시글에 새로운 댓글 " + newComment + "를 저장하자~");
		
		ResponseEntity<MyResponseWithoutData> result = null;
		
		try {
			
			if(cService.saveComment(newComment)) {
				result = new ResponseEntity<MyResponseWithoutData>(new MyResponseWithoutData(200, "", "success"), HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<MyResponseWithoutData>(new MyResponseWithoutData(500, "", "fail"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
}
