package com.example.disasterapp.controller.action;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.disasterapp.dao.PostDAO;
import com.example.disasterapp.dto.PostDTO;

public class DeletePostAction implements Action {
    private PostDAO postDAO = new PostDAO();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, Exception {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String postIdStr = request.getParameter("postId");

        if (userId == null || postIdStr == null || postIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/app/login"); // またはエラー処理
            return null;
        }

        try {
            int postId = Integer.parseInt(postIdStr);
            PostDTO post = postDAO.findById(postId); // PostDAO に findById メソッドが必要です

            if (post != null && post.getUserId() == userId) {
                postDAO.delete(postId); // PostDAO に delete メソッドが必要です
                response.sendRedirect(request.getContextPath() + "/app/myposts");
                return null;
            } else {
                request.setAttribute("errorMessage", "投稿が見つからないか、削除する権限がありません。");
                return "/error.jsp";
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "投稿IDの形式が不正です。");
            return "/error.jsp";
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "投稿の削除に失敗しました。");
            return "/error.jsp";
        }
    }
}