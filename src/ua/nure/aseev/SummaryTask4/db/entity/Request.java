package ua.nure.aseev.SummaryTask4.db.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Request entity.
 * 
 * @author B.Aseev
 * 
 */
public class Request extends Entity {

	private static final long serialVersionUID = 1507;

	private int idUser;

	private int roomNumber;

	private int numberOfPlaces;

	private String classApartment;

	private int price;

	private String timeStart;

	private String timeEnd;

	private boolean sucsess;

	private boolean payedStatus;

	private boolean agree;

	private boolean wait;

	private boolean opearation;

	@Override
	public String toString() {
		return "[idUser=" + idUser + ", roomNumber=" + roomNumber + ", numberOfPlaces=" + numberOfPlaces
				+ ", classApartment=" + classApartment + ", price=" + price + ", timeStart=" + timeStart + ", timeEnd="
				+ timeEnd + ", sucsess=" + sucsess + ", payedStatus=" + payedStatus + ", agree=" + agree + ", wait="
				+ wait + "]";
	}

	public boolean isOpearation() {
		return opearation;
	}

	public void setOpearation(boolean opearation) {
		this.opearation = opearation;
	}

	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
	}

	public boolean isWait() {
		return wait;
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public boolean isSucsess() {
		return sucsess;
	}

	public void setSucsess(boolean sucsess) {
		this.sucsess = sucsess;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int loginUser) {
		this.idUser = loginUser;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public boolean isPayedStatus() {
		return payedStatus;
	}

	public void setPayedStatus(boolean payedStatus) {
		this.payedStatus = payedStatus;
	}

}
