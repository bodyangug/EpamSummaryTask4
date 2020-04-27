package ua.nure.aseev.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.Role;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;

public class ErrorPageCommand extends Command {

	private static final long serialVersionUID = 1951;

	private static final Logger LOG = Logger.getLogger(ErrorPageCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("ErrorPageCommand starts");

		HttpSession session = request.getSession();

		String forward = Path.COMMAND_LOGOUT;

		User user = (User) session.getAttribute("user");
		LOG.trace("Get session parameter --> " + user);
		if (user != null) {
			Role role = Role.getRole(user);
			LOG.trace("User role -->" + role);
			if (role == Role.CLIENT) {
				forward = Path.COMMAND_LIST_MENU;
			} else if (role == Role.MANAGER) {
				forward = Path.COMMAND_CHANGE_ROOM_STATUS;
			}
		}

		LOG.debug("ErrorPageCommand finished");
		return forward;
	}

}
