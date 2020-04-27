package test.ua.nure.aseev.SummaryTask4.web.command.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.aseev.SummaryTask4.db.RoomsDAO;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.web.command.manager.ChangeRoomStatus;

public class ChangeRoomStatusTest extends Mockito {

	@Test
	public void testExecute() throws IOException, ServletException, AppException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		RoomsDAO roomsDao = mock(RoomsDAO.class);

		when(request.getParameter("chooseStatus")).thenReturn("status");
		when(request.getParameter("roomNumber")).thenReturn("1");

		ChangeRoomStatus change = new ChangeRoomStatus(roomsDao);
		change.execute(request, response);

		verify(request, atLeast(1)).getParameter("chooseStatus");
	}
}
