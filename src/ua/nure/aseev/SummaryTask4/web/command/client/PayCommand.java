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
import ua.nure.aseev.SummaryTask4.utils.MailNotification;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class PayCommand extends Command {

	private static final long serialVersionUID = 1900;

	private static final Logger LOG = Logger.getLogger(PayCommand.class);

	private static OrderDAO orderDao;

	private static RoomsDAO roomsDao;

	public PayCommand() {
		orderDao = new OrderDAO();
		roomsDao = new RoomsDAO();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.trace("PayCommand starts");

		HttpSession sesiion = request.getSession();
		User user = (User) sesiion.getAttribute("user");

		if (user == null) {
			request.setAttribute("errorMessage", Messages.ERR_PLEASE_LOGIN);
			return Path.PAGE_ERROR_ORDER;
		}

		int orderId = Integer.parseInt(request.getParameter("orderId"));
		LOG.trace("Request get parameter orderId --> " + orderId);

		boolean flag = orderDao.updatePayedStatusOrder(orderId);
		LOG.trace("Update `payed_status` to True --> " + flag);

		String roomNumber = request.getParameter("roomNumber");
		LOG.trace("Request get parameter roomNumber --> " + roomNumber);

		if (flag) {
			boolean flag1 = roomsDao.updateRoomStatusToBooked(roomNumber);
			LOG.trace("Update `room_status` to `Booked` --> " + flag1);
			payOrder(user);

		}
		PersonalOfficeCommand table = new PersonalOfficeCommand();
		String forward = table.createPresonalOffice(request);

		LOG.trace("PayCommand finished");
		return forward;
	}

	static void payOrder(User user) {
		MailNotification mail = new MailNotification();
		boolean flag2 = mail.send("Pay your Order", "Congratulations you payed your order", user.getMail());
		LOG.trace("Send mail --> " + flag2);
	}
}
