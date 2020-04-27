package ua.nure.aseev.SummaryTask4.web.command.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.entity.Room;
import ua.nure.aseev.SummaryTask4.db.entity.UserOrderBean;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class ShowOrderCommand extends Command {

	private static final long serialVersionUID = 1320;

	private static final Logger LOG = Logger.getLogger(ShowOrderCommand.class);

	private static RoomsDAO roomsDao;

	private static OrderDAO orderDao;

	public ShowOrderCommand() {
		roomsDao = new RoomsDAO();
		orderDao = new OrderDAO();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("ShowOrderCommand starts");

		HttpSession session = request.getSession();

		Long id = Long.parseLong(request.getParameter("id"));
		LOG.trace("Request parameter: id --> " + id);

		UserOrderBean order = orderDao.findOneUserOwnOrder(id);
		LOG.trace("DB: Find userOwnOrder --> " + order);

		List<Room> room = roomsDao.findFreeRooms();
		LOG.trace("DB: Find in DB Free Rooms --> " + room);

		request.setAttribute("room", room);
		LOG.trace("Set the request attribute: room --> " + room);

		request.setAttribute("order", order);
		LOG.trace("Set the request attribute: order --> " + order);

		session.setAttribute("orderBean", order);
		LOG.trace("Set the session attribute: order --> " + order);

		session.setAttribute("idOrderUser", id);
		LOG.trace("Set the session attribute: idOrderUser --> " + id);

		LOG.debug("ShowOrderCommand finished");
		return Path.PAGE_SHOW_ORDER_MANAGER;
	}

}
