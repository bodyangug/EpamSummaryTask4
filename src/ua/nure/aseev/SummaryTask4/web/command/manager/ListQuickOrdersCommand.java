package ua.nure.aseev.SummaryTask4.web.command.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.entity.UserOrderBean;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class ListQuickOrdersCommand extends Command {

	private static final long serialVersionUID = 1654;

	private static final Logger LOG = Logger.getLogger(ListQuickOrdersCommand.class);

	private static OrderDAO orderDao;

	public ListQuickOrdersCommand() {
		orderDao = new OrderDAO();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("ListQuickOrdersCommnad starts");

		makeQuickOrderTable(request);

		LOG.debug("ListQuickOrdersCommand finished");
		return Path.PAGE_QUICK_ORDERS;
	}

	public static void makeQuickOrderTable(HttpServletRequest request) throws AppException {
		List<UserOrderBean> orderList = orderDao.findUserOrder();
		LOG.trace("DB: Find AllUsersOrder -->" + orderList);
		request.setAttribute("orderList", orderList);
		LOG.trace("Set the request attribute: orderList --> " + orderList);
	}

}
