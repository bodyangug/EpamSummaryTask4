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

public class ListPersonalOrderCommand extends Command {

	private static final long serialVersionUID = 1651;

	private static final Logger LOG = Logger.getLogger(ListPersonalOrderCommand.class);

	private static OrderDAO orderDao;

	public ListPersonalOrderCommand() {
		this.orderDao = new OrderDAO();
	}

	public ListPersonalOrderCommand(OrderDAO orderDAO) {
		this.orderDao = orderDAO;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("ListPersonalOrderCommand starts");
		List<UserOrderBean> orderOwnList = orderDao.findUserOwnOrder();
		LOG.trace("DB: Find UserOwnOrder --> " + orderOwnList);

		request.setAttribute("orderOwnList", orderOwnList);
		LOG.trace("Set the request attribute: orderOwnList --> " + orderOwnList);

		LOG.debug("ListPersonalOrderCommand finished");
		return Path.PAGE_PERSONAL_ORDERS;

	}

}
