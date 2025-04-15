package com.example.disasterapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.disasterapp.dto.UserDTO;

import db.DBManager;

public class UserDAO {

	public void create(UserDTO user) throws SQLException {
		String sql = "INSERT INTO users (username, password, email, created_at) VALUES (?, ?, ?, NOW())";
		try (Connection conn = DBManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.executeUpdate();
		}
	}

	public UserDTO findByUsername(String username) throws SQLException {
		String sql = "SELECT id, username, password, email, created_at, updated_at FROM users WHERE username = ?";
		try (Connection conn = DBManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					UserDTO user = new UserDTO();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					user.setCreatedAt(LocalDateTime.parse(rs.getString("created_at"),
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					if (rs.getString("updated_at") != null) {
						user.setUpdatedAt(LocalDateTime.parse(rs.getString("updated_at"),
								DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					}
					return user;
				}
			}
		}
		return null;
	}

	public UserDTO findById(int id) throws SQLException {
		String sql = "SELECT id, username, password, email, created_at, updated_at FROM users WHERE id = ?";
		try (Connection conn = DBManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					UserDTO user = new UserDTO();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					user.setCreatedAt(LocalDateTime.parse(rs.getString("created_at"),
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					if (rs.getString("updated_at") != null) {
						user.setUpdatedAt(LocalDateTime.parse(rs.getString("updated_at"),
								DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					}
					return user;
				}
			}
		}
		return null;
	}

}