package com.springboot.project.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

@Repository("bookDao")
public class BookDao {
    private String id="root";
    private String password="040511";
    private String url = "jdbc:mysql://localhost:3306/book_sale?characterEncoding=utf-8";
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    public Connection getConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, id, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void closeConn(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if(conn != null) { try { if(!conn.isClosed()) conn.close(); } catch (SQLException e) { e.printStackTrace(); } finally { conn = null; } }
        if(pstmt != null) { try { if(!pstmt.isClosed()) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } finally { pstmt = null; } }
        if(rs != null) { try { if(!rs.isClosed()) rs.close(); } catch (SQLException e) { e.printStackTrace(); } finally { rs = null; } }
    }

    public void insertBook(BookDo bdo) {
        conn = getConn();
        try {
            String sql = "INSERT INTO book (title, author, publisher, content, price, imageUrl, seller_id, " +
                         "underline_pencil, underline_pen, writing_pencil, writing_pen, " +
                         "cover_clean, no_name, no_discoloration, no_damage) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bdo.getTitle());
            pstmt.setString(2, bdo.getAuthor());
            pstmt.setString(3, bdo.getPublisher());
            pstmt.setString(4, bdo.getContent());
            pstmt.setInt(5, bdo.getPrice());
            pstmt.setString(6, bdo.getImageUrl());
            pstmt.setLong(7, bdo.getSellerId());
            pstmt.setBoolean(8, bdo.isUnderline_pencil());
            pstmt.setBoolean(9, bdo.isUnderline_pen());
            pstmt.setBoolean(10, bdo.isWriting_pencil());
            pstmt.setBoolean(11, bdo.isWriting_pen());
            pstmt.setBoolean(12, bdo.isCover_clean());
            pstmt.setBoolean(13, bdo.isNo_name());
            pstmt.setBoolean(14, bdo.isNo_discoloration());
            pstmt.setBoolean(15, bdo.isNo_damage());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
    }

    public int getTotalBookCount() {
        int count = 0;
        conn = getConn();
        try {
            String sql = "SELECT COUNT(*) FROM book";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
        return count;
    }

 // [수정] getBookList 메소드
    public ArrayList<BookDo> getBookList(int offset, int pageSize) {
        ArrayList<BookDo> bList = new ArrayList<>();
        conn = getConn();
        try {
            // [수정] users 테이블과 JOIN하여 판매자 이름(u.name)을 가져옵니다.
            String sql = "SELECT b.*, u.name as sellerName FROM book b " +
                         "JOIN users u ON b.seller_id = u.id " +
                         "ORDER BY b.book_id ASC LIMIT ? OFFSET ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                bList.add(mapBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
        return bList;
    }

    public BookDo getOneBook(int bookId) {
        BookDo bdo = new BookDo();
        conn = getConn();
        try {
            // [수정] users 테이블과 JOIN하여 판매자 이름(u.name)을 sellerName으로 가져옵니다.
            String sql = "SELECT b.*, u.name as sellerName FROM book b " +
                         "JOIN users u ON b.seller_id = u.id " +
                         "WHERE b.book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                bdo = mapBook(rs); // 모든 정보를 담는 mapBook 헬퍼를 그대로 사용합니다.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
        return bdo;
    }
    
    public void updateBook(BookDo bdo) {
        conn = getConn();
        try {
            String sql = "UPDATE book SET title=?, author=?, publisher=?, content=?, price=?, imageUrl=?, " +
                         "underline_pencil=?, underline_pen=?, writing_pencil=?, writing_pen=?, " +
                         "cover_clean=?, no_name=?, no_discoloration=?, no_damage=? " +
                         "WHERE book_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bdo.getTitle());
            pstmt.setString(2, bdo.getAuthor());
            pstmt.setString(3, bdo.getPublisher());
            pstmt.setString(4, bdo.getContent());
            pstmt.setInt(5, bdo.getPrice());
            pstmt.setString(6, bdo.getImageUrl());
            pstmt.setBoolean(7, bdo.isUnderline_pencil());
            pstmt.setBoolean(8, bdo.isUnderline_pen());
            pstmt.setBoolean(9, bdo.isWriting_pencil());
            pstmt.setBoolean(10, bdo.isWriting_pen());
            pstmt.setBoolean(11, bdo.isCover_clean());
            pstmt.setBoolean(12, bdo.isNo_name());
            pstmt.setBoolean(13, bdo.isNo_discoloration());
            pstmt.setBoolean(14, bdo.isNo_damage());
            pstmt.setInt(15, bdo.getBookId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
    }

    public void deleteBook(int bookId) {
        conn = getConn();
        try {
            String sql = "DELETE FROM book WHERE book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
    }

    // [수정] searchBookList 메소드가 mapBook 헬퍼를 사용하도록 변경
    public ArrayList<BookDo> searchBookList(String searchCon, String searchKey) {
        ArrayList<BookDo> bList = new ArrayList<>();
        conn = getConn();
        String sql = "";
        
        String baseQuery = "SELECT b.*, u.name as sellerName FROM book b JOIN users u ON b.seller_id = u.id ";
        
        if ("title".equals(searchCon)) {
            sql = baseQuery + "WHERE b.title LIKE ? ORDER BY b.book_id DESC";
        } else if ("author".equals(searchCon)) {
            sql = baseQuery + "WHERE b.author LIKE ? ORDER BY b.book_id DESC";
        } else if ("publisher".equals(searchCon)) {
            sql = baseQuery + "WHERE b.publisher LIKE ? ORDER BY b.book_id DESC";
        } else {
            closeConn(conn, null, null);
            return bList;
        }

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searchKey + "%");
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                bList.add(mapBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstmt, rs);
        }
        return bList;
    }

    // [수정] 모든 컬럼을 정확히 가져오는 최종 mapBook 헬퍼 메소드
    private BookDo mapBook(ResultSet rs) throws SQLException {
        BookDo bdo = new BookDo();
        bdo.setBookId(rs.getInt("book_id"));
        bdo.setTitle(rs.getString("title"));
        bdo.setAuthor(rs.getString("author"));
        bdo.setPublisher(rs.getString("publisher"));
        bdo.setContent(rs.getString("content"));
        bdo.setPrice(rs.getInt("price"));
        bdo.setImageUrl(rs.getString("imageUrl"));
        bdo.setSellerId(rs.getLong("seller_id"));
        bdo.setSellerName(rs.getString("sellerName"));
        
        bdo.setUnderline_pencil(rs.getBoolean("underline_pencil"));
        bdo.setUnderline_pen(rs.getBoolean("underline_pen"));
        bdo.setWriting_pencil(rs.getBoolean("writing_pencil"));
        bdo.setWriting_pen(rs.getBoolean("writing_pen"));
        bdo.setCover_clean(rs.getBoolean("cover_clean"));
        bdo.setNo_name(rs.getBoolean("no_name"));
        bdo.setNo_discoloration(rs.getBoolean("no_discoloration"));
        bdo.setNo_damage(rs.getBoolean("no_damage"));
        
        return bdo;
    }
}