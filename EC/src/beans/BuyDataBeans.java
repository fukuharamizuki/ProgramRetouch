package beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyDataBeans  implements Serializable {
	private int id;
	private int userId;
	private int totalPrice;
	private int delivertMethodId;
	private Date buyDate;

	private String deliveryMethodName;
	private int deliveryMethodPrice;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}


	public int getDelivertMethodId() {
		return delivertMethodId;
	}
	public void setDelivertMethodId(int delivertMethodId) {
		this.delivertMethodId = delivertMethodId;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public String getDeliveryMethodName() {
		return deliveryMethodName;
	}
	public void setDeliveryMethodName(String deliveryMethodName) {
		this.deliveryMethodName = deliveryMethodName;
	}

	public String getFormatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH時mm分");
		return sdf.format(buyDate);
	}
	public int getDeliveryMethodPrice() {
		return deliveryMethodPrice;
	}
	public void setDeliveryMethodPrice(int deliveryMethodPrice) {
		this.deliveryMethodPrice = deliveryMethodPrice;
	}
	public BuyDataBeans(int id, int total, Date buyDate, String deliveryMethodName) {
		this.id =id;
		this.totalPrice = total;
		this.buyDate = buyDate;
		this.deliveryMethodName = deliveryMethodName;
	}
	public BuyDataBeans(int deliveryMethodPrice2, String deliveryMethodName2) {
		this.deliveryMethodName = deliveryMethodName2;
		this.deliveryMethodPrice = deliveryMethodPrice2;
	}
	public BuyDataBeans(Timestamp buyDate2, String deliveryMethodName2, int total) {
		this.buyDate = buyDate2;
		this.deliveryMethodName = deliveryMethodName2;
		this.totalPrice = total;
	}
	public BuyDataBeans(int id, int total, int deliveMethodid, Timestamp buyDate, String deliveryMethodName) {
		this.id = id;
		this.totalPrice = total;
		this.delivertMethodId = deliveMethodid;
		this.deliveryMethodName = deliveryMethodName;
		this.buyDate = buyDate;
	}
	public BuyDataBeans() {
	}


}
