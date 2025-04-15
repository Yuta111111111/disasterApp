package com.example.disasterapp.controller.action;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, Exception {
        HttpSession session = request.getSession(false); // セッションが存在しない場合は null を返す
        if (session != null) {
            session.invalidate(); // セッションを無効化
        }
        response.sendRedirect(request.getContextPath() + "/app/list"); // 投稿一覧へリダイレクト
        return null;
    }
}