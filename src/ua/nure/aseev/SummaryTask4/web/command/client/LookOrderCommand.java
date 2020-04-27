package ua.nure.aseev.SummaryTask4.web.command.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.entity.Request;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.db.entity.UserOrderBean;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.exception.Messages;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class LookOrderCommand extends Command {

	private static final long serialVersionUID = 1609;

	private static final Logger LOG = Logger.getLogger(LookOrderCommand.class);

	private static OrderDAO orderDao;

	public LookOrderCommand() {
		orderDao = new OrderDAO();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("LookOrderCommand starts");
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user == null) {
			request.setAttribute("errorMessage", Messages.ERR_PLEASE_LOGIN);
			return Path.PAGE_ERROR_ORDER;
		}
		Long userId = user.getId();
		Long id = Long.parseLong(request.getParameter("id"));
		LOG.trace("Request parameter: id --> " + id);

		createOwnOrder(id, request);

		reloadOrderFromManager(session, userId, request);

		session.setAttribute("userId", id);
		LOG.trace("Set the request attribute: userId --> " + id);

		LOG.debug("LookOrderCommand finished");
		return Path.PAGE_LOOK_ORDER_CLIENT;
	}

	public static UserOrderBean createOwnOrder(Long id, HttpServletRequest request) throws DBException {
		UserOrderBean ownReq = orderDao.findOneUserOwnOrder(id);
		LOG.trace("DB: Find User OwnOrder --> " + ownReq);
		request.setAttribute("own", ownReq);
		LOG.trace("Set the request attribute:  own --> " + ownReq);

		return ownReq;
	}

	public static Request reloadOrderFromManager(HttpSession session, Long id, HttpServletRequest request)
			throws DBException {
		Request req = orderDao.findOrderFromManager(id);
		LOG.trace("DB: Find Order from Manager --> " + req);

		request.setAttribute("manager_order", req);
		LOG.trace("Set the request attribute:  manager_order --> " + req);

		session.setAttribute("requestFromManager", req);
		LOG.trace("Set the session attribute:  requestFromManager --> " + req);

		return req;
	}
}
