package ua.nure.aseev.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;

public class LogoutCommand extends Command {

	private static final long serialVersionUID = -2785976616686657267L;

	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("LogoutCommand starts");

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		LOG.debug("LogoutCommand finished");
		return Path.PAGE_LOGIN;
	}

}
