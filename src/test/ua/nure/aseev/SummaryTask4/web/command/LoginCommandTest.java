package test.ua.nure.aseev.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.aseev.SummaryTask4.db.UserDAO;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.web.command.LoginCommand;

public class LoginCommandTest extends Mockito {

	@Test
	public void executeTest() throws IOException, ServletException, AppException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		UserDAO userDao = mock(UserDAO.class);

		User user = mock(User.class);
		user.setId(1l);
		user.setLogin("login");
		user.setFirstName("Some Name");
		user.setSecondName("SomeSname");
		user.setMail("email");
		user.setPassword("paswword");
		user.setRole(0);

		when(request.getSession()).thenReturn(session);
		when(request.getParameter("login")).thenReturn("login");
		when(request.getParameter("password")).thenReturn("paswword");
		when(userDao.findUserByLogin("login")).thenReturn(user);

		LoginCommand loginCommand = new LoginCommand(userDao);
		loginCommand.execute(request, response);

		verify(request, atLeast(1)).getParameter("password");
		verify(request, atLeast(1)).getParameter("login");
	}

}
