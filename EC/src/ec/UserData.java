package ec;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.UserDataBeans;
import dao.BuyDAO;
import dao.UserDAO;

/**
 * ユーザー情報画面
 *
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserData")
public class UserData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッション開始
		HttpSession session = request.getSession();
		try {
			// ログイン時に取得したユーザーIDをセッションから取得
			int userId = (int) session.getAttribute("userId");
			// 更新確認画面から戻ってきた場合Sessionから取得。それ以外はuserIdでユーザーを取得
			UserDataBeans udb = session.getAttribute("returnUDB") == null ? UserDAO.getUserDataBeansByUserId(userId) : (UserDataBeans) EcHelper.cutSessionAttribute(session, "returnUDB");



			// 入力された内容に誤りがあったとき等に表示するエラーメッセージを格納する
			String validationMessage = (String) EcHelper.cutSessionAttribute(session, "validationMessage");


			request.setAttribute("validationMessage", validationMessage);
			request.setAttribute("udb", udb);


			BuyDAO buyDao = new BuyDAO();
			List<BuyDataBeans> buyDataList;
			try {
				buyDataList = BuyDAO.buyData(userId);
				request.setAttribute("buyDataList", buyDataList);
				BuyDataBeans bdl = (BuyDataBeans) session.getAttribute("buyDataList");

			} catch (SQLException e) {
				e.printStackTrace();
			}



			request.getRequestDispatcher(EcHelper.USER_DATA_PAGE).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}


	}

}
