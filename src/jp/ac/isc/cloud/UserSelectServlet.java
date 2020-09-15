package jp.ac.isc.cloud;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


/**
 * Servlet implementation class UserSelectServlet
 */
@WebServlet("/UserSelectServlet")
public class UserSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Connection users = null;
			try {
				//MySQL用のJDBCドライバーのクラスをロードする
				Class.forName("com.mysql.jdbc.Driver");
				//DB（MySQL)サーバーへの接続のインスタンス（オブジェクト）
				users = DriverManager.getConnection("jdbc:mysql://localhost/servlet_db",
						"root", "");
				//レコードを管理する配列（コレクション）用意
				ArrayList<Member> list = new ArrayList<Member>();
				//SQLを実行するためのオブジェクトstateを用意する
				Statement state = users.createStatement();
				//SELECT（SQLを実行）した結果を入れるオブジェクトresultを用意
				ResultSet result = state.executeQuery("SELECT * FROM user_table");
				//次のデータ取り出し、取り出すデータが無い場合はfalse
				while(result.next()) {
					String id = result.getString("id");
					String name = result.getString("name");
					String picture = result.getString("picture");
					Member member = new Member(id, name, picture);
					list.add(member);
					//list.add(new Member(id, name, picture));
				}
				result.close();	//SQLの結果を受け取ったオブジェクトを閉じる
				state.close();	//SQLを実行するためのオブジェクトを閉じる
				users.close();	//DB接続を閉じる
				request.setAttribute("list", list);
				RequestDispatcher rd =
						getServletContext().getRequestDispatcher("/WEB-INF/select.jsp");
				rd.forward(request, response);
			}catch(ClassNotFoundException e) {	//クラスが無かった例外処理
				e.printStackTrace();
			}
		}catch(SQLException e) {	//SQL実行時の例外処理
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
