package ua.nure.aseev.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.Role;
import ua.nure.aseev.SummaryTask4.db.UserDAO;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.exception.Messages;

public class RegisterCommand extends Command {

	private static final long serialVersionUID = 2120;

	private static final Logger LOG = Logger.getLogger(RegisterCommand.class);

	private static UserDAO userDao;

	public RegisterCommand() {
		try {
			userDao = new UserDAO();
		} catch (DBException e) {
			LOG.error(e);
			e.printStackTrace();
		}
	}

	public RegisterCommand(UserDAO userDAO) {
		userDao = userDAO;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("RegisterCommand starts");

		String firstName = request.getParameter("f_name");
		LOG.trace("Request parameter: first_name --> " + firstName);

		String secondName = request.getParameter("s_name");
		LOG.trace("Request parameter: second_name --> " + secondName);

		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);

		String mail = request.getParameter("mail");
		LOG.trace("Request parameter: mail --> " + mail);

		List<User> listUser = userDao.getUserLoginAndMail();
		LOG.trace("Found in db all User --> " + listUser);
		for (int i = 0; i < listUser.size(); i++) {
			User newUser = listUser.get(i);
			if (login.equals(newUser.getLogin())) {
				request.setAttribute("errorMessage", Messages.ERR_CREATE_NEW_LOGIN);
				return Path.PAGE_ERROR_ORDER;
			}
			if (mail.equals(newUser.getMail())) {
				request.setAttribute("errorMessage", Messages.ERR_CREATE_NEW_MAIL);
				return Path.PAGE_ERROR_ORDER;

			}
		}

		String password = request.getParameter("password");
		LOG.trace("Request parameter: password --> " + password);

		User user = createUser(login, password, firstName, secondName, mail);

		boolean flag = userDao.insertUser(user);
		LOG.trace("Create User in DB: new user --> " + flag);

		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);

		LOG.debug("RegisterCommand finished");
		return Path.PAGE_LOGIN;

	}

	private User createUser(String login, String password, String FirstName, String SecondName, String mail) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setFirstName(FirstName);
		user.setSecondName(SecondName);
		user.setMail(mail);
		user.setRole(1);
		return user;
	}

}
