package ua.nure.aseev.SummaryTask4.web.command.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.RoomStatus;
import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.entity.Room;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.Messages;
import ua.nure.aseev.SummaryTask4.utils.MailNotification;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class MakeOrderCommand extends Command {

	private static final long serialVersionUID = 2130;

	private static final Logger LOG = Logger.getLogger(MakeOrderCommand.class);

	public static final String FORWARD = Path.PAGE_ERROR_ORDER;

	private static RoomsDAO roomsDao;

	private static OrderDAO orderDao;

	public MakeOrderCommand() {
		roomsDao = new RoomsDAO();
		orderDao = new OrderDAO();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("MakeOrderCommand starts");

		HttpSession session = request.getSession();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();

		String roomNumber = request.getParameter("roomNumber");
		LOG.trace("Request parameter:  roomNumber --> " + roomNumber);

		User user = (User) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("errorMessage", Messages.ERR_PLEASE_LOGIN);
			return Path.PAGE_ERROR_ORDER;
		}

		Long id = user.getId();
		LOG.trace("Request parameter:  id_user --> " + id);

		String timeStart = request.getParameter("start");
		LOG.trace("Request parameter:  start --> " + timeStart);

		String timeEnd = request.getParameter("end");
		LOG.trace("Request parameter:  end --> " + timeEnd);

		if (timeStart.isEmpty() || timeEnd.isEmpty()) {
			request.setAttribute("errorMessage", Messages.ERR_EMPTY_DATE);
			request.setAttribute("forward", true);
			return Path.PAGE_ERROR_ORDER;
		}

		timeStart += " " + sdf.format(date.getTime());
		timeEnd += " " + sdf.format(date.getTime());

		Room roomObject = roomsDao.checkRoomStatus(roomNumber);
		LOG.trace("Found in DB: room --> " + roomObject);

		RoomStatus status = RoomStatus.getStatus(roomObject);

		LOG.trace("Get status from room --> " + status);

		if (status.equals(RoomStatus.getStatus(0))) {
			roomObject = roomsDao.findRoomByNumber(roomNumber);
			LOG.trace("Find in DB : room " + roomObject);

			boolean insertRequest = orderDao.insertRequest(id, roomObject, timeStart, timeEnd);
			LOG.trace("In DB : insert request by number --> " + insertRequest);

			boolean updateRoomStatus = roomsDao.updateRoomStatusToWait(roomNumber);
			LOG.trace("In DB : update room status to 'booked' --> " + updateRoomStatus);

			sendMail(user, insertRequest, updateRoomStatus);

			List<Room> roomList = roomsDao.findRooms();
			LOG.trace("Find in DB:  room " + roomList);

			session.setAttribute("room", roomList);
			LOG.trace("Set the session attribute: room --> " + roomList);

			LOG.trace("MakeOrderCommand finished");
			return Path.PAGE_LIST_MENU;
		}
		request.setAttribute("errorMessage", Messages.ERR_CHOOSE_ANOTHER_ROOM);
		LOG.trace("MakeOrderCommand finished");
		return FORWARD;
	}

	static void sendMail(User user, boolean insertRequest, boolean updateRoomStatus) {
		if (insertRequest && updateRoomStatus) {
			MailNotification mail = new MailNotification();
			boolean flag1 = mail.send("You make order", "Congratulations your order in process, wait..",
					user.getMail());
			LOG.trace("Send mail --> " + flag1);
		}
	}
}
