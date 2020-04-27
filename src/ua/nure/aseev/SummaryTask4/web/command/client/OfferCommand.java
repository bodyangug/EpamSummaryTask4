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
import ua.nure.aseev.SummaryTask4.exception.Messages;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class OfferCommand extends Command {

	private static final long serialVersionUID = 1515;

	private static final Logger LOG = Logger.getLogger(OfferCommand.class);

	private static OrderDAO orderDao;

	public OfferCommand() {
		orderDao = new OrderDAO();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("OfferCommand starts");
		HttpSession session = request.getSession();
		String vote = request.getParameter("vote");
		LOG.trace("Request parameter: vote --> " + vote);

		Long idOrder = (Long) session.getAttribute("userId");
		LOG.trace("Request parameter: idOrder --> " + idOrder);

		if (vote == null || idOrder == 0) {
			request.setAttribute("errorMessage", Messages.ERR_FILL_ALL_FIELDS);
			request.setAttribute("forward", true);
			return Path.PAGE_ERROR_ORDER;
		}

		User user = (User) session.getAttribute("user");
		Long id = user.getId();

		if (vote.equals("yes")) {
			boolean flag = orderDao.updateWaitStatusToFalse(idOrder);
			LOG.trace("DB: Update `wait` to False --> " + flag);

			boolean flag1 = orderDao.updateRequestAgreeStatusToTrue(idOrder);
			LOG.trace("DB: Update `agree` to True --> " + flag1);
		} else {
			boolean flag = orderDao.deleteRequestFromManager(idOrder);
			LOG.trace("DB: Delete Order from manager --> " + flag);
			boolean flag2 = orderDao.upateOwnRequestOperationToFalse(idOrder);
			LOG.trace("DB: Update Own Request Operetion to False --> " + flag2);
		}

		Request req = LookOrderCommand.reloadOrderFromManager(session, id, request);
		UserOrderBean ownReq = LookOrderCommand.createOwnOrder(idOrder, request);

		request.setAttribute("own", ownReq);
		LOG.trace("Set the request attribute: own --> " + ownReq);

		request.setAttribute("manager_order", req);
		LOG.trace("Set the request attribute: manager_order --> " + req);

		LOG.debug("OfferCommand finished");
		return Path.PAGE_LOOK_ORDER_CLIENT;
	}

}
