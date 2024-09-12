package com.miniproject.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor // lombok의 기본생성자 어노테이션
@AllArgsConstructor // lombok의 생성자 어노테이션
@Getter // lombok의 getter 어노테이션
@Setter // lombok의 setter 어노테이션
@ToString // lombok의 toString 어노테이션
public class HBoardVO {
	
	private int boardNo;
	private String title;
	private String content;
	private String writer;
	private Timestamp postDate;
	private int readCount;
	private int ref;
	private int step;
	private int refOrder;
	private String isDelete;
	
	
	
	
}
