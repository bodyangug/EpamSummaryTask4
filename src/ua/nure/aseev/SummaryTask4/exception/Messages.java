package ua.nure.aseev.SummaryTask4.exception;

/**
 * An exception that provides information on an application error.
 * 
 * @author B.Aseev
 */

public final class Messages {

	private Messages() {
		// no op
	}

	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";

	public static final String ERR_CANNOT_INCORRECT_PASS = "Cannot find user with such login/password";

	public static final String ERR_CANNOT_PARSE_TIME = "Cannot parse time";

	public static final String ERR_INCORECT_PASS_OR_LOGIN = "Incorret password/login";

	public static final String ERR_EMPTY_DATE = "Choose Date";

	public static final String ERR_PLEASE_LOGIN = "Please login again!";

	public static final String ERR_FILL_ALL_FIELDS = "Please fill all fields";

	public static final String ERR_TO_FIELDS = "Error to update/insert DB fields";

	public static final String ERR_ONE_OWN_ORDER = "You cannot make more than one OwnOrders";

	public static final String ERR_CREATE_NEW_LOGIN = "This login is occupied";

	public static final String ERR_CREATE_NEW_MAIL = "This mail is occupied";
//	////////////////////////////////////////
	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";

	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";

	public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";

	public static final String ERR_CANNOT_CREATE_USER = "Cannot create user";

	public static final String ERR_CANNOT_FIND_USER_BY_LOGIN = "Cannot find a user by its login";

	public static final String ERR_CANNOT_OBTAIN_MENU_ROOMS = "Cannot obtain rooms";

	public static final String ERR_CANNOT_CHECK_ROOM_STATUS = "Cannot check room status";

	public static final String ERR_NUMBER_ROOM_NULL = "Please choose your order";

	public static final String ERR_CANNOT_MAKE_ROLLBACK = "Cannot make rollback";

	public static final String ERR_CANNOT_SORT_ROOM = "Cannot sort rooms";

	public static final String ERR_CANNOT_FIND_ROOM_BY_NUMBER = "Cannot find room by number";

	public static final String ERR_INSERT_REQUSEST = "Cannot insert your Request";

	public static final String ERR_CANNOT_UPDATE_STATUS = "Cannot update status";

	public static final String ERR_CANNOT_UPDATE_ROOM_STATUS = "Cannot update room status";

	public static final String ERR_CANNOT_FIND_USER_ORDER = "Cannot find user order";

	public static final String ERR_CANNOT_UPDATE_ORDER_SUCSESS = "Cannot update order sucsess";

	public static final String ERR_CANNOT_MAKE_SELECTION = "Cannot make selection";

	public static final String ERR_CANNOT_INSER_ORDER = "Cannot insert your order";

	public static final String ERR_CANNOT_UPDATE_OPERATION = "Cannot update operation";

	public static final String ERR_CANNOT_UPDATE_WAIT_STATUS = "Cannot update wait status";

	public static final String ERR_CANNOT_UPDATE_AGREE = "Cannot update agree";

	public static final String ERR_CANNOT_DELETE_ORDER = "Cannot delete order";

	public static final String ERR_CANNOT_UPDATE_PAYED_STATUS = "Cannot update payed status";

	public static final String ERR_CHOOSE_ANOTHER_ROOM = "Please choose another room";

	public static final String ERR_CANNOT_FIND_USER_BY_ID = "Cannot find user by id";

	public static final String ERR_CANNOT_GET_USER_LOGIN = "Cannot get user login and mail";

}
