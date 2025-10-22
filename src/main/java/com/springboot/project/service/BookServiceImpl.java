package com.springboot.project.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Transactional 추가

import com.springboot.project.model.BookDo;
import com.springboot.project.model.BookDao;

@Service("bookService")
public class BookServiceImpl implements BookService{
	@Autowired
	private BookDao bdao;

	@Override
	public void insertBookProc(BookDo bdo) {
		bdao.insertBook(bdo);
	}

	@Override
	public ArrayList<BookDo> getBookListProc(int offset, int pageSize) {
		return bdao.getBookList(offset, pageSize);
	}

	@Override
	public void updateBookProc(BookDo bdo) {
		bdao.updateBook(bdo);
	}
	
	@Override
	public void deleteBookProc(int bookId) {
		// 이제 이 한 줄만 있으면 DB가 알아서 장바구니까지 삭제해줍니다.
		bdao.deleteBook(bookId);
	}
    
    @Override
    public int getTotalBookCount() {
        return bdao.getTotalBookCount();
    }
}