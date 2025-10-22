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
		
	<div class="container form-style">
		<p class="fs-2 text-center"> 게시판 등록 </p>
		<form action="insertBookProc.do" method="get">
			 <div class="mb-3">
			    <label for="exampleInputEmail1" class="form-label">책저자(Author)</label>
			    <input type="text" class="form-control" id="exampleInputEmail1" 
				aria-describedby="emailHelp" name="writer">
			  </div>
			  
			  <div class="mb-3">
			    <label for="exampleInputPassword1" class="form-label">책제목(Title)</label>
			    <input type="text" class="form-control" id="exampleInputEmail1"
				aria-describedby="emailHelp" name="title">
			  </div>
			  
			  <div class="mb-3>
				<label for="exampleInputPassword1" class="form-label">책가격(Price)</label>
				<input type="text" class="form-control" id="exampleInputEmail1"
				aria-describedby="emailHelp" name="content">
			  </div>
			  
			  <button type="submit" class="btn btn-primary"> 저장 </button>
			  <button type="submit" class="btn btn-danger"> 취소 </button>
		</form>
	</div>
</body>
</html>