package com.springboot.project.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.project.model.BookDao;
import com.springboot.project.model.BookDo;
import com.springboot.project.service.BookService; // BookService 추가
import com.springboot.project.model.CartDao;

import com.springboot.project.model.UserDao; // import 추가
import org.springframework.security.core.annotation.AuthenticationPrincipal; // import 추가
import org.springframework.security.oauth2.core.user.OAuth2User; // import 추가
import com.springboot.project.model.User;
import java.util.List;

@Controller
public class BookControllerModel {

    // application.properties에 설정한 파일 업로드 경로를 가져옵니다.
    @Value("${file.upload-dir}")
    private String uploadDir;
    
    @Autowired
    private BookService bookService;
	
		@RequestMapping(value="/insertBook.do")
		public String insertBook() {
			System.out.println("insertBook() start !!");
			return "insertBookView";
		}
	
		// BookControllerModel.java

		// [수정] insertBookProc 메소드 전체를 아래 코드로 교체
		// BookControllerModel.java

		@RequestMapping(value="/insertBookProc.do", method = RequestMethod.POST)
		public String insertBookProc(BookDo bdo, 
		                             @RequestParam("uploadfile") MultipartFile uploadfile,
		                             @AuthenticationPrincipal OAuth2User oauthUser) {
		    
		    System.out.println("insertBookProc() 처리 시작!!");

		    // 1. 현재 로그인한 사용자의 ID를 찾습니다.
		    String email = oauthUser.getAttribute("email");
		    UserDao userDao = new UserDao();
		    User user = userDao.findByEmail(email);
		    
		    // 2. BookDo 객체에 판매자 ID를 설정합니다.
		    bdo.setSellerId(user.getId());
		    
		    // [추가된 확인용 코드] 콘솔에 판매자 ID가 찍히는지 확인합니다.
		    System.out.println("#############################################");
		    System.out.println("### SELLER ID SET TO: " + bdo.getSellerId() + " ###");
		    System.out.println("#############################################");

		    // 3. 파일 업로드 처리
		    if (uploadfile != null && !uploadfile.isEmpty()) {
		        try {
		            String originalFileName = uploadfile.getOriginalFilename();
		            String savedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
		            File newFile = new File(uploadDir + savedFileName);
		            uploadfile.transferTo(newFile);
		            bdo.setImageUrl(savedFileName);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		    
		    // 4. 판매자 ID가 포함된 책 정보를 DB에 저장
		    BookDao bdao = new BookDao();
		    bdao.insertBook(bdo);
		    
		    return "redirect:getBookList.do";
		}

	
		@RequestMapping(value="/getBookList.do")
		public String getBookList(@RequestParam(value = "page", defaultValue = "1") int page, 
		                          Model model,
		                          @AuthenticationPrincipal OAuth2User oauthUser) { // [추가] 로그인 정보를 받아오는 파라미터
		    int pageSize = 4;
		    int pageLimit = 5;
		    
		    int totalCount = bookService.getTotalBookCount();
		    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
		    int startPage = (((int) Math.ceil((double) page / pageLimit)) - 1) * pageLimit + 1;
		    int endPage = startPage + pageLimit - 1;
		    if (endPage > totalPages) {
		        endPage = totalPages;
		    }
		    int offset = (page - 1) * pageSize;

		    ArrayList<BookDo> bList = bookService.getBookListProc(offset, pageSize);
		    
		    model.addAttribute("bList", bList);
		    model.addAttribute("totalPages", totalPages);
		    model.addAttribute("currentPage", page);
		    model.addAttribute("startPage", startPage);
		    model.addAttribute("endPage", endPage);
		    model.addAttribute("pageLimit", pageLimit);
		    
		    // [추가] 로그인한 사용자의 ID를 찾아서 JSP로 전달하는 로직
		    if (oauthUser != null) {
		        // 1. 구글로부터 받은 이메일 정보 가져오기
		        String email = oauthUser.getAttribute("email");
		        
		        // 2. 이메일을 이용해 우리 DB에서 사용자 정보 조회
		        UserDao userDao = new UserDao();
		        User user = userDao.findByEmail(email);

		        // 3. 사용자의 고유 ID를 'loginUserId'라는 이름으로 JSP에 전달
		        if (user != null) {
		            model.addAttribute("loginUserId", user.getId());
		        }
		    }
		    
		    return "getBookListView";
		}
	
		@RequestMapping(value="/modifyBook.do")
		public String modifyBook(@RequestParam("bookId") int bookId, Model model) {
			System.out.println("modifyBook() 실행 시작 !! bookId: " + bookId);
			
			BookDao bdao = new BookDao();
			BookDo book = bdao.getOneBook(bookId); // ID로 책 정보를 DB에서 조회
			model.addAttribute("book", book); // 조회한 정보를 'book'이라는 이름으로 JSP에 전달
			
			return "modifyBookView";
		}
	
	// method를 POST로 지정하고, MultipartFile을 파라미터로 받도록 수정합니다.
	@RequestMapping(value="/modifyBookProc.do", method = RequestMethod.POST)
	public String modifyBookProc(BookDo bdo, @RequestParam("uploadfile") MultipartFile uploadfile) {
		System.out.println("modifyBookProc() 처리 시작");

		// 1. 새로운 파일이 업로드 되었는지 확인 후 처리
        if (uploadfile != null && !uploadfile.isEmpty()) {
            try {
                // 새로운 파일이므로 고유한 파일명 새로 생성
                String originalFileName = uploadfile.getOriginalFilename();
                String savedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                
                // 새 파일 저장
                File newFile = new File(uploadDir + savedFileName);
                uploadfile.transferTo(newFile);
                
                // BookDo 객체에 새로운 이미지 파일 이름 저장
                bdo.setImageUrl(savedFileName);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("수정 중 파일 업로드 오류 발생");
            }
        }
        // 새 파일이 없다면, 기존 imageUrl 값은 hidden input을 통해 bdo에 이미 담겨있으므로 별도 처리 필요 없음

		// 2. 데이터베이스에 수정된 책 정보 업데이트
		BookDao bdao = new BookDao();
		bdao.updateBook(bdo);
		
		return "redirect:getBookList.do";
	}
	
	@RequestMapping(value="/deleteBook.do") // 경로에 슬래시 추가
	public String deleteBook(@RequestParam("bookId") int bookId) { // 명확하게 bookId를 받도록 수정
		System.out.println("deleteBook() 처리 시작 !!");
		
		BookDao bdao = new BookDao();
		bdao.deleteBook(bookId);
		
		return "redirect:getBookList.do";
	}
	
	// BookControllerModel.java

	@RequestMapping(value="/searchBookList.do")
	public String searchBookList(
	    @RequestParam(value="searchCon") String searchCon,
	    @RequestParam(value="searchKey") String searchKey,
	    Model model,
	    @AuthenticationPrincipal OAuth2User oauthUser) { // [추가] 로그인 정보를 받아오는 파라미터
	    
	    System.out.println("searchBookList() 처리 시작 !!");
	    
	    // DAO를 직접 생성하는 대신, 주입받은 서비스를 사용합니다. (이전 구조를 따름)
	    // BookDao bdao = new BookDao(); 
	    // ArrayList<BookDo> bList = bdao.searchBookList(searchCon, searchKey);
	    // 위 코드 대신 아래 서비스 호출 코드를 사용하는 것을 권장합니다.
	    // (만약 서비스에 search 메소드가 없다면 추가해야 합니다.)
	    // 우선은 기존 구조를 유지하기 위해 BookDao를 직접 사용하겠습니다.
	    BookDao bdao = new BookDao();
	    ArrayList<BookDo> bList = bdao.searchBookList(searchCon, searchKey);
	    model.addAttribute("bList", bList);

	    // [추가] getBookList와 동일하게, 로그인한 사용자의 ID를 찾아서 JSP로 전달하는 로직
	    if (oauthUser != null) {
	        String email = oauthUser.getAttribute("email");
	        UserDao userDao = new UserDao();
	        User user = userDao.findByEmail(email);
	        if (user != null) {
	            model.addAttribute("loginUserId", user.getId());
	        }
	    }
	    
	    // 검색 결과에는 페이지네이션 정보가 없으므로, 임시로 1페이지 정보 전달
	    // (나중에 검색 결과에도 페이지네이션을 적용할 수 있습니다)
	    model.addAttribute("currentPage", 1);
	    model.addAttribute("totalPages", 1);
	    model.addAttribute("startPage", 1);
	    model.addAttribute("endPage", 1);

	    return "getBookListView";
	}
	
	// [추가] 장바구니에 책을 추가하는 메소드
    @RequestMapping(value="/add-to-cart", method=RequestMethod.POST)
    public String addToCart(@RequestParam("bookId") int bookId, @AuthenticationPrincipal OAuth2User oauthUser) {
        // 로그인한 사용자의 이메일을 가져옵니다.
        String email = oauthUser.getAttribute("email");
        
        // 이메일로 우리 DB에서 사용자 정보를 찾습니다.
        UserDao userDao = new UserDao();
        User user = userDao.findByEmail(email);
        
        // 장바구니에 추가합니다.
        CartDao cartDao = new CartDao();
        cartDao.addToCart(user.getId(), bookId);
        
        // 원래 있던 목록 페이지로 돌아갑니다.
        return "redirect:getBookList.do";
    }

    // [추가] 장바구니 페이지를 보여주는 메소드
    @RequestMapping(value="/cart")
    public String cartPage(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
        String email = oauthUser.getAttribute("email");
        UserDao userDao = new UserDao();
        User user = userDao.findByEmail(email);
        
        CartDao cartDao = new CartDao();
        ArrayList<BookDo> cartItems = cartDao.getCartItemsByUserId(user.getId());
        
        model.addAttribute("cartItems", cartItems);
        
        return "cart"; // cart.jsp 파일을 보여줍니다.
    }
    
 // [추가] 장바구니에서 책을 삭제하는 요청을 처리하는 메소드
    @RequestMapping(value="/remove-from-cart", method=RequestMethod.POST)
    public String removeFromCart(@RequestParam("bookId") int bookId, @AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        UserDao userDao = new UserDao();
        User user = userDao.findByEmail(email);
        
        CartDao cartDao = new CartDao();
        cartDao.deleteCartItem(user.getId(), bookId);
        
        // 장바구니 페이지로 다시 돌아갑니다.
        return "redirect:/cart";
    }
    
 // [추가] 선택한 장바구니 상품을 '구매' 처리하는 메소드 (실제로는 장바구니에서 삭제)
    @RequestMapping(value="/purchase-cart", method=RequestMethod.POST)
    public String purchaseCart(@RequestParam(value="selectedBooks", required = false) List<Integer> selectedBooks, @AuthenticationPrincipal OAuth2User oauthUser) {
        if (selectedBooks != null && !selectedBooks.isEmpty()) {
            String email = oauthUser.getAttribute("email");
            UserDao userDao = new UserDao();
            User user = userDao.findByEmail(email);
            
            CartDao cartDao = new CartDao();
            cartDao.deleteMultipleCartItems(user.getId(), selectedBooks);
        }
        // '구매 완료' 알림창을 띄우기 위해 파라미터를 추가하여 리다이렉트
        return "redirect:/cart?purchased=true";
    }

    // [추가] 선택한 장바구니 상품을 '삭제'하는 메소드
    @RequestMapping(value="/delete-selected-cart-items", method=RequestMethod.POST)
    public String deleteSelectedCartItems(@RequestParam(value="selectedBooks", required = false) List<Integer> selectedBooks, @AuthenticationPrincipal OAuth2User oauthUser) {
        if (selectedBooks != null && !selectedBooks.isEmpty()) {
            String email = oauthUser.getAttribute("email");
            UserDao userDao = new UserDao();
            User user = userDao.findByEmail(email);
            
            CartDao cartDao = new CartDao();
            cartDao.deleteMultipleCartItems(user.getId(), selectedBooks);
        }
        return "redirect:/cart";
    }
    
}