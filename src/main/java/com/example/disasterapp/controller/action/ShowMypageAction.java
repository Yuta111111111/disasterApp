package com.example.disasterapp.controller.action;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.disasterapp.dao.UserDAO;
import com.example.disasterapp.dto.UserDTO;

public class ShowMypageAction implements Action {
	private UserDAO userDAO = new UserDAO();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, Exception {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");

		if (userId != null) {
			try {
				UserDTO loggedInUser = userDAO.findById(userId); // UserDAO に findById メソッドが必要です
				if (loggedInUser != null) {
					request.setAttribute("loggedInUser", loggedInUser);
					return "/WEB-INF/views/mypage.jsp";
				} else {
					// ユーザーが見つからない場合はエラー処理
					request.setAttribute("errorMessage", "ユーザー情報が見つかりませんでした。");
					return "/error.jsp";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "データベースエラーが発生しました。");
				return "/error.jsp";
			}
		} else {
			// ログインしていない場合はログインページへリダイレクト
			response.sendRedirect(request.getContextPath() + "/app/login");
			return null;
		}
	}
}