package ua.nure.aseev.SummaryTask4.web.command.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.UserDAO;
import ua.nure.aseev.SummaryTask4.db.entity.Room;
import ua.nure.aseev.SummaryTask4.db.entity.UserOrderBean;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class ChooseThatRoomCommand extends Command {

	private static final long serialVersionUID = 2244;

	private static final Logger LOG = Logger.getLogger(ChooseThatRoomCommand.class);

	private static RoomsDAO roomsDao;

	private static OrderDAO orderDao;

	private static UserDAO userDao;

	public ChooseThatRoomCommand() {
		roomsDao = new RoomsDAO();
		orderDao = new OrderDAO();
		try {
			userDao = new UserDAO();
		} catch (DBException e) {
			LOG.error(e);
			e.printStackTrace();
		}
	}

	public ChooseThatRoomCommand(RoomsDAO roomsDao, OrderDAO orderDao, UserDAO userDao) {
		this.roomsDao = roomsDao;
		this.orderDao = orderDao;
		this.userDao = userDao;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("ChooseThatRoomCommand starts");
		HttpSession session = request.getSession();

		String roomNumber = request.getParameter("roomNumber");
		LOG.trace("Request parameter: roomNumber --> " + roomNumber);

		Room room = roomsDao.findRoomByNumber(roomNumber);
		LOG.trace("DB: Find in DB room -->" + room);

		UserOrderBean order = (UserOrderBean) session.getAttribute("orderBean");
		LOG.trace("Request parameter: orderBean --> " + order);

		Long idOrderUser = (Long) session.getAttribute("idOrderUser");
		LOG.trace("Request parameter: idOrderUser --> " + idOrderUser);

		Long id = userDao.findIdUserByOwnRequest(idOrderUser);
		LOG.trace("DB: Find in DB userID by OwnRequest --> " + id);

		boolean insertOrder = orderDao.insertRequestToManager(id, room, order.getTimeStart(), order.getTimeEnd());
		LOG.trace("DB: Insert in DB request from Manager --> " + insertOrder);

		if (insertOrder) {
			boolean flag = roomsDao.updateRoomStatusToWait(roomNumber);
			LOG.trace("DB: Try to update room to Booked --> " + flag);

			boolean flag1 = orderDao.upateOwnRequestOperationToTrue(idOrderUser);
			LOG.trace("DB: Try to update Operation to True --> " + flag1);
		}

		List<UserOrderBean> orderOwnList = orderDao.findUserOwnOrder();
		LOG.trace("DB: Find UsersOwnOrder --> " + orderOwnList);

		request.setAttribute("orderOwnList", orderOwnList);
		LOG.trace("Set the request attribute: orderOwnList --> " + orderOwnList);

		LOG.debug("ChooseThatRoomCommand finished");
		return Path.PAGE_PERSONAL_ORDERS;
	}

}
