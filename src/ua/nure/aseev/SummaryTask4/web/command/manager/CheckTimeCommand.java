package ua.nure.aseev.SummaryTask4.web.command.manager;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeComparator;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.entity.OwnRequest;
import ua.nure.aseev.SummaryTask4.db.entity.Request;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.exception.Messages;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class CheckTimeCommand extends Command {

	private static final long serialVersionUID = 2133;

	private static final Logger LOG = Logger.getLogger(CheckTimeCommand.class);

	private static OrderDAO orderDao;

	private static RoomsDAO roomsDao;

	public CheckTimeCommand() {
		this.orderDao = new OrderDAO();
		this.roomsDao = new RoomsDAO();
	}

	public CheckTimeCommand(OrderDAO orderDAO, RoomsDAO roomsDAO) {
		this.orderDao = orderDAO;
		this.roomsDao = roomsDAO;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("CheckTimeCommand starts");
		String forward = Path.PAGE_CHANGE_ROOM_STATUS;

		Date date = new Date();

		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentDate = time.format(date.getTime());

		DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();

		List<Request> orderList = orderDao.findAllUserOrders();
		LOG.trace("Find in DB all user`s orders -->" + orderList);

		List<OwnRequest> orderOwnList = orderDao.findAllUserOwnOrders();
		LOG.trace("Find in DB all user`s ows orders -->" + orderOwnList);

		forward = deleteOrder(dateTimeComparator, request, currentDate, orderList);

		forward = deleteOwnOrder(dateTimeComparator, request, currentDate, orderOwnList);

		ChangeRoomStatus status = new ChangeRoomStatus();
		status.execute(request, response);

		return forward;
	}

	public static String deleteOrder(DateTimeComparator dateTimeComparator, HttpServletRequest request,
			String currentDate, List<Request> orderList) throws DBException {
		for (Request i : orderList) {
			try {
				Date orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(i.getTimeEnd());
				Date nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(currentDate);
				int retVal = dateTimeComparator.compare(orderDate, nowDate);
				if (retVal < 0 && i.isOpearation()) {
					boolean flag = orderDao.deleteOrderTimeEnd(i.getId());
					LOG.trace("Delete all order where timeEnd < curentDate -->" + flag);
					boolean flag2 = roomsDao.updateRoomStatusToFree(i.getRoomNumber());
					LOG.trace("Update rooms= " + i.getRoomNumber() + " status to free " + flag2);
				}

			} catch (ParseException e) {
				LOG.error(e);
				request.setAttribute("errorMessage", Messages.ERR_CANNOT_PARSE_TIME);
				return Path.PAGE_ERROR_ORDER;
			}
		}
		return Path.PAGE_CHANGE_ROOM_STATUS;

	}

	private String deleteOwnOrder(DateTimeComparator dateTimeComparator, HttpServletRequest request, String currentDate,
			List<OwnRequest> orderOwnList) throws DBException {
		for (OwnRequest i : orderOwnList) {
			try {
				Date orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(i.getTimeEnd());
				Date nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentDate);

				int retVal = dateTimeComparator.compare(orderDate, nowDate);
				if (retVal > 0) {
					boolean flag = orderDao.deleteOrderTimeEnd(i.getId());
					LOG.trace("Delete all order where timeEnd < curentDate -->" + flag);
					boolean flag2 = roomsDao.updateRoomStatusToFree(i.getRoomNumber());
					LOG.trace("Update rooms= " + i.getRoomNumber() + " status to free " + flag2);
				}

			} catch (ParseException e) {
				LOG.error(e);
				request.setAttribute("errorMessage", Messages.ERR_CANNOT_PARSE_TIME);
				request.setAttribute("forward", true);
				return Path.PAGE_ERROR_ORDER;
			}
		}
		return Path.PAGE_CHANGE_ROOM_STATUS;

	}
}
