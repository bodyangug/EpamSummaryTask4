package ua.nure.aseev.SummaryTask4.db.entity;

/**
 * Room entity.
 * 
 * @author B.Aseev
 * 
 */

public class Room extends Entity {

	private static final long serialVersionUID = 1249;

	private int number;

	private int status;

	private int numberOfPlace;

	private String classApartment;

	private int price;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNumberOfPlace() {
		return numberOfPlace;
	}

	public void setNumberOfPlace(int numberOfPlace) {
		this.numberOfPlace = numberOfPlace;
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

	@Override
	public String toString() {
		return "[number=" + number + ", status=" + status + ", numberOfPlace=" + numberOfPlace + ", classApartment="
				+ classApartment + ", price=" + price + "]";
	}

}
