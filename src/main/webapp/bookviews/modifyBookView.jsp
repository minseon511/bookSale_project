<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고책 수정</title>
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
		max-width: 900px;
		margin: 30px auto; /* 상단 여백을 줄임 */
		padding: 25px; /* 내부 여백을 줄임 */
		background-color: #fff;
		border-radius: 10px;
		box-shadow: 0 8px 20px 0 rgba(0,0,0,0.1);
	}
    
	.current-img {
		max-width: 100%;
        max-height: 200px; /* 이미지 최대 높이를 줄임 */
		border-radius: 5px;
        object-fit: contain;
	}

    /* [변경점] 폼 요소들의 상하 간격(mb-3)을 줄임 */
    .form-style .mb-3 {
        margin-bottom: 0.75rem !important;
    }
    .form-style .form-label {
        margin-bottom: 0.25rem; /* 라벨과 입력칸 사이 여백 줄임 */
        font-size: 0.9em;
    }
    textarea[name="content"] {
        height: 70px; /* 내용 입력칸 높이를 더 줄임 */
        font-size: 0.9em;
    }
    .form-text {
        font-size: 0.8em;
    }
    h5 {
        font-size: 1.1rem;
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
        padding: 0.25rem 0.75rem; /* 버튼 크기를 줄임 */
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
	.btn-danger, .btn-secondary {
        background-color: #aaa;
        border-color: #aaa;
    }
    .btn-danger:hover, .btn-secondary:hover {
        background-color: #888;
        border-color: #888;
    }
</style>
</head>
<body>
	<div class="container form-style" style="max-width: 900px;">
		<i class="bi bi-pencil-square"></i>
		<p class="fs-2 text-center mb-4">판매 정보 수정</p>
		
		<form action="modifyBookProc.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="bookId" value="${book.bookId}">
			<input type="hidden" name="imageUrl" value="${book.imageUrl}">
			
            <div class="row g-5">
                <div class="col-md-5">
                    <h5>책 이미지</h5>
                    <div class="mb-3">
                        <label class="form-label small">현재 이미지</label>
                        <div>
                            <c:if test="${not empty book.imageUrl}"><img src="/images/${book.imageUrl}" alt="Current Image" class="current-img"></c:if>
                            <c:if test="${empty book.imageUrl}"><span class="text-muted">이미지가 없습니다.</span></c:if>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small">새 이미지로 교체</label>
                        <input type="file" class="form-control" name="uploadfile">
                        <div class="form-text">새로운 이미지를 업로드하지 않으면 기존 이미지가 유지됩니다.</div>
                    </div>
                </div>

                <div class="col-md-7">
                    <h5>책 정보</h5>
                    <div class="mb-3"><label class="form-label">책 제목</label><input type="text" class="form-control" name="title" value="${book.title}" required></div>
                    <div class="mb-3"><label class="form-label">저자</label><input type="text" class="form-control" name="author" value="${book.author}" required></div>
                    <div class="mb-3"><label class="form-label">출판사</label><input type="text" class="form-control" name="publisher" value="${book.publisher}"></div>
                    <div class="mb-3"><label class="form-label">내용</label><textarea class="form-control" name="content" rows="4">${book.content}</textarea></div>
                    <div class="mb-3"><label class="form-label">가격</label><input type="number" class="form-control" name="price" value="${book.price}" required></div>
                    
                    <hr class="my-4">
                    <h5>책 상태</h5>
                    <div class="row">
                        <div class="col-6">
                            <div class="form-check"><input class="form-check-input" type="checkbox" value="true" name="underline_pencil" <c:if test="${book.underline_pencil}">checked</c:if>><label class="form-check-label">밑줄 (연필/샤프)</label></div>
                            <div class="form-check"><input class="form-check-input" type="checkbox" value="true" name="underline_pen" <c:if test="${book.underline_pen}">checked</c:if>><label class="form-check-label">밑줄 (볼펜/형광펜)</label></div>
                            <div class="form-check"><input class="form-check-input" type="checkbox" value="true" name="writing_pencil" <c:if test="${book.writing_pencil}">checked</c:if>><label class="form-check-label">필기 (연필/샤프)</label></div>
                            <div class="form-check"><input class="form-check-input" type="checkbox" value="true" name="writing_pen" <c:if test="${book.writing_pen}">checked</c:if>><label class="form-check-label">필기 (볼펜/형광펜)</label></div>
                        </div>
                        <div class="col-6">
                            <div class="form-check"><input class="form-check-input" type="checkbox" value="true" name="cover_clean" <c:if test="${book.cover_clean}">checked</c:if>><label class="form-check-label">겉표지 깨끗함</label></div>
                            <div class="form-check"><input class="form-check-input" type="checkbox" value="true" name="no_name" <c:if test="${book.no_name}">checked</c:if>><label class="form-check-label">이름(서명) 기입 없음</label></div>
                            <div class="form-check"><input class="form-check-input" type="checkbox" value="true" name="no_discoloration" <c:if test="${book.no_discoloration}">checked</c:if>><label class="form-check-label">페이지 변색 없음</label></div>
                            <div class="form-check"><input class="form-check-input" type="checkbox" value="true" name="no_damage" <c:if test="${book.no_damage}">checked</c:if>><label class="form-check-label">페이지 훼손 없음</label></div>
                        </div>
                    </div>
                </div>
            </div>
            
            <hr class="my-4">

            <div class="d-flex justify-content-end">
                <button type="button" class="btn btn-secondary me-2" onclick="location.href='getBookList.do'">취소</button>
                <button type="submit" class="btn btn-primary">수정 완료</button>
            </div>
		</form>
	</div>
</body>
</html>