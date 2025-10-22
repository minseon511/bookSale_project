<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!--부트스트랩 CSS 라이브러리 추가-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<!--부트스트랩 JS 라이브러리 추가-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<style>
	.form-style {
		max=width: 600px;
		margin-top: 50px;
		padding: 30px;
		background-color: #fff;
		border-radius: 10px;
		box-shadow: 0 8px 20px 0 rgba(0,0,255,0.5);
	}
</style>

</head>
<body>
	<h3>1부터 5까지 출력</h3>
	<c:forEach var="i" begin="1" end="5">
		${i}<br>
	</c:forEach>
	
	<h3>짝수 출력</h3>
		<c:forEach var="i" begin="2" end="10" step="2">
			${i}<br>
		</c:forEach>
		
	<c:set var="fruits" value="${['사과','바나나','포도','딸기']}"/>
	<h3>과일 목록</h3>
		<ul>
		    <c:forEach var="fruit" items="${fruits}">
		        <li>${fruit}</li>
		    </c:forEach>
		</ul>

</body>
</html>