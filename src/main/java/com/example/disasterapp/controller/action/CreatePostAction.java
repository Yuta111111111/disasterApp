package com.example.disasterapp.controller.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.example.disasterapp.dao.PostDAO;
import com.example.disasterapp.dto.PostDTO;
import com.example.disasterapp.util.LocationExtractor;

public class CreatePostAction implements Action {
	private PostDAO postDAO = new PostDAO();
	private String uploadPath;

	public CreatePostAction(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, Exception {
		request.setCharacterEncoding("UTF-8");

		// セッションからユーザーIDを取得
        HttpSession session = request.getSession();
        Integer currentUserId = (Integer) session.getAttribute("userId");

        if (currentUserId == null) {
            // ユーザーIDがセッションにない場合はエラーまたはリダイレクト
            request.setAttribute("errorMessage", "ログインが必要です。");
            return "/WEB-INF/views/login.jsp"; // またはログインページへリダイレクト
        }
        
		String message = request.getParameter("message");
		Part filePart = request.getPart("image");

		String imagePathDb = null;
		Double latitude = null;
		Double longitude = null;

		try {
			if (filePart != null && filePart.getSize() > 0) {
				String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
				String extension = "";
				int i = originalFileName.lastIndexOf('.');
				if (i > 0) {
					extension = originalFileName.substring(i);
				}
				String uniqueFileName = UUID.randomUUID().toString() + extension;
				File imageFile = new File(uploadPath, uniqueFileName);

				try (InputStream input = filePart.getInputStream()) {
					Files.copy(input, imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					imagePathDb = "uploads/" + uniqueFileName;

					LocationExtractor.Location location = LocationExtractor.extractLocation(imageFile);
					if (location != null) {
						latitude = location.latitude;
						longitude = location.longitude;
					} else {
						System.out.println("位置情報が見つかりませんでした: " + originalFileName);
					}

				} catch (IOException e) {
					System.err.println("ファイルアップロードエラー: " + e.getMessage());
					if (imageFile.exists()) {
						imageFile.delete();
					}
					request.setAttribute("errorMessage", "ファイルのアップロード中にエラーが発生しました。");
					return "/WEB-INF/views/error.jsp";
				}
			}

			PostDTO newPost = new PostDTO();
			newPost.setUserId(currentUserId);
			newPost.setMessage(message);
			newPost.setImagePath(imagePathDb);
			newPost.setLatitude(latitude);
			newPost.setLongitude(longitude);

			postDAO.create(newPost);

			request.setAttribute("message", "投稿が正常に完了しました。");
		    return "/WEB-INF/views/success.jsp";
		
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "投稿処理中にデータベースエラーが発生しました。");
			return "/WEB-INF/views/error.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "投稿処理中に予期せぬエラーが発生しました。");
			return "/WEB-INF/views/error.jsp";
		}
	}
}