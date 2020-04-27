package ua.nure.aseev.SummaryTask4.web.command.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.Messages;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class PayManagerCommand extends Command {

	private static final long serialVersionUID = 1757;

	private static final Logger LOG = Logger.getLogger(PayManagerCommand.class);

	private static OrderDAO orderDao;

	private static RoomsDAO roomsDao;

	public PayManagerCommand() {
		orderDao = new OrderDAO();
		roomsDao = new RoomsDAO();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.trace("PayManagerCommand starts");

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user == null) {
			request.setAttribute("errorMessage", Messages.ERR_PLEASE_LOGIN);
			return Path.PAGE_ERROR_ORDER;
		}

		int orderId = Integer.parseInt(request.getParameter("orderId"));
		LOG.trace("Request get parametr: orderId -->" + orderId);

		boolean flag = orderDao.updatePayedStatusManagerOrder(orderId);
		LOG.trace("Update `payed_status` to `True` --> " + flag);

		String numberRoom = request.getParameter("roomNumber");
		LOG.trace("Request get parametr: roomNumber -->" + numberRoom);

		boolean flag1 = roomsDao.updateRoomStatusToBooked(numberRoom);
		LOG.trace("Update `room_status` to `Booked` --> " + flag1);

		if (flag1 && flag) {
			PayCommand.payOrder(user);
		}
		PersonalOfficeCommand table = new PersonalOfficeCommand();

		String forward = table.createPresonalOffice(request);

		LOG.trace("PayManagerCommand finished");
		return forward;
	}

}
