package ua.nure.aseev.SummaryTask4.web.command.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.entity.Room;
import ua.nure.aseev.SummaryTask4.db.entity.UserOrderBean;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class SelectionCommand extends Command {

	private static final long serialVersionUID = 1831;

	private static final Logger LOG = Logger.getLogger(SelectionCommand.class);

	private static final String DEFAULT = "default";

	private static RoomsDAO roomsDao;

	public SelectionCommand() {
		roomsDao = new RoomsDAO();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("SelectionCommand starts");
		HttpSession session = request.getSession();

		String number = request.getParameter("place");
		LOG.trace("Request parameter: number --> " + number);

		String classApartment = request.getParameter("class");
		LOG.trace("Request parameter: class --> " + classApartment);

		UserOrderBean order = (UserOrderBean) session.getAttribute("orderBean");
		LOG.trace("Session parameter: orderBean" + order);

		if (number.equals(DEFAULT) && classApartment.equals(DEFAULT)) {
			List<Room> room = roomsDao.findFreeRooms();
			LOG.trace("DB: Find Free rooms --> " + room);

			setRequestRoom(request, room);
		} else if (classApartment.equals(DEFAULT)) {
			List<Room> room = roomsDao.selectionByNumber(number);
			LOG.trace("DB: Find in DB rooms By NumderOfPlaces --> " + room);

			setRequestRoom(request, room);

		} else {
			List<Room> room = roomsDao.selectionByClass(classApartment);
			LOG.trace("DB: Find in DB rooms By ClassApartment --> " + classApartment);

			setRequestRoom(request, room);

		}

		request.setAttribute("order", order);
		LOG.debug("SelectionCommand finished");
		return Path.PAGE_SHOW_ORDER_MANAGER;
	}

	private void setRequestRoom(HttpServletRequest request, List<Room> room) {
		request.setAttribute("room", room);
		LOG.trace("Set the request attribute: room --> " + room);

	}
}
