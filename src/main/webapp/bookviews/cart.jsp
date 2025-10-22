<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 장바구니</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<style>
    :root {
        --primary-color: #E57373; /* 부드러운 오렌지 */
        --secondary-color: #8D6E63; /* 옅은 갈색 */
        --background-color: #FFF8E1; /* 밝은 크림색 */
        --text-color-dark: #5D4037; /* 짙은 갈색 */
    }

    body {
        background-color: var(--background-color);
        color: var(--text-color-dark);
    }
    .container {
        background-color: #fff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 8px 20px 0 rgba(0,0,0,0.1);
    }
    #totalPrice {
        color: var(--primary-color);
        font-weight: bold;
    }
    /* 구매하기 버튼 스타일 (처음부터 채워진 상태) */
    .btn-primary {
        background-color: var(--primary-color);
        border-color: var(--primary-color);
        color: #fff; /* 글씨 색상 흰색 */
    }
    .btn-primary:hover {
        background-color: #D36363;
        border-color: #D36363;
    }
    .btn-outline-danger {
        border-color: #E74C3C;
        color: #E74C3C;
    }
    .btn-outline-danger:hover {
        background-color: #E74C3C;
        color: #fff;
    }
    /* 쇼핑 계속하기 버튼 (테두리만 있는 상태) */
    .btn-secondary, .btn-outline-secondary {
        color: var(--secondary-color);
        border-color: var(--secondary-color);
    }
    .btn-secondary:hover, .btn-outline-secondary:hover {
        background-color: var(--secondary-color);
        color: #fff;
    }
</style>

</head>
<body>
    <div class="container" style="max-width: 900px; margin-top: 50px;">
        <div class="d-flex justify-content-between align-items-center mb-4">
			<h2 class="mb-4">
			    <i class="bi bi-basket2-fill me-2"></i> 나의 장바구니
			</h2>
            <a href="/getBookList.do" class="btn btn-outline-secondary">← 쇼핑 계속하기</a>
        </div>
        
        <c:choose>
            <c:when test="${not empty cartItems}">
                <form method="post" id="cartForm">
                    <table class="table align-middle">
						<thead>
						    <tr class="text-center">
						        <th scope="col" class="text-start" style="width: 25%;">
						            <div class="d-flex align-items-center ps-2">
						                <input class="form-check-input" type="checkbox" id="selectAll">
						                <button type="submit" formaction="/delete-selected-cart-items" class="btn btn-outline-danger btn-sm ms-2">선택 삭제</button>
						            </div>
						        </th>
						        <th scope="col" style="width: 45%;">상품 정보</th>
						        <th scope="col" style="width: 15%;">가격</th>
						        <th scope="col" style="width: 15%;">관리</th>
						    </tr>
						</thead>
                        <tbody>
                            <c:forEach items="${cartItems}" var="item">
                                <tr>
                                    <td class="text-center"><input class="form-check-input item-check" type="checkbox" name="selectedBooks" value="${item.bookId}" data-price="${item.price}"></td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                        <c:choose>
                                            <c:when test="${not empty item.imageUrl}"><img src="/images/${item.imageUrl}" class="img-fluid rounded me-3" style="width: 50px; height: 70px; object-fit: cover;"></c:when>
                                            <c:otherwise><img src="https://placehold.co/80x100?text=No+Img" class="img-fluid rounded me-3" style="width: 50px; height: 70px; object-fit: cover;"></c:otherwise>
                                        </c:choose>
                                        <div>
                                            <h6 class="my-0">${item.title}</h6>
                                            <small class="text-muted">${item.author}</small>
                                        </div>
                                        </div>
                                    </td>
                                    <td class="text-center"><fmt:formatNumber value="${item.price}" pattern="#,###" />원</td>
                                    <td class="text-center">
                                        <button type="button" onclick="removeItem(${item.bookId})" class="btn btn-outline-danger btn-sm">삭제</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    
                    <div class="d-flex justify-content-end align-items-center mt-4 p-3 bg-light rounded">
                        <div class="text-end">
                            <span class="fs-5">총 주문 금액: <strong id="totalPrice">0</strong>원</span>
                            <button type="submit" formaction="/purchase-cart" class="btn btn-primary ms-3">구매하기</button>
                        </div>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <div class="alert alert-secondary text-center" role="alert">
                    장바구니가 비어 있습니다.
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <%-- ... JavaScript 코드는 동일 ... --%>
    <script>
        function removeItem(bookId) {
            if (confirm("이 상품을 장바구니에서 삭제하시겠습니까?")) {
                const form = document.createElement('form');
                form.method = 'post';
                form.action = '/remove-from-cart';
                const hiddenField = document.createElement('input');
                hiddenField.type = 'hidden';
                hiddenField.name = 'bookId';
                hiddenField.value = bookId;
                form.appendChild(hiddenField);
                document.body.appendChild(form);
                form.submit();
            }
        }
        const selectAll = document.getElementById('selectAll');
        const itemChecks = document.querySelectorAll('.item-check');
        const totalPriceEl = document.getElementById('totalPrice');
        function calculateTotal() {
            let total = 0;
            itemChecks.forEach(checkbox => {
                if (checkbox.checked) {
                    total += parseInt(checkbox.dataset.price);
                }
            });
            totalPriceEl.textContent = total.toLocaleString();
        }
        if (selectAll) {
            selectAll.addEventListener('change', function() {
                itemChecks.forEach(checkbox => {
                    checkbox.checked = this.checked;
                });
                calculateTotal();
            });
        }
        itemChecks.forEach(checkbox => {
            checkbox.addEventListener('change', calculateTotal);
        });
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('purchased')) {
            alert('구매가 완료되었습니다!');
            window.history.replaceState({}, document.title, window.location.pathname);
        }
    </script>
</body>
</html>