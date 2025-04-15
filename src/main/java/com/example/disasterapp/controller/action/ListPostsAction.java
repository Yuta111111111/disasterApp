package com.example.disasterapp.controller.action;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.disasterapp.dao.PostDAO;

public class ListPostsAction implements Action {
    private PostDAO postDAO = new PostDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        try {
        	request.setAttribute("posts", postDAO.findAll());
            return "/WEB-INF/views/postList.jsp";
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "投稿一覧の取得中にデータベースエラーが発生しました。");
            return "/WEB-INF/views/error.jsp";
        }
    }
}