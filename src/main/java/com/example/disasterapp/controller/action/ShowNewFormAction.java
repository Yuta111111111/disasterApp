package com.example.disasterapp.controller.action;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowNewFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getMethod().equalsIgnoreCase("GET")) {
				return "/WEB-INF/views/postForm.jsp";
			} else {
				// GET 以外のメソッドでアクセスされた場合は、メソッドを許可しないエラーを返します
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
						"このリクエストメソッドは許可されていません。GET メソッドを使用してください。");
				return null; // エラーレスポンスを直接送信したので、フォワードするパスは null を返します
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "新規投稿フォーム表示中にエラーが発生しました。");
			return "/WEB-INF/views/error.jsp";
		}
	}
}