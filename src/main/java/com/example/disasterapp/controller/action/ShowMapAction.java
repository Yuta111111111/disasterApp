package com.example.disasterapp.controller.action;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowMapAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String latStr = request.getParameter("lat");
		String lonStr = request.getParameter("lon");
		String imagePath = request.getParameter("imagePath");

		if (latStr != null && lonStr != null) {
			try {
				double lat = Double.parseDouble(latStr);
				double lon = Double.parseDouble(lonStr);
				request.setAttribute("latitude", lat);
				request.setAttribute("longitude", lon);
				request.setAttribute("imagePath", imagePath);
				return "/WEB-INF/views/mapView.jsp";
			} catch (NumberFormatException e) {
				request.setAttribute("error", "緯度・経度の形式が無効です。");
				return "/WEB-INF/views/error.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "地図表示処理中にエラーが発生しました。");
				return "/WEB-INF/views/error.jsp";
			}
		} else {
			request.setAttribute("error", "緯度・経度が指定されていません。");
			return "/WEB-INF/views/error.jsp";
		}
	}
}