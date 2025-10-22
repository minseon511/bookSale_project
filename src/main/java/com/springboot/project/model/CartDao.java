package com.springboot.project.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
    private String id="root";
    private String password="040511";
    private String url = "jdbc:mysql://localhost:3306/book_sale?characterEncoding=utf-8";
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // DB 연결 메소드
    public Connection getConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, id, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // DB 연결 해제 메소드
    public void closeConn(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if(conn != null) { try { if(!conn.isClosed()) conn.close(); } catch (SQLException e) { e.printStackTrace(); } finally { conn = null; } }
        if(pstmt != null) { try { if(!pstmt.isClosed()) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } finally { pstmt = null; } }
        if(rs != null) { try { if(!rs.isClosed()) rs.close(); } catch (SQLException e) { e.printStackTrace(); } finally { rs = null; } }
    }

    // 장바구니에 책 추가
    public void addToCart(long userId, int bookId) {
        conn = getConn();
        try {
            // INSERT IGNORE: 이미 데이터가 있으면 무시하고 넘어갑니다.
            String sql = "INSERT IGNORE INTO cart (user_id, book_id) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
    }
    
    // 특정 사용자의 장바구니 목록 가져오기 (책 정보와 함께)
    public ArrayList<BookDo> getCartItemsByUserId(long userId) {
        ArrayList<BookDo> cartItems = new ArrayList<>();
        conn = getConn();
        try {
            String sql = "SELECT b.* FROM book b JOIN cart c ON b.book_id = c.book_id WHERE c.user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, userId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                BookDo book = new BookDo();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setContent(rs.getString("content"));
                book.setPrice(rs.getInt("price"));
                book.setImageUrl(rs.getString("imageUrl"));
                // 책 상태 정보도 필요하면 여기에 추가해야 합니다.
                cartItems.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
        return cartItems;
    }
    
    public void deleteCartItemsByBookId(int bookId) {
        conn = getConn();
        try {
            String sql = "DELETE FROM cart WHERE book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
    }
    
    public void deleteCartItem(long userId, int bookId) {
        conn = getConn();
        try {
            String sql = "DELETE FROM cart WHERE user_id = ? AND book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
    }
    
 // [추가] 장바구니에서 여러 개의 선택된 책을 삭제하는 메소드
    public void deleteMultipleCartItems(long userId, List<Integer> bookIds) {
        // 삭제할 책이 없으면 아무것도 하지 않음
        if (bookIds == null || bookIds.isEmpty()) {
            return;
        }
        
        conn = getConn();
        
        // IN 절에 들어갈 ?를 동적으로 생성합니다. (예: ?,?,?)
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < bookIds.size(); i++) {
            placeholders.append("?");
            if (i < bookIds.size() - 1) {
                placeholders.append(",");
            }
        }
        
        String sql = "DELETE FROM cart WHERE user_id = ? AND book_id IN (" + placeholders.toString() + ")";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, userId);
            
            // ?에 bookId 값을 순서대로 채워넣음
            int index = 2;
            for (Integer bookId : bookIds) {
                pstmt.setInt(index++, bookId);
            }
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
    }
    
}