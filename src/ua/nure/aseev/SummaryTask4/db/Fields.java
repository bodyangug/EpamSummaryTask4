package ua.nure.aseev.SummaryTask4.db;

/**
 * Holder for fields names of DB tables and beans.
 * 
 */
public class Fields {

	// Entity
	public static final String ENTITY_ID = "id";
	// Entity User
	public static final String USER_LOGIN = "login";
	public static final String USER_MAIL = "mail";
	public static final String USER_PASSWORD = "pass";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_ROLE_ID = "role";
	// Entity Room
	public static final String ROOM_NUMBER = "number";
	public static final String ROOM_STATUS = "status";
	public static final String ROOM_NUMBER_OF_PLACES = "number_of_places";
	public static final String ROOM_CLASS_APARTMENT = "class_apartment";
	public static final String ROOM_PRICE = "price";
	// Entity Request
	public static final String REQUEST_USER_ID = "id_user";
	public static final String REQUEST_USER_LOGIN = "login_user";
	public static final String REQUEST_ROOM_NUMBER = "room_number";
	public static final String REQUEST_NUMBER_OF_PLACES = "number_of_places";
	public static final String REQUEST_CLASS_APARTMENT = "class_apartment";
	public static final String REQUEST_PRICE = "price";
	public static final String REQUEST_TIME_START = "time_start";
	public static final String REQUEST_TIME_END = "time_end";
	public static final String REQUEST_AGREE = "agree";
	public static final String REQUEST_WAIT = "wait";
	public static final String REQUEST_OPERATION = "operation";
	public static final String REQUEST_PAYED_STATUS = "payed_status";
	public static final String REQUEST_SUCSESS = "sucsess";
	// Entity Bean
	public static final String BEAN_FIRST_NAME = "first_name";
	public static final String BEAN_LAST_NAME = "last_name";
	public static final String BEAN_ROOM_NUMBER = "room_number";
	public static final String BEAN_NUMBER_OF_PLACES = "number_of_places";
	public static final String BEAN_CLASS_APARTMENT = "class_apartment";
	public static final String BEAN_TIME_START = "time_start";
	public static final String BEAN_TIME_END = "time_end";
	public static final String BEAN_PRICE = "price";
	public static final String BEAN_OPERATION = "operation";
	public static final String BEAN_PAYED_STATUS = "payed_status";

}
