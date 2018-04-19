package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import base.DBManager;
import beans.BuyDataBeans;
import beans.ItemDataBeans;

public class BuyDAO {
	//インスタンスオブジェクトを返却させてコードの簡略化
	public static BuyDAO getInstance() {
		return new BuyDAO();
	}

	/**
	 * 購入情報登録処理
	 * @param bdb 購入情報
	 * @throws SQLException 呼び出し元にスローさせるため
	 */
	public static int insertBuy(BuyDataBeans bdb) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		int autoIncKey = -1;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"INSERT INTO t_buy(user_id,total_price,delivery_method_id,create_date) VALUES(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, bdb.getUserId());
			st.setInt(2, bdb.getTotalPrice());
			st.setInt(3, bdb.getDelivertMethodId());
			st.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			st.executeUpdate();

			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				autoIncKey = rs.getInt(1);
			}
			System.out.println("inserting buy-datas has been completed");

			return autoIncKey;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	/**
	 * 購入IDによる購入情報検索
	 * @param buyId
	 * @return BuyDataBeans
	 * 				購入情報のデータを持つJavaBeansのリスト
	 * @throws SQLException
	 * 				呼び出し元にスローさせるため
	 */
	public static BuyDataBeans getBuyDataBeansByBuyId(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT * FROM t_buy"
							+ " JOIN m_delivery_method"
							+ " ON t_buy.delivery_method_id = m_delivery_method.id"
							+ " WHERE t_buy.id = ?");
			st.setInt(1, buyId);

			ResultSet rs = st.executeQuery();

			BuyDataBeans bdb = new BuyDataBeans();
			if (rs.next()) {
				bdb.setId(rs.getInt("id"));
				bdb.setTotalPrice(rs.getInt("total_price"));
				bdb.setBuyDate(rs.getTimestamp("create_date"));
				bdb.setDelivertMethodId(rs.getInt("delivery_method_id"));
				bdb.setUserId(rs.getInt("user_id"));
				bdb.setDeliveryMethodPrice(rs.getInt("delivery_price"));
				bdb.setDeliveryMethodName(rs.getString("delivery_name"));
			}

			System.out.println("searching BuyDataBeans by buyID has been completed");

			return bdb;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static List<BuyDataBeans> buyData(int userId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		List <BuyDataBeans> buyDataList = new ArrayList <BuyDataBeans>();
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT * FROM t_buy"
							+ " INNER JOIN m_delivery_method"
							+ " ON t_buy.delivery_method_id = m_delivery_method.id"
							+ " WHERE t_buy.user_id = ?"
							+ " ORDER BY create_date DESC");
			st.setInt(1, userId);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int totalPrice = rs.getInt("total_price");
				Timestamp buyDate = rs.getTimestamp("create_date");
				int deliveMethodid = rs.getInt("delivery_method_id");
				int UserId = rs.getInt("user_id");
				int deliveryMethodPrice = rs.getInt("delivery_price");
				String deliveryMethodName = rs.getString("delivery_name");

				BuyDataBeans bdb = new BuyDataBeans(id,totalPrice,deliveMethodid,buyDate,deliveryMethodName);
				buyDataList.add(bdb);
			}

			return buyDataList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static  List<ItemDataBeans> findBuyData(int buyId) throws SQLException{
		Connection con = null;
		PreparedStatement st = null;
		List <ItemDataBeans> buyHistoryList = new ArrayList <ItemDataBeans>();

		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT * FROM t_buy_detail"
							+ " INNER JOIN m_item"
							+ " ON t_buy_detail.item_id = m_item.id"
							+ " INNER JOIN t_buy"
							+ " ON t_buy_detail.buy_id = t_buy.id"
							+ " JOIN m_delivery_method"
							+ " ON t_buy.delivery_method_id = m_delivery_method.id"
							+ " WHERE t_buy_detail.buy_id = ?");


			st.setInt(1, buyId);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int inputBuyId = rs.getInt("buy_id");
				int inputItemId = rs.getInt("item_id");
				String inputName = rs.getString("name");
				int inputPrice = rs.getInt("price");

				ItemDataBeans idb = new ItemDataBeans(inputBuyId,inputItemId,inputName,inputPrice);
				buyHistoryList.add(idb);
			}

			return buyHistoryList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	public static BuyDataBeans findData(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		List <BuyDataBeans> buyDataList = new ArrayList <BuyDataBeans>();
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"SELECT * FROM t_buy"
							+ " INNER JOIN m_delivery_method"
							+ " ON t_buy.delivery_method_id = m_delivery_method.id"
							+ " WHERE t_buy.id = ?");

			st.setInt(1, buyId);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int totalPrice = rs.getInt("total_price");
				Timestamp buyDate = rs.getTimestamp("create_date");
				int deliveMethodid = rs.getInt("delivery_method_id");
				int UserId = rs.getInt("user_id");
				int deliveryMethodPrice = rs.getInt("delivery_price");
				String deliveryMethodName = rs.getString("delivery_name");

				BuyDataBeans bdb = new BuyDataBeans(buyDate,deliveryMethodName,totalPrice);
				return bdb;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return null;
	}

	public static BuyDataBeans findDelivery(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		List <BuyDataBeans> buyDeliveryList = new ArrayList <BuyDataBeans>();
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"SELECT * FROM t_buy_detail"
							+ " INNER JOIN m_item"
							+ " ON t_buy_detail.item_id = m_item.id"
							+ " INNER JOIN t_buy"
							+ " ON t_buy_detail.buy_id = t_buy.id"
							+ " JOIN m_delivery_method"
							+ " ON t_buy.delivery_method_id = m_delivery_method.id"
							+ " WHERE t_buy_detail.buy_id = ?");

			st.setInt(1, buyId);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int deliveryMethodPrice = rs.getInt("delivery_price");
				String deliveryMethodName = rs.getString("delivery_name");

				BuyDataBeans bdb = new BuyDataBeans(deliveryMethodPrice,deliveryMethodName);
				return bdb;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return null;
	}


}
