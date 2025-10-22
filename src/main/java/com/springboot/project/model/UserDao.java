package com.springboot.project.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
    private String id="root";
    private String password="040511";
    private String url = "jdbc:mysql://localhost:3306/book_sale?characterEncoding=utf-8";

    private Connection getConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, id, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if(rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
        if(pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
        if(conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setPicture(rs.getString("picture"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // [수정] save 메소드 전체를 아래 코드로 교체
    public User save(User user) {
        User existingUser = findByEmail(user.getEmail());
        if (existingUser != null) {
            // 이미 있는 유저면 업데이트
            String sql = "UPDATE users SET name = ?, picture = ? WHERE email = ?";
            try (Connection conn = getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getPicture());
                pstmt.setString(3, user.getEmail());
                pstmt.executeUpdate();
                
                user.setId(existingUser.getId());
                user.setRole(existingUser.getRole()); // [수정] 기존 사용자의 role을 설정
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // 새로운 유저면 저장
            String sql = "INSERT INTO users (name, email, role, picture) VALUES (?, ?, ?, ?)";
            try (Connection conn = getConn(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                String role = "ROLE_USER"; // 기본 역할
                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getEmail());
                pstmt.setString(3, role);
                pstmt.setString(4, user.getPicture());
                pstmt.executeUpdate();

                user.setRole(role); // [수정] 새로 가입한 사용자의 role을 설정

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}