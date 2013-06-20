package com.example.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/board/insert")
public class InsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		String author = request.getRemoteUser();
		if (author == null || author.equals("")) {
			author = "dongja94";
		}
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		DBResult result = new DBResult();
		try {
			Connection conn = DBConstant.getConnection();
			PreparedStatement statement = conn.prepareStatement(DBConstant.BoardTable.BOARD_INSERT);
			statement.setString(DBConstant.BoardTable.FIELD_AUTHOR, author);
			statement.setString(DBConstant.BoardTable.FIELD_TITLE, title);
			statement.setString(DBConstant.BoardTable.FIELD_CONTENT, content);
			statement.executeUpdate();
			result.code = "SUCCESS";
			result.message = "OK";
//			request.setAttribute("CODE", "SUCCESS");
//			request.setAttribute("MESSAGE", "OK");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.code = "FAIL";
			result.message = "DB Fail";
//			request.setAttribute("CODE", "FAIL");
//			request.setAttribute("MESSAGE", "DB Fail");
		}
		
		response.setContentType("application/json;charset=euc-kr");
		Gson gson = new Gson();
		String json = gson.toJson(result);
		response.getWriter().print(json);
//		RequestDispatcher dispatcher = request.getRequestDispatcher("../DBResult.jsp");
//		dispatcher.forward(request, response);
	}

}
