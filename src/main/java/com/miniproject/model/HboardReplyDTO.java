package com.miniproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class HboardReplyDTO {
	private String title;
	private String content;
	private String writer;
	
	private int ref;
	private int step;
	private int refOrder;
	
}
