package com.miniproject.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyResponseWithData<T> {
	private int resultCode;
	private String resultMessage;
	private T data;

	@Builder // 생성자 위에 사용하는 @Builder어노테이션은 아래의 생성자가 가지고 있는 변수를
	// builder패턴으로 만들어 준다.
	public MyResponseWithData(ResponseType responseType, T data) {
		this.resultCode = responseType.getResultCode();
		this.resultMessage = responseType.getResultMessage();
		this.data = data;
	}

	// data없이 성공했다는 코드(200)와 "success)만 전송
	public static MyResponseWithData success() {
		return MyResponseWithData.builder()
				.responseType(ResponseType.SUCCESS)
				.build();
	}
	
	// data + 성공
	public static <T> MyResponseWithData<T> success(T data) {
		return new MyResponseWithData<>(ResponseType.SUCCESS, data);
	}
	
	// 실패
	public static MyResponseWithData fail() {
		return MyResponseWithData.builder()
				.responseType(ResponseType.FAIL)
				.build();
	}
}
