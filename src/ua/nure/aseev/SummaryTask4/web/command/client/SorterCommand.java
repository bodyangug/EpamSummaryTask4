package ua.nure.aseev.SummaryTask4.web.command.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.Role;
import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.entity.Room;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.exception.Messages;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class SorterCommand extends Command {

	private static final long serialVersionUID = 1810;

	private static final Logger LOG = Logger.getLogger(SorterCommand.class);

	private static RoomsDAO roomsDao = new RoomsDAO();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("SorterCommand starts");

		HttpSession session = request.getSession();

		List<Room> room = null;

		StringBuilder sorter = new StringBuilder();

		User user = (User) session.getAttribute("user");
		LOG.trace("Get the session atribbute: user -->" + user);

		if (user == null) {
			request.setAttribute("errorMessage", Messages.ERR_PLEASE_LOGIN);
			request.setAttribute("forward", false);
			return Path.PAGE_ERROR_ORDER;
		}

		Role role = Role.getRole(user);
		LOG.trace("User role --> " + user);
		String forward = null;

		if (role == Role.CLIENT) {
			room = sortMethod(request, room, sorter);
			forward = Path.PAGE_LIST_MENU;
		} else if (role == Role.MANAGER) {
			room = sortMethod(request, room, sorter);
			forward = Path.PAGE_CHANGE_ROOM_STATUS;
		}

		request.setAttribute("room", room);
		LOG.trace("Set the request attribute: room --> " + room);

		LOG.debug("SorterCommand finished");
		return forward;

	}

	private List<Room> sortMethod(HttpServletRequest request, List<Room> room, StringBuilder sorter)
			throws DBException {
		List<Room> list = room;
		String sort = request.getParameter("sort");
		LOG.trace("Sort by -->" + sort);
		if (sort == null) {
			list = roomsDao.findRooms();
			LOG.trace("Found in DB: rooms --> " + list);
		} else {
			list = sort(sort, sorter, room);
			LOG.trace("Found in DB: rooms --> " + list);
		}
		return list;
	}

	private List<Room> sort(String sort, StringBuilder sorter, List<Room> list) throws DBException {
		List<Room> room = list;
		switch (sort) {
		case "low-max-price":
			sorter.append("price DESC ;");
			room = roomsDao.sortRoom(sorter.toString());
			break;
		case "max-low-price":
			sorter.append("price ASC ;");
			room = roomsDao.sortRoom(sorter.toString());
			break;
		case "low-max-count":
			sorter.append("number_of_places ASC ;");
			room = roomsDao.sortRoom(sorter.toString());
			break;
		case "max-low-count":
			sorter.append("number_of_places DESC ;");
			room = roomsDao.sortRoom(sorter.toString());
			break;
		case "luxure":
			sorter.append("luxure");
			room = roomsDao.sortRoomClass(sorter.toString());
			break;
		case "standart":
			sorter.append("Standart");
			room = roomsDao.sortRoomClass(sorter.toString());
			break;
		case "medium":
			sorter.append("medium");
			room = roomsDao.sortRoomClass(sorter.toString());
			break;
		case "free":
			sorter.append(0);
			room = roomsDao.sortRoomStatus(sorter.toString());
			break;
		case "booked":
			sorter.append(1);
			room = roomsDao.sortRoomStatus(sorter.toString());
			break;
		case "locked":
			sorter.append(3);
			room = roomsDao.sortRoomStatus(sorter.toString());
			break;
		case "unable":
			sorter.append(4);
			room = roomsDao.sortRoomStatus(sorter.toString());
			break;
		default:
			room = roomsDao.findRooms();
			break;
		}
		return room;
	}
}
