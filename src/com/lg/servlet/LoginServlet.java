package com.lg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lg.bean.ResultMsg;
import com.lg.bean.User;
import com.lg.dao.JDBCManager;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		ArrayList<User> users;
		ResultMsg result = new ResultMsg();
		Gson gson = new Gson();
		String resultStr;
		String usercode = request.getParameter("usercode");
		String userpassword = request.getParameter("userpassword");
		String sql = "select usercode, username, openid, userwechat, userphone, useremail,"
				+ " usertype, userlvl, userpoint, totaltimes, resttimes, prepaiddate, usergender,"
				+ " userbirthday, userlocation, userhead, userdesc, userplatform, userpermission"
				+ " from user where userstatus <> 0 and userpassword = ? collate utf8_bin"
				+ " and (usercode = ? collate utf8_bin or userphone = ? or useremail = ? collate utf8_bin)";
		Connection conn = JDBCManager.getInstance().getConncetion();
		try {
			PreparedStatement psm = conn.prepareStatement(sql);
			psm.setString(1, userpassword);
			psm.setString(2, usercode);
			psm.setString(3, usercode);
			psm.setString(4, usercode);

			ResultSet rs = psm.executeQuery();
			users = new ArrayList<>();
			User user;
			while (rs.next()) {
				user = new User();
				user.setUsercode(rs.getString(1));
				user.setUsername(rs.getString(2));
				user.setOpenid(rs.getString(3));
				user.setUserwechat(rs.getString(4));
				user.setUserphone(rs.getString(5));
				user.setUseremail(rs.getString(6));
				user.setUsertype(rs.getInt(7));
				user.setUserlvl(rs.getInt(8));
				user.setUserpoint(rs.getInt(9));
				user.setTotaltimes(rs.getInt(10));
				user.setResttimes(rs.getInt(11));
				user.setPrepaiddate(rs.getString(12));
				user.setUsergender(rs.getInt(13));
				user.setUserbirthday(rs.getString(14));
				user.setUserlocation(rs.getString(15));
				user.setUserhead(rs.getString(16));
				user.setUserdesc(rs.getString(17));
				user.setUserplatform(rs.getInt(18));
				user.setUserpermission(rs.getInt(19));
				users.add(user);
				result.setData(user);
			}
		} catch (Exception e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			result.setSts("login");
			resultStr = gson.toJson(result);
			pw.append(resultStr);
			return;
		}
		if (!users.isEmpty()) {
			// 生成cookie
			String cookieString = UUID.randomUUID().toString();
			Cookie cookie = new Cookie("verify", cookieString);
			response.addCookie(cookie);
			// 将cookie保存到数据库
			String dsql = "delete from cookies where usercode = '" + usercode + "'";
			String isql = "insert into cookies (usercode, cookieverify) values ('" + usercode + "', '" + cookieString
					+ "')";
			try {
				conn.setAutoCommit(false);
				Statement sm = conn.createStatement();
				sm.addBatch(dsql);
				sm.addBatch(isql);
				sm.executeBatch();
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			result.setSuccess(true);
			result.setSts("login");
			resultStr = gson.toJson(result);
			pw.append(resultStr);
		} else {
			result.setMsg("null");
			result.setSuccess(false);
			result.setSts("login");
			resultStr = gson.toJson(result);
			pw.append(resultStr);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
