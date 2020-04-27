package ua.nure.aseev.SummaryTask4.db.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * OwnRequest entity.
 * 
 * @author B.Aseev
 * 
 */
public class OwnRequest extends Entity {

	private static final long serialVersionUID = 1549;

	private int idUser;

	private int numberOfPlaces;

	private String classApartment;

	private String timeStart;

	private String timeEnd;

	private boolean payedStatus;

	private boolean agree;

	private boolean wait;

	private String userFirstName;

	private String userLastName;

	private int roomNumber;

	private boolean operation;

	@Override
	public String toString() {
		return "[idUser=" + idUser + ", numberOfPlaces=" + numberOfPlaces + ", classApartment=" + classApartment
				+ ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + ", payedStatus=" + payedStatus + ", agree="
				+ agree + ", wait=" + wait + ", userFirstName=" + userFirstName + ", userLastName=" + userLastName
				+ ", roomNumber=" + roomNumber + ", operation=" + operation + "]";
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
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

	public boolean isPayedStatus() {
		return payedStatus;
	}

	public void setPayedStatus(boolean payedStatus) {
		this.payedStatus = payedStatus;
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

	public boolean isOperation() {
		return operation;
	}

	public void setOperation(boolean operation) {
		this.operation = operation;
	}

}
