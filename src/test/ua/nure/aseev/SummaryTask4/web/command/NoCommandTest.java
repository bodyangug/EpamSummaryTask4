package test.ua.nure.aseev.SummaryTask4.web.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.web.command.NoCommand;

public class NoCommandTest extends Mockito {

	@Test
	public void testExecute() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		String errorMessage = "No such command";

		String forward = new NoCommand().execute(request, response);

		verify(request, atLeast(1)).setAttribute("errorMessage", errorMessage);

		assertEquals(Path.PAGE_ERROR_PAGE, forward);
	}

}
