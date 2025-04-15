package com.example.disasterapp.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.disasterapp.controller.action.Action;
import com.example.disasterapp.controller.action.CreatePostAction;
import com.example.disasterapp.controller.action.DeletePostAction;
import com.example.disasterapp.controller.action.ListPostsAction;
import com.example.disasterapp.controller.action.LoginAction;
import com.example.disasterapp.controller.action.LogoutAction;
import com.example.disasterapp.controller.action.RegisterUserAction;
import com.example.disasterapp.controller.action.ShowMapAction;
import com.example.disasterapp.controller.action.ShowMyPostsAction;
import com.example.disasterapp.controller.action.ShowMypageAction;
import com.example.disasterapp.controller.action.ShowNewFormAction;

@WebServlet("/app/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 15)
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Action> actionMap;
	private String uploadPath;

	@Override
	public void init() throws ServletException {
		uploadPath = getServletContext().getRealPath("/uploads");
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		actionMap = new HashMap<>();
		actionMap.put("/list", new ListPostsAction());
		actionMap.put("/new", new ShowNewFormAction());
		actionMap.put("/map", new ShowMapAction());
		actionMap.put("/create", new CreatePostAction(uploadPath));
		actionMap.put("/register", new RegisterUserAction());
		actionMap.put("/login", new LoginAction());
		actionMap.put("/mypage", new ShowMypageAction());
		actionMap.put("/logout", new LogoutAction());
		actionMap.put("/myposts", new ShowMyPostsAction());
		actionMap.put("/deletepost", new DeletePostAction());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		if (action == null) {
			action = "/list"; // デフォルトアクション
		}

		try {
			Action handler = actionMap.get(action);
			if (handler != null) {
				String viewPath = handler.execute(request, response);
				if (viewPath != null) {
					forwardTo(request, response, viewPath);
				}
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (SQLException ex) {
			throw new ServletException("Database error", ex);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost メソッドが呼び出されました！");
		request.setCharacterEncoding("UTF-8");
		String action = request.getPathInfo();

		try {
			Action handler = actionMap.get(action);
			if (handler != null) {
				String viewPath = handler.execute(request, response);
				if (viewPath != null) {
					if ("/create".equals(action)) {
						response.sendRedirect(request.getContextPath() + "/app/list");
					} else if ("/register".equals(action)) {
						// 登録処理後のリダイレクトは RegisterUserAction で行っているのでここでは何もしない
					} else {
						forwardTo(request, response, viewPath);
					}
				}
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
			}
		} catch (SQLException ex) {
			request.getSession().setAttribute("error", "データベースエラーが発生しました: " + ex.getMessage());
			response.sendRedirect(request.getContextPath() + "/app/new");
			ex.printStackTrace();
		} catch (Exception ex) {
			request.getSession().setAttribute("error", "処理中にエラーが発生しました: " + ex.getMessage());
			response.sendRedirect(request.getContextPath() + "/app/new");
			ex.printStackTrace();
		}
	}

	private void forwardTo(HttpServletRequest request, HttpServletResponse response, String jspPath)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}
}