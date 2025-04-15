package com.example.disasterapp.controller.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.disasterapp.dao.PostDAO;
import com.example.disasterapp.dto.PostDTO;

public class ShowMyPostsAction implements Action {
	private PostDAO postDAO = new PostDAO();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, Exception {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/app/login");
            return null;
        }
		
		try {
			List<PostDTO> myposts = postDAO.findByUserId(userId);
			request.setAttribute("myPosts", myposts);
			return "/WEB-INF/views/myposts.jsp";
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "投稿リストの取得に失敗しました。");
			return "/error.jsp";
		}
	}

}
