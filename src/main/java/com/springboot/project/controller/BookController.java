package com.springboot.project.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.project.model.BookDao;
import com.springboot.project.model.BookDo;


//@Controller
public class BookController {
	
	//jstlEx.do가 요청되면 jstlEx()가 실행되도록 코드 구현(뷰어이름 : jstlEx.jsp)
	@RequestMapping(value="/jstlEx.do")
	public String jstlEx() {
		System.out.println("jstlEx() start !! ");
		return "jstlViewer";
	}
	
	//RequestMapping() 기능 : insertBoard.do가 요청이 되면 insertBoard() 실행
	@RequestMapping(value="/insertBook.do")
	public String insertBook() {
		System.out.println("insertBook() start !!");
		return "insertBookView"; //뷰리졸버에 의해서, "boardviews/insertBoardView/jsp" 파일을 찾아가서 실행
	}
	
	//insertBoardProc.do가 요청이 되면 insertBoardProc() 실행

	@RequestMapping(value="/insertBookProc.do")
	public String insertBookProc(BookDo bdo) {
		System.out.println("insertBookProc() 처리 시작!!");
		
		//입력한 값들이 제대로 서버에 전달되는지를 확인
		System.out.println("author : " + bdo.getAuthor());
		System.out.println("title : " + bdo.getTitle());
		System.out.println("price : " + bdo.getPrice());
		
		//Dao를 이용하여 디비에 입력된 데이터를 저장
		BookDao bdao = new BookDao();
		bdao.insertBook(bdo);
		
		return "redirect:getBookList.do";
	}
	
	//getBoardList.do가 요청이 되면, getBoardList() 실행
	//주요기능 : 전체 데이터를 디비로부터 가져오고 가져온 데이터를 뷰어(getBoardListView.jsp)를 통해서 보여주는 메소드
	@RequestMapping(value="/getBookList.do")
	public ModelAndView getBookList(@RequestParam(value = "page", defaultValue = "1") int page, ModelAndView mav) {
	    System.out.println("getBookList() 처리 시작 !!");
	    
	    BookDao bdao = new BookDao();

	    // --- 페이지네이션 로직 ---
	    int pageSize = 8;
	    int pageLimit = 5;
	    int totalCount = bdao.getTotalBookCount();
	    int totalPages = (int) Math.ceil((double) totalCount / pageSize);
	    int startPage = (((int) Math.ceil((double) page / pageLimit)) - 1) * pageLimit + 1;
	    int endPage = startPage + pageLimit - 1;
	    if (endPage > totalPages) {
	        endPage = totalPages;
	    }
	    int offset = (page - 1) * pageSize;
	    // --- 로직 끝 ---

	    // 수정된 getBookList 메소드 호출
	    ArrayList<BookDo> bList = bdao.getBookList(offset, pageSize);
	    
	    // JSP로 넘겨줄 데이터
	    mav.addObject("bList", bList);
	    mav.addObject("totalPages", totalPages);
	    mav.addObject("currentPage", page);
	    mav.addObject("startPage", startPage);
	    mav.addObject("endPage", endPage);
	    mav.addObject("pageLimit", pageLimit);
	    
	    mav.setViewName("getBookListView");
	    return mav;
	}
	
	//modifyBoard.do가 요청되면 modifyBoard() 실행
	//seq 이름으로 값이 넘어오면 해당 seq의 데이터를 수정하는 메소드
	@RequestMapping(value="/modifyBook.do")
	public ModelAndView modifyBook(BookDo bdo, BookDao bdao, ModelAndView mav) {
		System.out.println("modifyBook() 실행 시작 !!");
		System.out.println("seq : " + bdo.getBookId());
		
		//seq에 해당되는 데이터를 디비로부터 가져오기 !!
		BookDo book = bdao.getOneBook(bdo.getBookId());
		mav.addObject("book", book);
		mav.setViewName("modifyBookView");
		
		return mav;
	}
	
	//modifyBoard.do가 요청되면, modifyBoard() 실행
	//seq에 해당하는 디비의 데이터를 수정하는 메소드
	@RequestMapping(value="/modifyBookProc.do")
	public String modifyBookProc(BookDo bdo, BookDao bdao) {
		System.out.println("modifyBookProc() 처리 시작");
		System.out.println("seq : " + bdo.getBookId() + 
						   ", title : " + bdo.getTitle() + 
						   ", author : " + bdo.getAuthor() + 
						   ", price : " + bdo.getPrice());
		
		bdao.updateBook(bdo);
		
		return "redirect:getBookList.do";
	}
	
	@RequestMapping(value="deleteBook.do")
	public String deleteBook(BookDo bdo, BookDao bdao) {
		System.out.println("deleteBook() 처리 시작 !!");
		
		bdao.deleteBook(bdo.getBookId());
		
		return "redirect:getBookList.do";
	}
	
	@RequestMapping(value="/searchBookList.do")
	public String searchBookList(
		@RequestParam(value="searchCon") String searchCon,
		@RequestParam(value="searchKey") String searchKey,
		BookDao bdao, Model model
		) {
		System.out.println("searchBookList() 처리 시작 !!");
		System.out.println("searchCon : " + searchCon);
		System.out.println("searchKey: " + searchKey);
		
		ArrayList<BookDo> bList = bdao.searchBookList(searchCon, searchKey);
		model.addAttribute("bList", bList);
		
		return "getBookListView";
	}
}
