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
import ua.nure.aseev.SummaryTask4.exception.Messages;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class ListRoomCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger.getLogger(ListRoomCommand.class);

	private static RoomsDAO roomsDao;

	public ListRoomCommand() {
		roomsDao = new RoomsDAO();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		LOG.trace("user -->" + user);

		if (user == null) {
			request.setAttribute("errorMessage", Messages.ERR_CANNOT_INCORRECT_PASS);
			return Path.PAGE_ERROR_ORDER;
		}

		Role userRole = Role.getRole(user);
		LOG.trace("user Role --> " + userRole);

		List<Room> room = roomsDao.findRooms();
		LOG.trace("Found in DB: Rooms List --> " + room);

		request.setAttribute("room", room);
		LOG.trace("Set the request attribute: room --> " + room);

		LOG.debug("Command finished");
		return Path.PAGE_LIST_MENU;
	}

}
