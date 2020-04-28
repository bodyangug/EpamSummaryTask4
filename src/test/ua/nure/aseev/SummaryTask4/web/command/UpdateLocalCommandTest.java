package test.ua.nure.aseev.SummaryTask4.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.db.entity.Room;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.web.command.UpdateLocaleCommand;

public class UpdateLocalCommandTest extends Mockito {

	@Test
	public void testExecute() throws IOException, ServletException, AppException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RoomsDAO roomsDao = mock(RoomsDAO.class);

		User user = new User();
		user.setLogin("login");
		user.setId(1l);
		user.setRole(0);

		List<Room> list = new ArrayList<>();
		Room room = mock(Room.class);
		room.setNumber(1);
		list.add(room);

		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);
		when(request.getParameter("locale")).thenReturn("something");
		when(roomsDao.findRooms()).thenReturn(list);
		UpdateLocaleCommand update = new UpdateLocaleCommand(roomsDao);
		update.execute(request, response);

		verify(session, atLeast(1)).setAttribute("locale", "something");
	}
}
