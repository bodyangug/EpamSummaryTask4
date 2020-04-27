package ua.nure.aseev.SummaryTask4.web.command.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.entity.OwnRequest;
import ua.nure.aseev.SummaryTask4.db.entity.Request;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class PersonalOfficeCommand extends Command {

	private static final long serialVersionUID = 2320;

	private static final Logger LOG = Logger.getLogger(PersonalOfficeCommand.class);

	private static OrderDAO orderDao = new OrderDAO();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("PersonalOfficeCommand starts");
		String forward = createPresonalOffice(request);

		LOG.trace("PersonalOfficeCommand finished");
		return forward;
	}

	String createPresonalOffice(HttpServletRequest request) throws DBException {
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		Long id = user.getId();
		LOG.trace("Request parameter:  id_user --> " + id);

		if (id == null) {
			String message = "There isn`t login like " + id + ". Please , register";
			request.setAttribute("errorMessage", message);
			request.setAttribute("forward", false);
			return Path.PAGE_ERROR_PAGE;
		}

		List<Request> req = orderDao.findUserRequest(id);
		LOG.trace("Found in DB: Orders List --> " + req);

		List<OwnRequest> ownReq = (orderDao.findOwnUserRequest(id));

		LOG.trace("Found in DB: OwnOrders List --> " + ownReq);

		request.setAttribute("req", req);
		LOG.trace("Set the request attribute: room --> " + req);

		request.setAttribute("ownReq", ownReq);
		LOG.trace("Set the request attribute: ownReq --> " + ownReq);

		return Path.PAGE_PERSONAL_OFFICE;
	}
}
