<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>commonError.jsp</h1>
	
	<div>${errorMsg }</div>
	
	<%-- <div>${errorStack }</div> --%>
	<ul>
		<c:forEach var="err" items="${errorStack }">
			<li>${err }</li>
		</c:forEach>
	</ul>
</body>
</html>