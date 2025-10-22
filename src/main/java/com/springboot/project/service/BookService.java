package com.springboot.project.service;

import java.util.ArrayList;

import com.springboot.project.model.BookDo;

public interface BookService {
	//1. 게시판 글 등록
	void insertBookProc(BookDo bdo);
	
	//2. 전체 게시판 글 가져오기
	// [수정] getBookListProc 메소드에 파라미터 추가
	ArrayList<BookDo> getBookListProc(int offset, int pageSize);
	
	//3. 게시판 글 수정하기
	void updateBookProc(BookDo bdo);
	
	//4. 게시판 글 삭제하기
	void deleteBookProc(int seq);
	
	// [추가] 전체 책 개수를 가져오는 메소드 선언
    int getTotalBookCount();
}
