package com.example.disasterapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.example.disasterapp.dto.PostDTO;

import db.DBManager;

public class PostDAO {

    public void create(PostDTO post) throws SQLException {
        String sql = "INSERT INTO posts (user_id, message, image_path, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, post.getUserId()); // userId をセット
            pstmt.setString(2, post.getMessage());
            pstmt.setString(3, post.getImagePath());
            if (post.getLatitude() != null) {
                pstmt.setDouble(4, post.getLatitude());
            } else {
                pstmt.setNull(4, Types.DOUBLE);
            }
            if (post.getLongitude() != null) {
                pstmt.setDouble(5, post.getLongitude());
            } else {
                pstmt.setNull(5, Types.DOUBLE);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // エラーログを出力するなど、適切なエラーハンドリングを行う
            System.err.println("PostDAO create error: " + e.getMessage());
            throw e; // エラーを呼び出し元に再スロー
        }
    }

//    public List<PostDTO> findAll() throws SQLException {
//        List<PostDTO> posts = new ArrayList<>();
//         // user_id を SELECT に追加 (JOINしてユーザー名も取得する例は後述)
//        String sql = "SELECT id, user_id, message, image_path, latitude, longitude, posted_at FROM posts ORDER BY posted_at DESC";
//        try (Connection conn = DBManager.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                PostDTO post = mapResultSetToPostDTO(rs);
//                posts.add(post);
//            }
//        }
//        return posts;
//    }

    // (参考) ユーザー名も一緒に取得する場合のfindAllメソッド
    public List<PostDTO> findAll() throws SQLException {
        List<PostDTO> posts = new ArrayList<>();
        String sql = "SELECT p.id, p.user_id, p.message, p.image_path, p.latitude, p.longitude, p.posted_at, u.username " +
                     "FROM posts p JOIN users u ON p.user_id = u.id " +
                     "ORDER BY p.posted_at DESC";
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PostDTO post = mapResultSetToPostDTO(rs);
                // Post DTOにusernameフィールドとsetterがあればここでセット
                post.setUsername(rs.getString("username"));
                posts.add(post);
            }
        }
        return posts;
    }


    public PostDTO findById(int id) throws SQLException {
         // user_id を SELECT に追加
         String sql = "SELECT id, user_id, message, image_path, latitude, longitude, posted_at FROM posts WHERE id = ?";
         try (Connection conn = DBManager.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
              pstmt.setInt(1, id);
              try (ResultSet rs = pstmt.executeQuery()) {
                  if (rs.next()) {
                      return mapResultSetToPostDTO(rs);
                  }
              }
         }
         return null;
    }

    private PostDTO mapResultSetToPostDTO(ResultSet rs) throws SQLException {
         PostDTO post = new PostDTO();
         post.setId(rs.getInt("id"));
         post.setUserId(rs.getInt("user_id")); // userId をマッピング
         post.setMessage(rs.getString("message"));
         post.setImagePath(rs.getString("image_path"));
         double lat = rs.getDouble("latitude");
         post.setLatitude(rs.wasNull() ? null : lat);
         double lon = rs.getDouble("longitude");
         post.setLongitude(rs.wasNull() ? null : lon);
         post.setPostedAt(rs.getTimestamp("posted_at"));
         return post;
    }
    
    // ShowMyPostsActionで使用
    public List<PostDTO> findByUserId(int userId) throws SQLException {
        List<PostDTO> posts = new ArrayList<>();
        String sql = "SELECT p.id, p.user_id, u.username, p.message, p.image_path, p.latitude, p.longitude, p.posted_at FROM posts p INNER JOIN users u ON p.user_id = u.id WHERE p.user_id = ? ORDER BY p.posted_at DESC";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PostDTO post = new PostDTO();
                    post.setId(rs.getInt("id"));
                    post.setUserId(rs.getInt("user_id"));
                    post.setUsername(rs.getString("username"));
                    post.setMessage(rs.getString("message"));
                    post.setImagePath(rs.getString("image_path"));
                    post.setLatitude(rs.getDouble("latitude"));
                    post.setLongitude(rs.getDouble("longitude"));
                    post.setPostedAt(rs.getTimestamp("posted_at"));
                    posts.add(post);
                }
            }
        }
        return posts;
    }
	
	public void delete(int id) throws SQLException {
        String sql = "DELETE FROM posts WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}