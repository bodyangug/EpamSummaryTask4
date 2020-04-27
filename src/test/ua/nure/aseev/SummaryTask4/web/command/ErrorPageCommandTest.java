package test.ua.nure.aseev.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.web.command.ErrorPageCommand;

public class ErrorPageCommandTest extends Mockito {

	@Test
	public void executeTest() throws IOException, ServletException, AppException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		User user = mock(User.class);

		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);

		new ErrorPageCommand().execute(request, response);

		verify(request, atLeast(1)).getSession();
		verify(session, atLeast(1)).getAttribute("user");
	}

}
