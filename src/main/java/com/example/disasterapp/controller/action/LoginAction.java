package com.example.disasterapp.controller.action;

import java.io.IOException;
import java.sql.SQLException;

import com.example.disasterapp.dao.UserDAO;
import com.example.disasterapp.dto.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginAction implements Action {
	private UserDAO userDAO = new UserDAO();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return "/WEB-INF/views/login.jsp"; // ログインフォームを表示
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
				request.setAttribute("error", "ユーザー名とパスワードを入力してください。");
				return "/WEB-INF/views/login.jsp";
			}

			try {
				UserDTO user = userDAO.findByUsername(username);

				if (user != null && user.getPassword().equals(password)) {
					// 認証成功
					HttpSession session = request.getSession();
					session.setAttribute("userId", user.getId()); // ユーザーIDをセッションに保存 (例)
					session.setAttribute("username", user.getUsername()); // ユーザー名をセッションに保存 (例)
					response.sendRedirect(request.getContextPath() + "/app/list"); // 投稿一覧へリダイレクト
					return null;
				} else {
					// 認証失敗
					request.setAttribute("error", "ユーザー名またはパスワードが間違っています。");
					return "/WEB-INF/views/login.jsp";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "ログイン処理中にデータベースエラーが発生しました。");
				return "/error.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "ログイン処理中に予期せぬエラーが発生しました。");
				return "/error.jsp";
			}
		}
		return null;
	}
}
