package ua.nure.aseev.SummaryTask4.web.command.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.entity.Room;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.web.command.Command;

public class ChangeRoomStatus extends Command {

	private static final long serialVersionUID = 839;

	private static Logger LOG = Logger.getLogger(ChangeRoomStatus.class);

	private static RoomsDAO roomsDao;

	public ChangeRoomStatus() {
		roomsDao = new RoomsDAO();
	}

	public ChangeRoomStatus(RoomsDAO roomsDao) {
		this.roomsDao = roomsDao;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("ChangeRoomStatus starts");

		List<Room> room = null;

		String changeBy = request.getParameter("chooseStatus");
		LOG.trace("Get parameter from request: changeBy --> " + changeBy);

		if (changeBy != null) {

			int numberRoom = Integer.parseInt(request.getParameter("roomNumber"));
			LOG.trace("Get parameter from request: roomNumber --> " + numberRoom);

			boolean flag = roomsDao.checkRoomStatusManager(numberRoom, changeBy);
			LOG.trace("Change room status --> " + flag);

			room = findRooms(room);

		} else {
			room = findRooms(room);
		}

		request.setAttribute("room", room);
		LOG.trace("Set the request attribute: room --> " + room);

		LOG.debug("ChangeRoomStatus finished");
		return Path.PAGE_CHANGE_ROOM_STATUS;
	}

	private List<Room> findRooms(List<Room> room) throws DBException {
		room = roomsDao.findRooms();
		LOG.trace("Found in DB: Rooms List --> " + room);

		return room;
	}

}
