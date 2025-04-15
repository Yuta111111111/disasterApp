package com.example.disasterapp.dto;

import java.sql.Timestamp;

public class PostDTO {
	private int id;
    private int userId; // user_id カラムに対応するフィールドを追加
    private String message;
    private String imagePath;
    private Double latitude;
    private Double longitude;
    private Timestamp postedAt;
    private String username;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Timestamp getPostedAt() {
		return postedAt;
	}
	public void setPostedAt(Timestamp postedAt) {
		this.postedAt = postedAt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    
    
}
