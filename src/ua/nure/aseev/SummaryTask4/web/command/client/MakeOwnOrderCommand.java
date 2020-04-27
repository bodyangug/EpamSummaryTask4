package ua.nure.aseev.SummaryTask4.web.command.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.entity.OwnRequest;
import ua.nure.aseev.SummaryTask4.db.entity.Room;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.exception.Messages;
import ua.nure.aseev.SummaryTask4.utils.MailNotification;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class MakeOwnOrderCommand extends Command {

	private static final long serialVersionUID = 1232;

	private static final Logger LOG = Logger.getLogger(MakeOwnOrderCommand.class);

	private static RoomsDAO roomsDao;

	private static OrderDAO orderDao;

	public MakeOwnOrderCommand() {
		roomsDao = new RoomsDAO();
		orderDao = new OrderDAO();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("MakeOwnOrderCommand starts");

		HttpSession session = request.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();

		User user = (User) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("errorMessage", Messages.ERR_PLEASE_LOGIN);
			request.setAttribute("forward", true);
			return Path.PAGE_ERROR_ORDER;
		}
		Long id = user.getId();
		LOG.trace("Request parameter: id --> " + id);

		String numberOfPlaces = request.getParameter("numberOfPlaces");
		LOG.trace("Request parameter: numberOfPlace --> " + numberOfPlaces);

		String classApartment = request.getParameter("classApartment");
		LOG.trace("Request parameter: classApartment --> " + classApartment);

		String timeStart = request.getParameter("timeStart");
		LOG.trace("Request parameter: timeStart --> " + timeStart);
		String timeEnd = request.getParameter("timeEnd");
		LOG.trace("Request parameter: timeEnd --> " + timeEnd);

		timeStart += " " + sdf.format(date.getTime());
		timeEnd += " " + sdf.format(date.getTime());

		boolean check = checkOwnRequest(id);

		if (!check) {
			request.setAttribute("errorMessage", Messages.ERR_ONE_OWN_ORDER);
			request.setAttribute("forward", true);
			return Path.PAGE_ERROR_ORDER;
		}

		if (numberOfPlaces != null && classApartment != null && check) {
			boolean flag = orderDao.insertOwnRequest(id, numberOfPlaces, classApartment, timeStart, timeEnd);
			LOG.trace("Try to insert own user request --> " + flag);
			MailNotification mail = new MailNotification();
			boolean flag1 = mail.send("You make 'own' order", "Congratulations your order in process, wait..",
					user.getMail());
			LOG.trace("Send mail --> " + flag1);
		} else {
			return Path.PAGE_MAKE_OWN_ORDER;
		}
		List<Room> list = roomsDao.findRooms();
		request.setAttribute("room", list);

		LOG.debug("MakeOwnOrderCommand finished");
		return Path.PAGE_LIST_MENU;
	}

	private boolean checkOwnRequest(Long id) throws DBException {
		List<OwnRequest> ownReq = (orderDao.findOwnUserRequest(id));
		if (ownReq.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}
