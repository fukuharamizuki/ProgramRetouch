package ec;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int buyId = Integer.parseInt(request.getParameter("buy_id"));

		BuyDAO buyDao = new BuyDAO();

		try {
			BuyDataBeans bdb = BuyDAO.findData(buyId);

			request.setAttribute("bdb", bdb);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			BuyDataBeans bdb2 = BuyDAO.findDelivery(buyId);

			request.setAttribute("bdb2", bdb2);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}



		List <ItemDataBeans> buyHistoryList;
		try {
			buyHistoryList = BuyDAO.findBuyData(buyId);

			request.setAttribute("buyHistoryList", buyHistoryList);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);

	}
}
