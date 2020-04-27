package test.ua.nure.aseev.SummaryTask4.web.command;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.web.command.LogoutCommand;

public class LogoutCommandTest extends Mockito {

	@Test
	public void testExecute() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);

		when(request.getSession(false)).thenReturn(session);

		LogoutCommand logoutCommand = new LogoutCommand();
		String forward = logoutCommand.execute(request, response);
		verify(request, atLeast(1)).getSession(false);

		assertEquals(Path.PAGE_LOGIN, forward);
	}
}
