package ua.nure.aseev.SummaryTask4.web.command.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.UserDAO;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.utils.MailNotification;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class UpdateSucsessCommand extends Command {

	private static final long serialVersionUID = 1714;

	private static final Logger LOG = Logger.getLogger(UpdateSucsessCommand.class);

	private static UserDAO userDao;

	private static OrderDAO orderDao;

	private static RoomsDAO roomsDao;

	public UpdateSucsessCommand() {
		orderDao = new OrderDAO();
		roomsDao = new RoomsDAO();
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
		LOG.debug("UpdateSucsessCommand starts");

		int id = 0;

		boolean yes = false;

		ListQuickOrdersCommand.makeQuickOrderTable(request);
		String valueYes = request.getParameter("value_yes");
		LOG.trace("Request parameter: valueYes --> " + valueYes);
		String valueNo = request.getParameter("value_no");
		LOG.trace("Request parameter: valueNo --> " + valueNo);

		if (valueYes != null) {
			id = Integer.parseInt(valueYes);
			yes = true;
		} else {
			id = Integer.parseInt(valueNo);
		}

		if (id != -1 && yes) {
			boolean flag = orderDao.updateRequestSucsessStatusToTrue(id);
			LOG.trace("DB: Try to update `sucsess` to True -->" + flag);

			updateRequestOperation(id);

		} else {
			boolean flag = orderDao.updateRequestSucsessStatusToFalse(id);
			LOG.trace("DB: Try to update `sucsess` to False -->" + flag);

			int room = roomsDao.findRoomByIdOrder(id);
			LOG.trace("DB: Find Room Number By ID order -->" + room);

			updateRequestOperation(id);

			boolean flag2 = roomsDao.updateRoomStatusToFree(room);
			LOG.trace("DB: Try to update `status` in Room to Free --> " + flag2);

			if (flag) {
				Long userId = userDao.findIdUserByRequest(id);
				LOG.trace("Found in DB user_id by OWN request --> " + userId);
				User user = userDao.findUserById(userId);
				LOG.trace("Found in DB user by user_id --> " + user);
				MailNotification mail = new MailNotification();
				boolean flag1 = mail.send("Decline", "Your order was decline by manager", user.getMail());
				LOG.trace("Send mail --> " + flag1);

			}

			boolean flag3 = orderDao.deleteQuickRequest(id);
			LOG.trace("DB: Try to delete Order -->" + flag3);
		}

		ListQuickOrdersCommand.makeQuickOrderTable(request);

		LOG.debug("UpdateSucsessCommand finished");
		return Path.PAGE_QUICK_ORDERS;
	}

	private void updateRequestOperation(int id) throws DBException {
		boolean flag1 = orderDao.upateRequestOperationToTrue(id);
		LOG.trace("DB: Try to update `operation` to True --> " + flag1);

	}

}
