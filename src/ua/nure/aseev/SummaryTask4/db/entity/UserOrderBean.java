package ua.nure.aseev.SummaryTask4.db.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * UserOrderBean entity.
 * 
 * @author B.Aseev
 * 
 */
public class UserOrderBean extends Entity {

	private static final long serialVersionUID = 1233;

	private String userFirstName;

	private String userLastName;

	private int roomNumber;

	private int numberOfPlaces;

	private String classApartment;

	private String timeStart;

	private String timeEnd;

	private int price;

	private boolean payedStatus;

	private boolean wait;

	private boolean operation;

	private boolean agree;

	@Override
	public String toString() {
		return "[userFirstName=" + userFirstName + ", userLastName=" + userLastName + ", roomNumber=" + roomNumber
				+ ", numberOfPlaces=" + numberOfPlaces + ", classApartment=" + classApartment + ", timeStart="
				+ timeStart + ", timeEnd=" + timeEnd + ", price=" + price + ", payedStatus=" + payedStatus + ", wait="
				+ wait + ", operation=" + operation + ", agree=" + agree + "]";
	}

	public boolean isOperation() {
		return operation;
	}

	public void setOperation(boolean operation) {
		this.operation = operation;
	}

	public boolean isWait() {
		return wait;
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getNumberOfPlaces() {
		return numberOfPlaces;
	}

	public void setNumberOfPlaces(int numberOfPlaces) {
		this.numberOfPlaces = numberOfPlaces;
	}

	public String getClassApartment() {
		return classApartment;
	}

	public void setClassApartment(String classApartment) {
		this.classApartment = classApartment;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Timestamp timeStart) {
		Date newD = new Date(timeStart.getTime());
		this.timeStart = dateFormat.format(newD);
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Timestamp timeEnd) {
		Date newD = new Date(timeEnd.getTime());
		this.timeEnd = dateFormat.format(newD);
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isPayedStatus() {
		return payedStatus;
	}

	public void setPayedStatus(boolean payedStatus) {
		this.payedStatus = payedStatus;
	}
}
