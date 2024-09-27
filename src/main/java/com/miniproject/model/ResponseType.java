package com.miniproject.model;



// 성공/ 실패 했을 때의 메세지와 코드가지고 있는 enum
public enum ResponseType {
	SUCCESS(200), FAIL(400);
	
	private int resultCode;
	
	ResponseType(int resultCode) { // 생성자 (private)
		this.resultCode = resultCode;
	}
	
	public int getResultCode() {
		return this.resultCode;
	}
	
	public String getResultMessage() {
		return this.name(); //SUCCESS, FAIL이 String으로 반환
	}
}
