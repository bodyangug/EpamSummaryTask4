package ua.nure.aseev.SummaryTask4.web.command.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.UserDAO;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.db.entity.UserOrderBean;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.utils.MailNotification;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class DeclineOrderCommand extends Command {

	private static final long serialVersionUID = 907;

	private static final Logger LOG = Logger.getLogger(DeclineOrderCommand.class);

	private static OrderDAO orderDao;

	private static UserDAO userDao;

	public DeclineOrderCommand() {
		orderDao = new OrderDAO();
		try {
			userDao = new UserDAO();
		} catch (DBException e) {
			LOG.error(e);
			e.printStackTrace();
		}
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.trace("DeclineOrderCommand starts");
		Long orderId = Long.parseLong(request.getParameter("decline"));
		LOG.trace("Request get parameter: decline --> " + orderId);

		notifyAboutDelete(orderId);

		boolean flag = orderDao.deleteOwnOrder(orderId);
		LOG.trace("Try to delete room by `decline` --> " + flag);

		List<UserOrderBean> orderOwnList = orderDao.findUserOwnOrder();
		LOG.trace("DB: Find UserOwnOrder --> " + orderOwnList);

		request.setAttribute("orderOwnList", orderOwnList);
		LOG.trace("Set the request attribute: orderOwnList --> " + orderOwnList);

		LOG.trace("DeclineOrderCommand finished");
		return Path.PAGE_PERSONAL_ORDERS;
	}

	private void notifyAboutDelete(Long orderId) throws DBException {
		Long userId = userDao.findIdUserByOwnRequest(orderId);
		LOG.trace("Found in DB user_id by OWN request --> " + userId);
		User user = userDao.findUserById(userId);
		LOG.trace("Found in DB user by user_id --> " + user);
		MailNotification mail = new MailNotification();
		boolean flag1 = mail.send("Decline", "Your order was decline by manager", user.getMail());
		LOG.trace("Send mail --> " + flag1);

	}

}
