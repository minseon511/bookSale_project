<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고책 등록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<style>
	:root {
		--primary-color: #E57373;
		--secondary-color: #8D6E63;
		--background-color: #FFF8E1;
		--text-color-dark: #5D4037;
        --border-color: #D7CCC8;
	}

	body {
		background-color: var(--background-color);
		color: var(--text-color-dark);
	}
	.form-style {
		max-width: 600px; /* 등록 페이지는 1열이므로 너비를 600px로 유지 */
		margin: 30px auto;
		padding: 25px;
		background-color: #fff;
		border-radius: 10px;
		box-shadow: 0 8px 20px 0 rgba(0,0,0,0.1);
	}
    
    /* 폼 요소들의 상하 간격(mb-3)을 줄임 */
    .form-style .mb-3 {
        margin-bottom: 0.75rem !important;
    }
    .form-style .form-label {
        margin-bottom: 0.25rem;
        font-size: 0.9em;
    }
    textarea[name="content"] {
        height: 70px;
        font-size: 0.9em;
    }
    .form-text {
        font-size: 0.8em;
    }
    h6 {
        font-size: 1rem;
        margin-top: 0.5rem;
    }

	.form-control, .form-select, .form-check-input {
		border-color: var(--border-color);
	}
    .form-check-input:checked {
        background-color: var(--primary-color);
        border-color: var(--primary-color);
    }
	.form-control:focus, .form-select:focus {
		border-color: var(--primary-color);
		box-shadow: 0 0 0 0.25rem rgba(229, 115, 115, 0.25);
	}
	.btn {
        padding: 0.25rem 0.75rem;
        font-size: 0.9rem;
    }
	.btn-primary {
		background-color: var(--primary-color);
		border-color: var(--primary-color);
	}
    .btn-primary:hover {
        background-color: #D36363;
		border-color: #D36363;
    }
	.btn-danger {
        background-color: #aaa;
        border-color: #aaa;
    }
    .btn-danger:hover {
        background-color: #888;
        border-color: #888;
    }
</style>

</head>
<body>
	<div class="container form-style">
		<h2 class="text-center mb-4">
		    <i class="bi bi-pencil-square me-2"></i> 중고책 등록
		</h2>
		
		<form action="insertBookProc.do" method="post" enctype="multipart/form-data">
			 
			<div class="mb-3">
				<label class="form-label">책 이미지 (Image)</label>
				<input type="file" class="form-control" name="uploadfile">
			</div>
			 
			<div class="mb-3">
			    <label class="form-label">책 제목 (Title)</label>
			    <input type="text" class="form-control" name="title" required>
			</div>
			  
			<div class="mb-3">
			  	<label class="form-label">저자 (Author)</label>
			  	<input type="text" class="form-control" name="author" required>
			</div>

			<div class="mb-3">
			    <label class="form-label">출판사 (Publisher)</label>
			    <input type="text" class="form-control" name="publisher">
			</div>
			  
			<div class="mb-3">
				<label class="form-label">내용 (Content)</label>
				<textarea class="form-control" name="content" rows="3"></textarea>
			</div>

			<div class="mb-3">
				<label class="form-label">가격 (Price)</label>
				<input type="number" class="form-control" name="price" required>
			</div>

			<hr class="my-4">
			<div class="row">
				<div class="col-md-6">
					<h6>필기한 흔적이 있나요?</h6>
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="true" name="underline_pencil">
						<label class="form-check-label">밑줄 (연필/샤프)</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="true" name="underline_pen">
						<label class="form-check-label">밑줄 (볼펜/형광펜)</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="true" name="writing_pencil">
						<label class="form-check-label">필기 (연필/샤프)</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="true" name="writing_pen">
						<label class="form-check-label">필기 (볼펜/형광펜)</label>
					</div>
				</div>
				<div class="col-md-6">
					<h6>보존 상태는 어떤가요?</h6>
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="true" name="cover_clean">
						<label class="form-check-label">겉표지 깨끗함</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="true" name="no_name">
						<label class="form-check-label">이름(서명) 기입 없음</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="true" name="no_discoloration">
						<label class="form-check-label">페이지 변색 없음</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="true" name="no_damage">
						<label class="form-check-label">페이지 훼손 없음</label>
					</div>
				</div>
			</div>
			<hr class="my-4">
			  
			<button type="submit" class="btn btn-primary"> 등록 </button>
			<button type="button" class="btn btn-danger" onclick="location.href='getBookList.do'"> 취소 </button>
		</form>
	</div>
</body>
</html>