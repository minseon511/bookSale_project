<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고책 거래 플랫폼</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<style>
	:root {
		--primary-color: #E57373; /* 부드러운 오렌지 */
		--secondary-color: #8D6E63; /* 옅은 갈색 */
		--background-color: #FFF8E1; /* 밝은 크림색 */
		--text-color-dark: #5D4037; /* 짙은 갈색 */
		--text-color-light: #BCAAA4; /* 아주 연한 갈색 */
		--card-bg: #FFFFFF;
		--header-bg: #FFFFFF;
	}
	body { background-color: var(--background-color); color: var(--text-color-dark); }
	.main-container { max-width: 1200px; margin: 30px auto 0; }
	header { background-color: var(--header-bg) !important; }
	header .text-dark { color: var(--text-color-dark) !important; }
	header .btn-primary { background-color: var(--primary-color); border-color: var(--primary-color); }
	header .btn-primary:hover { background-color: #D36363; border-color: #D36363; }
	header .btn-outline-secondary, header .form-select { border-color: var(--text-color-light); color: var(--text-color-dark); }
	header .btn-outline-secondary:hover { background-color: var(--secondary-color); color: var(--card-bg); }
	.card { transition: transform .2s, box-shadow .2s; background-color: var(--card-bg); border: 1px solid var(--text-color-light); }
	.card:hover { transform: translateY(-5px); box-shadow: 0 8px 20px 0 rgba(0,0,0,0.15); }
	.card-title { color: var(--text-color-dark); }
	.card-text.text-muted.small { color: var(--text-color-light) !important; }
	.card-content-clamp { overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; height: 3em; color: var(--text-color-dark); }
	.badge { font-size: 0.75em; }
	.fw-bold.fs-5.text-primary { color: var(--primary-color) !important; }
	.btn-outline-primary { border-color: var(--primary-color); color: var(--primary-color); }
	.btn-outline-primary:hover { background-color: var(--primary-color); color: var(--card-bg); }
	.btn-outline-secondary { border-color: var(--secondary-color); color: var(--secondary-color); }
	.btn-outline-secondary:hover { background-color: var(--secondary-color); color: var(--card-bg); }
	.btn-outline-danger { border-color: #E74C3C; color: #E74C3C; }
	.btn-outline-danger:hover { background-color: #E74C3C; color: var(--card-bg); }
	.pagination .page-link { color: var(--secondary-color); border-color: var(--text-color-light); }
	.pagination .page-item.active .page-link { background-color: var(--primary-color); border-color: var(--primary-color); color: var(--card-bg); }
	.pagination .page-link:hover { color: var(--primary-color); }
</style>

</head>
<body>
	<div class="container main-container">
		
		<header class="d-flex justify-content-between align-items-center p-3 mb-4 bg-white rounded shadow-sm">
			<a href="getBookList.do" class="d-flex align-items-center text-dark text-decoration-none">
				<i class="fas fa-book-open fa-2x me-3"></i>
				<span class="fs-4 d-none d-lg-inline">중고 책방</span>
			</a>
	
			<form action="searchBookList.do" class="d-flex w-50" method="get">
				<select name="searchCon" class="form-select flex-shrink-0" style="width: auto;">
				  <option value="title" selected>책 제목</option>
				  <option value="author">저자</option>
				  <option value="publisher">출판사</option>
				</select>
				<input name="searchKey" class="form-control me-2" type="search" placeholder="책 제목, 저자, 출판사로 검색해보세요">
				<button class="btn btn-outline-secondary" type="submit">
					<i class="fas fa-search"></i>
				</button>
			</form>
	
			<div class="d-flex align-items-center">
				<sec:authorize access="!isAuthenticated()">
					<a href="#" onclick="alert('로그인이 필요한 기능입니다.');" class="text-dark fs-5 ms-3"><i class="fas fa-shopping-cart"></i></a>
					<a href="/oauth2/authorization/google" class="btn btn-primary btn-sm ms-3">구글 로그인</a>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<a href="insertBook.do" class="btn btn-primary btn-sm me-3">+ 책 판매하기</a>
					<a href="/cart" class="text-dark fs-5 ms-3"><i class="fas fa-shopping-cart"></i></a>
					<div class="dropdown ms-3">
						<a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" id="dropdownUser" data-bs-toggle="dropdown" aria-expanded="false">
							<sec:authentication property="principal.attributes['name']"/>님
						</a>
						<ul class="dropdown-menu text-small" aria-labelledby="dropdownUser">
							<li><a class="dropdown-item" href="/logout">로그아웃</a></li>
						</ul>
					</div>
				</sec:authorize>
			</div>
		</header>
		
		<div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
			<c:forEach items="${bList}" var="book">
				<div class="col">
					<div class="card h-100 shadow-sm">
						<c:choose>
						    <c:when test="${not empty book.imageUrl}"><img src="/images/${book.imageUrl}" class="card-img-top" alt="${book.title}" style="height: 250px; object-fit: cover;"></c:when>
						    <c:otherwise><img src="https://placehold.co/600x800?text=No+Image" class="card-img-top" alt="No Image" style="height: 250px; object-fit: cover;"></c:otherwise>
						</c:choose>
						<div class="card-body d-flex flex-column">
							<h5 class="card-title">${book.title}</h5>
							<p class="card-text text-muted small">${book.author} · ${book.publisher}</p>
							
                            <div class="mb-2">
                                <c:if test="${book.cover_clean}"><span class="badge bg-success me-1">겉표지 깨끗</span></c:if>
                                <c:if test="${book.no_name}"><span class="badge bg-success me-1">이름 기입 없음</span></c:if>
                                <c:if test="${book.no_damage}"><span class="badge bg-info text-dark me-1">훼손 없음</span></c:if>
                                <c:if test="${book.no_discoloration}"><span class="badge bg-info text-dark me-1">변색 없음</span></c:if>
                                <c:if test="${book.writing_pen or book.writing_pencil}"><span class="badge bg-warning text-dark me-1">필기 흔적</span></c:if>
                                <c:if test="${book.underline_pen or book.underline_pencil}"><span class="badge bg-secondary me-1">밑줄 흔적</span></c:if>
                            </div>

							<p class="card-text card-content-clamp">${book.content}</p>

							<div class="mt-auto pt-3">
                                <p class="card-text text-end small mb-2">판매자: ${book.sellerName}</p>
								<span class="fw-bold fs-5 text-primary">${book.price}원</span>
								<div class="float-end">
									<sec:authorize access="!isAuthenticated()">
										<a href="#" onclick="alert('로그인이 필요한 기능입니다.');" class="btn btn-outline-primary btn-sm" title="장바구니에 담기">
											<i class="fas fa-cart-plus"></i>
										</a>
									</sec:authorize>
									<sec:authorize access="isAuthenticated()">
                                        <c:if test="${loginUserId != book.sellerId}">
                                            <form action="/add-to-cart" method="post" style="display: inline-block;">
                                                <input type="hidden" name="bookId" value="${book.bookId}">
                                                <button type="submit" class="btn btn-outline-primary btn-sm" title="장바구니에 담기">
                                                    <i class="fas fa-cart-plus"></i>
                                                </button>
                                            </form>
                                        </c:if>
									</sec:authorize>
									
									<sec:authorize access="isAuthenticated()">
										<c:if test="${loginUserId == book.sellerId}">
											<a href="modifyBook.do?bookId=${book.bookId}" class="btn btn-outline-secondary btn-sm ms-1">수정</a>
											<a href="deleteBook.do?bookId=${book.bookId}" class="btn btn-outline-danger btn-sm">삭제</a>
										</c:if>
									</sec:authorize>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		
		<nav aria-label="Page navigation" class="d-flex justify-content-center mt-4">
		    <ul class="pagination">
		        <c:if test="${currentPage > 1}"><li class="page-item"><a class="page-link" href="getBookList.do?page=${currentPage - 1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li></c:if>
		        <c:forEach begin="${startPage}" end="${endPage}" var="i">
		            <c:choose>
		                <c:when test="${i == currentPage}"><li class="page-item active" aria-current="page"><span class="page-link">${i}</span></li></c:when>
		                <c:otherwise><li class="page-item"><a class="page-link" href="getBookList.do?page=${i}">${i}</a></li></c:otherwise>
		            </c:choose>
		        </c:forEach>
		        <c:if test="${currentPage < totalPages}"><li class="page-item"><a class="page-link" href="getBookList.do?page=${currentPage + 1}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li></c:if>
		    </ul>
		</nav>
	</div>
</body>
</html>