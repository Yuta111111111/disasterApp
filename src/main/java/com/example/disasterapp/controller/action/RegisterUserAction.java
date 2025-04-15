package com.example.disasterapp.controller.action;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.disasterapp.dao.UserDAO;
import com.example.disasterapp.dto.UserDTO;

public class RegisterUserAction implements Action {
	private UserDAO userDAO = new UserDAO();

	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, Exception {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            return "/WEB-INF/views/register.jsp"; // 登録フォームを表示
        } else if (request.getMethod().equalsIgnoreCase("POST")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String email = request.getParameter("email");

            // 入力値の検証
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty() || !password.equals(confirmPassword)) {
                request.setAttribute("error", "ユーザー名、パスワード、または確認パスワードが正しくありません。");
                return "/WEB-INF/views/register.jsp";
            }
            try {
	            // ユーザー名が既に存在するか確認
	            if (userDAO.findByUsername(username) != null) {
	                request.setAttribute("error", "そのユーザー名は既に登録されています。");
	                return "/WEB-INF/views/register.jsp";
	            }
	
	            // パスワードをハッシュ化せずにそのまま保存 (非推奨)
	            String plainPassword = password;
	
	            // ユーザー情報の登録
	            UserDTO newUser = new UserDTO();
	            newUser.setUsername(username);
	            newUser.setPassword(plainPassword);
	            newUser.setEmail(email);
	
	            userDAO.create(newUser);
	            
	         // ★ 登録成功後、ユーザー情報を取得してセッションに保存
                UserDTO registeredUser = userDAO.findByUsername(username);
                HttpSession session = request.getSession();
                session.setAttribute("userId", registeredUser.getId());
                session.setAttribute("username", registeredUser.getUsername());
	
	            // 登録成功メッセージなどを設定してログインページへリダイレクト
	            request.getSession().setAttribute("successMessage", "登録が完了しました。");
	            response.sendRedirect(request.getContextPath() + "/app/list");
	            return null;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            request.setAttribute("errorMessage", "ユーザー登録処理中にデータベースエラーが発生しました。");
	            return "/error.jsp";
	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("errorMessage", "ユーザー登録処理中に予期せぬエラーが発生しました。");
	            return "/error.jsp";
	        }
        }
        return null;
    }
}
