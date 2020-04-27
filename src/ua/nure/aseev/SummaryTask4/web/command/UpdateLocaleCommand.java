package ua.nure.aseev.SummaryTask4.web.command;

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

public class UpdateLocaleCommand extends Command {

	private static final long serialVersionUID = 1314;

	private static final Logger LOG = Logger.getLogger(UpdateLocaleCommand.class);

	private static RoomsDAO roomsDao;

	public UpdateLocaleCommand() {
		this.roomsDao = new RoomsDAO();
	}

	public UpdateLocaleCommand(RoomsDAO roomsDao) {
		this.roomsDao = roomsDao;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("UpdateLocaleCommand starts");

		HttpSession session = request.getSession();
		String forward = null;
		User user = (User) session.getAttribute("user");
		LOG.trace("Get session parameter --> " + user);
		if (user == null) {
			request.setAttribute("errorMessage", Messages.ERR_PLEASE_LOGIN);
			return Path.PAGE_ERROR_ORDER;
		}

		Role role = Role.getRole(user);
		if (role == Role.CLIENT) {
			forward = Path.COMMAND_LIST_MENU;

		} else if (role == Role.MANAGER) {
			forward = Path.COMMAND_CHANGE_ROOM_STATUS;
		}

		String locale = request.getParameter("locale");
		LOG.trace("Get request parameter --> " + locale);

		session.setAttribute("locale", locale);

		List<Room> room = roomsDao.findRooms();

		request.setAttribute("room", room);
		LOG.trace("Set the request attribute: room --> " + room);

		LOG.debug("UpdateLocaleCommand finished");
		return forward;
	}

}
