package ua.nure.aseev.SummaryTask4.web.command;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.Role;
import ua.nure.aseev.SummaryTask4.db.UserDAO;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.exception.Messages;

public class LoginCommand extends Command {

	private static final long serialVersionUID = 1223;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	private static UserDAO userDao;

	public LoginCommand() {
		try {
			userDao = new UserDAO();
		} catch (DBException e) {
			LOG.error(e);
			e.printStackTrace();

		}
	}

	public LoginCommand(UserDAO userDAO) {
		this.userDao = userDAO;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();

		String forward = Path.PAGE_ERROR_ORDER;

		// obtain login and password from a request
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);

		String password = request.getParameter("password");
		LOG.trace("Request parametr: password --> " + password);

		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			request.setAttribute("errorMessage", Messages.ERR_INCORECT_PASS_OR_LOGIN);
			request.setAttribute("forward", false);
			return forward;

		}

		User user = userDao.findUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !password.equals(user.getPassword())) {
			request.setAttribute("errorMessage", Messages.ERR_CANNOT_INCORRECT_PASS);
			request.setAttribute("forward", false);
			return forward;
		}

		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);

		if (userRole == Role.MANAGER) {
			forward = Path.COMMAND_CHANGE_ROOM_STATUS;
		}
		if (userRole == Role.CLIENT) {
			forward = Path.PAGE_MAKE_OWN_ORDER;
			setMinValueTime(session);
			setMaxValueTime(session);
		}

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("login_user", user.getLogin());
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		LOG.debug("Command finished");
		return forward;

	}

	private void setMinValueTime(HttpSession session) {
		Date date = new Date();
		SimpleDateFormat minTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		String minTime = minTimeFormat.format(date.getTime());
		session.setAttribute("minTime", minTime);

	}

	private void setMaxValueTime(HttpSession session) {
		Date date = new Date();
		SimpleDateFormat dd = new SimpleDateFormat("dd");
		SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyy-MM");

		StringBuilder time = new StringBuilder();
		time.append(yyyyMM.format(date.getTime())).append("-");

		int day = Integer.parseInt(dd.format(date.getTime()));
		int nextDay = day + 1;
		time.append(nextDay);

		session.setAttribute("maxTime", time.toString());

	}

}
