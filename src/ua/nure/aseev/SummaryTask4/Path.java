package ua.nure.aseev.SummaryTask4;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author B.Aseev
 * 
 */
public class Path {

	private Path() {
		// no op
	}

	// pages
	public static final String PAGE_LOGIN = "/login.jsp";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_ERROR_ORDER = "/WEB-INF/jsp/error_order.jsp";
	public static final String PAGE_LIST_MENU = "/WEB-INF/jsp/client/list_menu.jsp";
	public static final String PAGE_LIST_ORDERS = "/WEB-INF/jsp/manager/list_orders.jsp";
	public static final String PAGE_SETTINGS = "/WEB-INF/jsp/settings.jsp";
	public static final String PAGE_PERSONAL_OFFICE = "/WEB-INF/jsp/client/personal_office.jsp";
	public static final String PAGE_MAKE_OWN_ORDER = "/WEB-INF/jsp/client/request.jsp";
	public static final String PAGE_QUICK_ORDERS = "/WEB-INF/jsp/manager/quick_order.jsp";
	public static final String PAGE_PERSONAL_ORDERS = "/WEB-INF/jsp/manager/personal_order.jsp";
	public static final String PAGE_SHOW_ORDER_MANAGER = "/WEB-INF/jsp/manager/show_order.jsp";
	public static final String PAGE_LOOK_ORDER_CLIENT = "/WEB-INF/jsp/client/look_order.jsp";
	public static final String PAGE_CHANGE_ROOM_STATUS = "/WEB-INF/jsp/manager/change_status_room.jsp";

	// commands
	public static final String COMMAND_CHANGE_ROOM_STATUS = "/controller?command=changeStatusRoom";
	public static final String COMMAND_LIST_MENU = "/controller?command=listMenu";
	public static final String COMMAND_LOGOUT = "/controller?command=logout";

}
